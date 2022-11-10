package com.zhihao.newretail.user.vo;

public class UserInfoVO {

    private String nickName;

    private String photo;

    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

}
