package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.model.security.PersistedLoginInfo;
import io.github.enerccio.llllm.model.service.UserService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import io.github.enerccio.llllm.model.tx.NoTx;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserServiceImpl extends ExtendedContentServiceImpl<User> implements UserService {

    private boolean singleUser = false;
    private String singleUserName;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public boolean isSingleUser() {
        return singleUser;
    }

    public void setSingleUser(boolean singleUser) {
        this.singleUser = singleUser;
    }

    @Override
    public String getSingleUserName() {
        return singleUserName;
    }

    @Override
    public User findByName(String name) {
        List<User> results = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.login = :name",
                User.class
        ).setParameter("name", name).setMaxResults(1).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.getFirst();
    }

    @Override
    public boolean authenticate(String username, String password) throws Exception {
        User user = findByName(username);
        if (user == null) {
            return false;
        }
        String storedPasswordData = user.getPasswordHash();
        if (storedPasswordData == null && StringUtils.isBlank(password)) {
            return true;
        }
        if (StringUtils.isBlank(password) || storedPasswordData == null) {
            return false;
        }
        String[] parts = storedPasswordData.split(":");
        String salt = parts[0];
        String hash = parts[1];
        return hash.equals(hashPassword(password, salt));
    }

    @Override
    @CommonTx
    public User changePassword(User user, String password) throws Exception {
        byte[] saltBytes = new byte[16];
        secureRandom.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        String hash = hashPassword(password, salt);
        user.setPasswordHash(salt + ":" + hash);
        save(user);
        return user;
    }

    @Override
    @CommonTx
    public PersistedLoginInfo authenticateFromCookie(User user, String identifier, String secret) throws Exception {
        List<PersistedLoginInfo> loginInfos = getPersistedLoginInfo(user);
        for (PersistedLoginInfo loginInfo : loginInfos) {
            if (loginInfo.getIdentifier().equals(identifier)) {
                if (loginInfo.getHashedSecret().equals(hashPersistedLoginSecret(user, secret))) {
                    if (configuration.getPersistentLoginInfoTTL() != null) {
                        long ctime = System.currentTimeMillis() - (configuration.getPersistentLoginInfoTTL() * 1000);
                        if (ctime >= loginInfo.getCreate()) {
                            // we passed the max login window
                            deletePersistedLoginInfo(user, loginInfo);
                            save(user);
                            return null;
                        }
                    }
                    PersistedLoginInfo newLoginInfo = generateNewPersistentInfo(user);
                    newLoginInfo.setIdentifier(loginInfo.getIdentifier());
                    newLoginInfo.setCreate(loginInfo.getCreate());
                    deletePersistedLoginInfo(user, loginInfo);
                    addPersistedLoginInfo(user, newLoginInfo);
                    save(user);
                    return newLoginInfo;
                }
            }
        }
        return null;
    }

    @Override
    public void deletePersistedLoginInfo(User user, PersistedLoginInfo persistedLoginInfo) throws Exception {
        List<PersistedLoginInfo> persistedLoginInfos = getPersistedLoginInfo(user);
        List<PersistedLoginInfo> newPersistedLoginInfos = new ArrayList<>();
        for (PersistedLoginInfo loginInfo : persistedLoginInfos) {
            if (loginInfo.getIdentifier().equals(persistedLoginInfo.getIdentifier()))
                continue;
            long ctime = System.currentTimeMillis() - configuration.getPersistentLoginInfoTTL() * 1000;
            if (ctime >= loginInfo.getCreate()) {
                continue;
            }
            newPersistedLoginInfos.add(loginInfo);
        }
        serializePersistedLoginInfo(user, newPersistedLoginInfos);
    }

    @NoTx
    @Override
    public List<PersistedLoginInfo> getPersistedLoginInfo(User user) throws Exception {
        if (StringUtils.isBlank(user.getSavedLogins()))
            return Collections.emptyList();
        LinkedHashSet<PersistedLoginInfo> loginInfos = new LinkedHashSet<>();
        for (String savedLogin : user.getSavedLogins().split(Pattern.quote("|"))) {
            PersistedLoginInfo info = new PersistedLoginInfo();
            String[] parsed = savedLogin.split(Pattern.quote(";"));
            info.setIdentifier(parsed[0]);
            info.setHashedSecret(parsed[1]);
            info.setCreate(Long.parseLong(parsed[2]));
            info.setLastAccess(Long.parseLong(parsed[3]));
            loginInfos.add(info);
        }
        return loginInfos.stream().toList();
    }

    @NoTx
    @Override
    public PersistedLoginInfo generateNewPersistentInfo(User user) throws Exception {
        BigInteger prime = BigInteger.probablePrime(160, secureRandom);
        String secret = "" + prime;
        PersistedLoginInfo loginInfo = new PersistedLoginInfo();
        loginInfo.setIdentifier(UUID.randomUUID().toString());
        loginInfo.setCreate(System.currentTimeMillis());
        loginInfo.setLastAccess(System.currentTimeMillis());
        loginInfo.setPlainSecret(secret);
        loginInfo.setHashedSecret(hashPersistedLoginSecret(user, secret));
        return loginInfo;
    }

    @NoTx
    @Override
    public void addPersistedLoginInfo(User user, PersistedLoginInfo persistedLoginInfo) throws Exception {
        List<PersistedLoginInfo> persistedLoginInfos = getPersistedLoginInfo(user);
        List<PersistedLoginInfo> newPersistedLoginInfos = new ArrayList<>();
        for (PersistedLoginInfo loginInfo : persistedLoginInfos) {
            if (loginInfo.getIdentifier().equals(persistedLoginInfo.getIdentifier()))
                continue;
            long ctime = System.currentTimeMillis() - configuration.getPersistentLoginInfoTTL();
            if (ctime >= loginInfo.getCreate()) {
                continue;
            }
            newPersistedLoginInfos.add(loginInfo);
        }
        newPersistedLoginInfos.add(persistedLoginInfo);
        serializePersistedLoginInfo(user, newPersistedLoginInfos);
    }

    @Override
    @CommonTx
    public void onInitialize() throws Exception {
        User user = findByName(singleUserName);
        if (user == null) {
            user = new User();
            user.setLogin(singleUserName);
            user.setFullName(singleUserName);
            user.setAdmin(true);
            save(user);
        }
    }


    protected String hashPersistedLoginSecret(User user, String secret) throws Exception {
        secret = user.getId() + "_" + secret;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(secret.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest.digest());
    }

    @NoTx
    protected void serializePersistedLoginInfo(User user, List<PersistedLoginInfo> persistedLoginInfos) throws Exception {
        user.setSavedLogins(persistedLoginInfos.stream().map(pli ->
                pli.getIdentifier() + ";" + pli.getHashedSecret() + ";" + pli.getCreate() + ";" + pli.getLastAccess()).collect(Collectors.joining("|")));
    }

    protected String hashPassword(String password, String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(Base64.getDecoder().decode(salt));
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    public void setSingleUserName(String singleUserName) {
        this.singleUserName = singleUserName;
    }
}
