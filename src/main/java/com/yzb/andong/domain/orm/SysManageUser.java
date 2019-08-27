package com.yzb.andong.domain.orm;

/**
 * Created by brander on 2019/1/12
 */
public class SysManageUser {
    private Integer id;
    private String account;
    private String pass;
    private String realName;
    private String salt;
    private Boolean locked;
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SysManageUser{" +
                "account='" + account + '\'' +
                ", pass='" + pass + '\'' +
                ", realName='" + realName + '\'' +
                ", salt='" + salt + '\'' +
                ", locked=" + locked +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
