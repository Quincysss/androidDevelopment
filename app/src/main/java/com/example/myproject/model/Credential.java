package com.example.myproject.model;

import java.util.Date;

public class Credential {
    private Integer credentialId;
    private Users userid;
    private String username;
    private String password;
    private Date signdate;

    public Credential() {
    }

    public Credential(Users userid, String username, String password, Date signdate ) {
        // this.credentialId = credentialId;
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.signdate = signdate;

    }
    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSigndate() {
        return signdate;
    }

    public void setSigndate(Date signdate) {
        this.signdate = signdate;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }
}