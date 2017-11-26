package com.describer.model;

/**
 * Class describes account entity
 */

import java.security.Timestamp;

public class Account {

    private String userName;
    private String password;
    private Timestamp lastLogin;

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

