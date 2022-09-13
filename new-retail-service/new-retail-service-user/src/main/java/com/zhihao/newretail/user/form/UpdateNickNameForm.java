package com.zhihao.newretail.user.form;

import javax.validation.constraints.NotBlank;

public class UpdateNickNameForm {

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
