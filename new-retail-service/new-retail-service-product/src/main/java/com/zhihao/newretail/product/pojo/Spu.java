package com.zhihao.newretail.product.pojo;

import java.util.Date;

public class Spu {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private Integer isSaleable;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private SpuInfo spuInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getIsSaleable() {
        return isSaleable;
    }

    public void setIsSaleable(Integer isSaleable) {
        this.isSaleable = isSaleable;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SpuInfo getSpuInfo() {
        return spuInfo;
    }

    public void setSpuInfo(SpuInfo spuInfo) {
        this.spuInfo = spuInfo;
    }

    @Override
    public String toString() {
        return "Spu{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", isSaleable=" + isSaleable +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", spuInfo=" + spuInfo +
                '}';
    }

}
