package com.zhihao.newretail.api.user.vo;

public class UserApiVO {

    private Integer id;

    private String uuid;

    private UserInfoApiVO userInfoApiVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserInfoApiVO getUserInfoApiVO() {
        return userInfoApiVO;
    }

    public void setUserInfoApiVO(UserInfoApiVO userInfoApiVO) {
        this.userInfoApiVO = userInfoApiVO;
    }

    @Override
    public String toString() {
        return "UserApiVO{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userInfoApiVO=" + userInfoApiVO +
                '}';
    }

}
