package com.zhihao.newretail.api.product.dto;

public class SpuAddApiDTO {

    private Integer categoryId;

    private String title;

    private String subTitle;

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

    @Override
    public String toString() {
        return "SpuAddApiDTO{" +
                "categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", showImage='" + showImage + '\'' +
                ", sliderImage='" + sliderImage + '\'' +
                ", detailTitle='" + detailTitle + '\'' +
                ", detailPram='" + detailPram + '\'' +
                ", detailImage='" + detailImage + '\'' +
                '}';
    }

}
