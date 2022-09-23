package com.zhihao.newretail.admin.form;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class SkuForm {

    @NotNull(message = "请选择所属商品")
    private Integer spuId;

    private String skuImage;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @NotNull(message = "请填写库存数量")
    private Integer stock;

    private List<SkuParamForm> skuParamFormList;

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<SkuParamForm> getSkuParamFormList() {
        return skuParamFormList;
    }

    public void setSkuParamFormList(List<SkuParamForm> skuParamFormList) {
        this.skuParamFormList = skuParamFormList;
    }

}
