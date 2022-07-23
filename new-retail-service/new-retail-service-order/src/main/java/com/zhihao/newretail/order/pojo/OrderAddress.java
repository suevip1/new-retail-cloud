package com.zhihao.newretail.order.pojo;

public class OrderAddress {

    private Long orderId;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

    private Integer orderAddressSharding;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public Integer getOrderAddressSharding() {
        return orderAddressSharding;
    }

    public void setOrderAddressSharding(Integer orderAddressSharding) {
        this.orderAddressSharding = orderAddressSharding;
    }

    @Override
    public String toString() {
        return "OrderAddress{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", orderAddressSharding=" + orderAddressSharding +
                '}';
    }

}
