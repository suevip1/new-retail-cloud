package com.zhihao.newretail.api.product.vo;

public class SlideApiVO {

    private Integer id;

    private Integer spuId;

    private String slideImage;

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

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "SlideApiVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", slideImage='" + slideImage + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

}
