package com.zhihao.newretail.product.pojo;

import lombok.Data;

@Data
public class Category {

    private Integer id;

    private String name;

    private Integer parentId;

    private Integer isParent;

    private Integer sort;

    private Integer isDelete;

}
