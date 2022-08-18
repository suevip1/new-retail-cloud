package com.zhihao.newretail.security.vo;

import java.io.Serializable;

public class SysUserLoginVO implements Serializable {

    private String userToken;

    private Integer id;

    private String username;

    private String name;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SysUserLoginVO{" +
                "userToken='" + userToken + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
