package com.zhihao.newretail.product.pojo.vo;

public class GoodsVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private String showImage;

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

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    @Override
    public String toString() {
        return "GoodsVO{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", showImage='" + showImage + '\'' +
                '}';
    }

}
