package com.zhihao.newretail.api.user.dto;

public class UserApiDTO {

    private String uuid;

    private String username;

    private String weChat;

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

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @Override
    public String toString() {
        return "UserApiDTO{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", weChat='" + weChat + '\'' +
                '}';
    }

}
