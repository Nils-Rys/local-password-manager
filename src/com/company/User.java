package com.company;

import java.util.ArrayList;

public class User {

    // Stores users session information
    private String key;
    private String userHash;
    private ArrayList<Account> accounts = new ArrayList<>();


    public User(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

}
