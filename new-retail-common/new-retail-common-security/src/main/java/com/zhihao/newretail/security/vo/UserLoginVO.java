package com.zhihao.newretail.security.vo;

import java.io.Serializable;

public class UserLoginVO implements Serializable {

    private String userToken;

    private Integer userId;

    private String uuid;

    private String nickName;

    private String photo;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserLoginVO{" +
                "userToken='" + userToken + '\'' +
                ", userId=" + userId +
                ", uuid='" + uuid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

}
