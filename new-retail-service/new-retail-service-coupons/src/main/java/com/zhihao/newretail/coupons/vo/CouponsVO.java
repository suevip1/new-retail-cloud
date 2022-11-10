package com.zhihao.newretail.coupons.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CouponsVO {

    private Integer id;

    private BigDecimal deno;

    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    private Integer isSaleable;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIsSaleable() {
        return isSaleable;
    }

    public void setIsSaleable(Integer isSaleable) {
        this.isSaleable = isSaleable;
    }

    @Override
    public String toString() {
        return "CouponsVO{" +
                "id=" + id +
                ", deno=" + deno +
                ", condition=" + condition +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isSaleable=" + isSaleable +
                '}';
    }

}
