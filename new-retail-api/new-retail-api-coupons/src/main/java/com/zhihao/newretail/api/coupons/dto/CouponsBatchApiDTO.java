package com.zhihao.newretail.api.coupons.dto;

import java.util.Set;

public class CouponsBatchApiDTO {

    Set<Integer> couponsIdSet;

    public Set<Integer> getCouponsIdSet() {
        return couponsIdSet;
    }

    public void setCouponsIdSet(Set<Integer> couponsIdSet) {
        this.couponsIdSet = couponsIdSet;
    }

    @Override
    public String toString() {
        return "CouponsBatchApiDTO{" +
                "couponsIdSet=" + couponsIdSet +
                '}';
    }

}
