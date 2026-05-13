package io.github.enerccio.llllm;

import org.springframework.beans.factory.InitializingBean;

import java.io.File;

public class Configuration implements InitializingBean {

    private Long persistentLoginInfoTTL = 60 * 60 * 24 * 30L;
    private boolean allowPersistentLogin = true;
    private File folder;

    public Long getPersistentLoginInfoTTL() {
        return persistentLoginInfoTTL;
    }

    public void setPersistentLoginInfoTTL(Long persistentLoginInfoTTL) {
        this.persistentLoginInfoTTL = persistentLoginInfoTTL;
    }

    public boolean isAllowPersistentLogin() {
        return allowPersistentLogin;
    }

    public void setAllowPersistentLogin(boolean allowPersistentLogin) {
        this.allowPersistentLogin = allowPersistentLogin;
    }

    public File getFolder() {
        return folder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String homeDir = System.getProperty("user.home");
        folder = new File(homeDir, ".llllm");
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Cannot create folder " + folder.getAbsolutePath());
        }
    }

    public String resolveDb(String db) {
        return "jdbc:sqlite:" + folder.getAbsolutePath() + File.separator + db;
    }
}
