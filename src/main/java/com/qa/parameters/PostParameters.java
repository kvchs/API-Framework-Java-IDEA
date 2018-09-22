package com.qa.parameters;

public class PostParameters {
    private String userName;
    private String password;

    public PostParameters(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public PostParameters() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
