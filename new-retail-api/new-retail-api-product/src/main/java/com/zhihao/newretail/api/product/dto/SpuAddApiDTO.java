package com.zhihao.newretail.api.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SpuAddApiDTO {

    @NotNull(message = "商品所属分类不能为空")
    private Integer categoryId;

    @NotBlank(message = "商品标题不能为空")
    private String title;

    private String subTitle;

    @NotBlank(message = "商品图片不能为空")
    private String showImage;

    private String sliderImage;

    private String detailTitle;

    private String detailPram;

    private String detailImage;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetailPram() {
        return detailPram;
    }

    public void setDetailPram(String detailPram) {
        this.detailPram = detailPram;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

}
