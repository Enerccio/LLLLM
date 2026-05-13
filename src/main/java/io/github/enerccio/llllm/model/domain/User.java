package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public class User extends ExtendedContentEntity {

    @Column(unique = true, length = 64)
    private String login;

    @Lob
    private String fullName;

    @Lob
    private String passwordHash;

    @Lob
    private String savedLogins;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSavedLogins() {
        return savedLogins;
    }

    public void setSavedLogins(String savedLogins) {
        this.savedLogins = savedLogins;
    }
}
