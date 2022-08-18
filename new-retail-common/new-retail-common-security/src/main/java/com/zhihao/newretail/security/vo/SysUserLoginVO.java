package com.zhihao.newretail.security.vo;

import java.io.Serializable;

public class SysUserLoginVO implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String key;

    private Integer scope;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "SysUserLoginVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", scope=" + scope +
                '}';
    }

}
