package com.zhihao.newretail.product.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class HomeProductVO implements Serializable {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<ProductVO> productVOList;

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

    public List<ProductVO> getProductVOList() {
        return productVOList;
    }

    public void setProductVOList(List<ProductVO> productVOList) {
        this.productVOList = productVOList;
    }

    @Override
    public String toString() {
        return "HomeProductVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", productVOList=" + productVOList +
                '}';
    }

}
