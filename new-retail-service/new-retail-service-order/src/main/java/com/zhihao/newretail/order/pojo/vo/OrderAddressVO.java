package com.zhihao.newretail.order.pojo.vo;

public class OrderAddressVO {

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

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
        return "OrderAddressVO{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                '}';
    }

}
