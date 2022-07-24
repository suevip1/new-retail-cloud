package com.zhihao.newretail.api.user.vo;

public class UserInfoApiVO {

    private String nickName;

    private String photo;

    private String gender;

    private Integer integral;

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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "UserInfoApiVO{" +
                "nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", gender='" + gender + '\'' +
                ", integral=" + integral +
                '}';
    }

}
