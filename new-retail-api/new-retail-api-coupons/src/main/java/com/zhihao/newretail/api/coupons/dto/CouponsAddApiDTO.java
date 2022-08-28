package com.zhihao.newretail.api.coupons.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CouponsAddApiDTO {

    private BigDecimal deno;

    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    private Integer maxNum;

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

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

}
