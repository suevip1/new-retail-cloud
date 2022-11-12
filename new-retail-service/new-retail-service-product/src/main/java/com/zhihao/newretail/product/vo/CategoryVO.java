package com.zhihao.newretail.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<CategoryVO> categoryVOList;

}
