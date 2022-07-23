package com.zhihao.newretail.user.pojo;

import java.math.BigDecimal;

public class UserLevel {

    private Integer id;

    private String level;

    private BigDecimal discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserLevel{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", discount=" + discount +
                '}';
    }

}
