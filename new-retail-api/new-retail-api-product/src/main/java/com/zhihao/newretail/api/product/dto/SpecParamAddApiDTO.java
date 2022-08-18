package com.zhihao.newretail.api.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SpecParamAddApiDTO {

    @NotNull(message = "请选择分类")
    private Integer categoryId;

    @NotBlank(message = "参数名不能为空")
    private String name;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
