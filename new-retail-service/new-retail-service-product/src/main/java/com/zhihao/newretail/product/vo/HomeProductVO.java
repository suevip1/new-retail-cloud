package com.zhihao.newretail.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HomeProductVO implements Serializable {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<ProductVO> productVOList;

}
