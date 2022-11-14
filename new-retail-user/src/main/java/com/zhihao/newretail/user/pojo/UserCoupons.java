package com.zhihao.newretail.user.pojo;

import lombok.Data;

@Data
public class UserCoupons {

    private Integer userId;

    private Integer couponsId;

    private Integer quantity;

    private Integer mqVersion;

}
