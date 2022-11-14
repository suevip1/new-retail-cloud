package com.zhihao.newretail.user.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateNickNameForm {

    @NotBlank(message = "昵称不能为空")
    private String nickName;

}
