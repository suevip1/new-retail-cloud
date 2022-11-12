package com.zhihao.newretail.product.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Slide {

    private Integer id;

    private Integer spuId;

    private String slideImage;

    private Integer isDelete;

    private Date createTime;

}
