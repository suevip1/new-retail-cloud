package com.zhihao.newretail.product.pojo.vo;

import java.util.List;

public class CategoryVO {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<CategoryVO> categoryVOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<CategoryVO> getCategoryVOList() {
        return categoryVOList;
    }

    public void setCategoryVOList(List<CategoryVO> categoryVOList) {
        this.categoryVOList = categoryVOList;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", categoryVOList=" + categoryVOList +
                '}';
    }

}
