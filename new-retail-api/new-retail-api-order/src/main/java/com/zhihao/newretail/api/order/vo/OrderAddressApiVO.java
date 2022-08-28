package com.zhihao.newretail.api.order.vo;

public class OrderAddressApiVO {

    private Long orderId;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

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
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    @Override
    public String toString() {
        return "OrderAddressApiVO{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                '}';
    }

}
