package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

// User is also settings for the app
@Entity
@Table(indexes = {
        @Index(name = "user_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "user_login_idx", columnList = "login"),
})
public class User extends ExtendedContentEntity {

    @Column(unique = true, length = 64)
    private String login;

    @Lob
    private String fullName;

    @Lob
    private String passwordHash;

    @Lob
    private String savedLogins;

    private boolean isAdmin;

    // settings for the app

    @ManyToOne(fetch = FetchType.EAGER)
    private AI summaryAI;

    @ManyToOne(fetch = FetchType.EAGER)
    private Protocol summaryProtocol;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public AI getSummaryAI() {
        return summaryAI;
    }

    public void setSummaryAI(AI summaryAI) {
        this.summaryAI = summaryAI;
    }

    public Protocol getSummaryProtocol() {
        return summaryProtocol;
    }

    public void setSummaryProtocol(Protocol summaryProtocol) {
        this.summaryProtocol = summaryProtocol;
    }
}
