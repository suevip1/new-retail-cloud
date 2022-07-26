package com.zhihao.newretail.product.pojo;

public class SpuInfo {

    private Integer id;

    private Integer spuId;

    private String showImage;

    private String sliderImage;

    private String detailTitle;

    private String detailPram;

    private String detailImage;

    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage == null ? null : showImage.trim();
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
        this.detailTitle = detailTitle == null ? null : detailTitle.trim();
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "SpuInfo{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", showImage='" + showImage + '\'' +
                ", sliderImage='" + sliderImage + '\'' +
                ", detailTitle='" + detailTitle + '\'' +
                ", detailPram='" + detailPram + '\'' +
                ", detailImage='" + detailImage + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

}
