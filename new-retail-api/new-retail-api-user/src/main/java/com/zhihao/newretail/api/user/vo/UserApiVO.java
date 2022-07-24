package com.zhihao.newretail.api.user.vo;

public class UserApiVO {

    private Integer id;

    private String uuid;

    private String username;

    private String password;

    private String weChat;

    private String tel;

    private Integer levelId;

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

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", weChat='" + weChat + '\'' +
                ", tel='" + tel + '\'' +
                ", levelId=" + levelId +
                ", userInfoApiVO=" + userInfoApiVO +
                '}';
    }

}
