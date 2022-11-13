package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuForm {

    @NotNull(message = "请选择所属商品")
    private Integer spuId;

    private String skuImage;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @NotNull(message = "请填写库存数量")
    private Integer stock;

    private List<SkuParamForm> skuParamFormList;

}
