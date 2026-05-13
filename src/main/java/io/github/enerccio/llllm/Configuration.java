package io.github.enerccio.llllm;

public class Configuration {


    private Long persistentLoginInfoTTL = 60 * 60 * 24 * 30L;
    private boolean allowPersistentLogin = true;

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
}
