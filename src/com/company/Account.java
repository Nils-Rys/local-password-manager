package com.company;

public class Account {
    private String webName;
    private String webUser;
    private String webPass;

    public Account(String webName, String webUser, String webPass){
        this.webName = webName;
        this.webUser = webUser;
        this.webPass = webPass;
    }


    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebUser() {
        return webUser;
    }

    public void setWebUser(String webUser) {
        this.webUser = webUser;
    }

    public String getWebPass() {
        return webPass;
    }

    public void setWebPass(String webPass) {
        this.webPass = webPass;
    }



}
