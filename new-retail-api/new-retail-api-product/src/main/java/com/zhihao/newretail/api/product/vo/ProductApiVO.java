package com.zhihao.newretail.api.product.vo;

import java.util.List;

public class ProductApiVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private Integer isSaleable;

    private String showImage;

    private List<GoodsApiVO> goodsApiVOList;

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

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public List<GoodsApiVO> getGoodsApiVOList() {
        return goodsApiVOList;
    }

    public void setGoodsApiVOList(List<GoodsApiVO> goodsApiVOList) {
        this.goodsApiVOList = goodsApiVOList;
    }

    @Override
    public String toString() {
        return "ProductApiVO{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", isSaleable=" + isSaleable +
                ", showImage='" + showImage + '\'' +
                ", goodsApiVOList=" + goodsApiVOList +
                '}';
    }

}
