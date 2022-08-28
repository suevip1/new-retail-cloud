package com.zhihao.newretail.api.order.vo;

public class OrderUserApiVO {

    private String nickName;

    private String photo;

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
        return "OrderUserApiVO{" +
                "nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

}
