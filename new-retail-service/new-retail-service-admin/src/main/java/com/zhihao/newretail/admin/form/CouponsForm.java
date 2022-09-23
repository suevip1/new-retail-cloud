package com.zhihao.newretail.admin.form;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class CouponsForm {

    @NotNull(message = "优惠券面值不能为空")
    private BigDecimal deno;

    @NotNull(message = "优惠券使用条件不能为空")
    private BigDecimal condition;

    private Date startDate;

    private Date endDate;

    @NotNull(message = "优惠券发放数量不能为空")
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
