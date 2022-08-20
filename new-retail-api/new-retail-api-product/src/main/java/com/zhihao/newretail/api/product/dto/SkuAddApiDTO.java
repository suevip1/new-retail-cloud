package com.zhihao.newretail.api.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SkuAddApiDTO {

    @NotNull(message = "请选择所属商品")
    private Integer spuId;

    @NotBlank(message = "请添加图片")
    private String skuImage;

    @NotBlank(message = "商品规格参数不能为空")
    private String param;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SkuAddApiDTO{" +
                "spuId=" + spuId +
                ", skuImage='" + skuImage + '\'' +
                ", param='" + param + '\'' +
                ", price=" + price +
                '}';
    }

}
