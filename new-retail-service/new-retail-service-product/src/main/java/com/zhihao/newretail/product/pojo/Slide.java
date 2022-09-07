package com.zhihao.newretail.product.pojo;

import java.util.Date;

public class Slide {

    private Integer id;

    private Integer spuId;

    private String slideImage;

    private Integer isDelete;

    private Date createTime;

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
        this.slideImage = slideImage == null ? null : slideImage.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
