package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.model.security.PersistedLoginInfo;
import io.github.enerccio.llllm.model.tx.NoTx;

import java.util.List;

public interface UserService extends ExtendedContentService<User> {

    boolean isSingleUser();
    String getSingleUserName();

    User findByName(String name) throws Exception;
    boolean authenticate(String username, String password) throws Exception;
    User changePassword(User user, String password) throws Exception;
    PersistedLoginInfo authenticateFromCookie(User user, String cookieIdentifier, String cookieSecret) throws Exception;

    @NoTx
    void deletePersistedLoginInfo(User user, PersistedLoginInfo persistedLoginInfo) throws Exception;

    @NoTx
    List<PersistedLoginInfo> getPersistedLoginInfo(User user) throws Exception;

    @NoTx
    PersistedLoginInfo generateNewPersistentInfo(User user) throws Exception;

    @NoTx
    void addPersistedLoginInfo(User user, PersistedLoginInfo persistedLoginInfo) throws Exception;

    void onInitialize() throws Exception;
}
