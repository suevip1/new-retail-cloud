package com.zhihao.newretail.order.pojo.vo;

import java.math.BigDecimal;

public class OrderCouponsVO {

    private Integer id;

    private BigDecimal deno;

    private BigDecimal condition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDeno() {
        return deno;
    }

    public void setDeno(BigDecimal deno) {
        this.deno = deno;
    }

    public BigDecimal getCondition() {
        return condition;
    }

    public void setCondition(BigDecimal condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "OrderCouponsVO{" +
                "id=" + id +
                ", deno=" + deno +
                ", condition=" + condition +
                '}';
    }

}
