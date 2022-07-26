package com.zhihao.newretail.product.pojo.vo;

import java.util.List;

public class ProductDetailVO {

    private Integer id;

    private String title;

    private ProductInfoVO productInfoVO;

    private List<SkuVO> skuVOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductInfoVO getProductInfoVO() {
        return productInfoVO;
    }

    public void setProductInfoVO(ProductInfoVO productInfoVO) {
        this.productInfoVO = productInfoVO;
    }

    public List<SkuVO> getSkuVOList() {
        return skuVOList;
    }

    public void setSkuVOList(List<SkuVO> skuVOList) {
        this.skuVOList = skuVOList;
    }

    @Override
    public String toString() {
        return "ProductDetailVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", productInfoVO=" + productInfoVO +
                ", skuVOList=" + skuVOList +
                '}';
    }

}
