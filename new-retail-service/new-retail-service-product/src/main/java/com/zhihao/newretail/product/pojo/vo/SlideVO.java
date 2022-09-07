package com.zhihao.newretail.product.pojo.vo;

public class SlideVO {

    private Integer id;

    private Integer spuId;

    private String slideImage;

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

    @Override
    public String toString() {
        return "SlideVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", slideImage='" + slideImage + '\'' +
                '}';
    }

}
