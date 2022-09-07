package com.zhihao.newretail.rbac.form;

import javax.validation.constraints.NotBlank;

public class SlideForm {

    private Integer spuId;

    @NotBlank(message = "图片URL不能为空")
    private String slideImage;

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

}
