package com.zhihao.newretail.order.pojo;

import java.math.BigDecimal;

public class OrderItem extends OrderItemKey {

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Integer num;

    private Integer orderItemSharding;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOrderItemSharding() {
        return orderItemSharding;
    }

    public void setOrderItemSharding(Integer orderItemSharding) {
        this.orderItemSharding = orderItemSharding;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "price=" + price +
                ", totalPrice=" + totalPrice +
                ", num=" + num +
                ", orderItemSharding=" + orderItemSharding +
                '}';
    }

}
