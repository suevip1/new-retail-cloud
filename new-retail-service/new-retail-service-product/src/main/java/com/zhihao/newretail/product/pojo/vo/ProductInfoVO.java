package com.zhihao.newretail.product.pojo.vo;

public class ProductInfoVO {

    private String showImage;

    private String sliderImage;

    private String detailTitle;

    private String detailPram;

    private String detailImage;

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
        return "ProductInfoVO{" +
                "showImage='" + showImage + '\'' +
                ", sliderImage='" + sliderImage + '\'' +
                ", detailTitle='" + detailTitle + '\'' +
                ", detailPram='" + detailPram + '\'' +
                ", detailImage='" + detailImage + '\'' +
                '}';
    }

}
