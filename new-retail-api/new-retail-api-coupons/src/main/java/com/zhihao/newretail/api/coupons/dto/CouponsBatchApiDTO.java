package com.zhihao.newretail.api.coupons.dto;

import java.util.Set;

public class CouponsBatchApiDTO {

    private Set<Integer> couponsIdSet;

    public CouponsBatchApiDTO() {
    }

    public CouponsBatchApiDTO(Set<Integer> couponsIdSet) {
        this.couponsIdSet = couponsIdSet;
    }

    public Set<Integer> getCouponsIdSet() {
        return couponsIdSet;
    }

    public void setCouponsIdSet(Set<Integer> couponsIdSet) {
        this.couponsIdSet = couponsIdSet;
    }

}
