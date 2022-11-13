package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SlideForm {

    private Integer spuId;

    @NotBlank(message = "图片URL不能为空")
    private String slideImage;

}
