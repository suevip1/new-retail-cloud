package com.zhihao.newretail.admin.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SpuForm {

    @NotNull(message = "请选择所属分类")
    private Integer categoryId;

    @NotBlank(message = "商品标题不能为空")
    private String title;

    private String subTitle;

    private String showImage;

    private String detailTitle;

    private List<String> sliderImageUrlList;

    private List<String> detailImageUrlList;

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

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public List<String> getSliderImageUrlList() {
        return sliderImageUrlList;
    }

    public void setSliderImageUrlList(List<String> sliderImageUrlList) {
        this.sliderImageUrlList = sliderImageUrlList;
    }

    public List<String> getDetailImageUrlList() {
        return detailImageUrlList;
    }

    public void setDetailImageUrlList(List<String> detailImageUrlList) {
        this.detailImageUrlList = detailImageUrlList;
    }

}
