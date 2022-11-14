package com.zhihao.newretail.user.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserLevel {

    private Integer id;

    private String level;

    private BigDecimal discount;

}
