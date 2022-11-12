package com.zhihao.newretail.api.product.dto;

import lombok.Data;

@Data
public class CategoryAddApiDTO {

    private String name;

    private Integer parentId;

}
