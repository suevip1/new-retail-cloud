package com.zhihao.newretail.api.product.vo;

public class SpuApiVO {

    private Integer id;

    private Integer categoryId;

    private String title;

    private String subTitle;

    private SpuInfoApiVO spuInfoApiVO;

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

    public SpuInfoApiVO getSpuInfoApiVO() {
        return spuInfoApiVO;
    }

    public void setSpuInfoApiVO(SpuInfoApiVO spuInfoApiVO) {
        this.spuInfoApiVO = spuInfoApiVO;
    }

    @Override
    public String toString() {
        return "SpuApiVO{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", spuInfoApiVO=" + spuInfoApiVO +
                '}';
    }

}
