package com.zhihao.newretail.user.form;

import javax.validation.constraints.NotBlank;

public class UserAddressAddForm {

    @NotBlank(message = "收货人姓名不能为空")
    private String name;

    @NotBlank(message = "收货人手机号码不能为空")
    private String tel;

    @NotBlank(message = "收货人地址不能为空")
    private String address;

    private String addressDetail;

    private Integer isPrime;

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

    public Integer getIsPrime() {
        return isPrime;
    }

    public void setIsPrime(Integer isPrime) {
        this.isPrime = isPrime;
    }

    @Override
    public String toString() {
        return "UserAddressAddForm{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", isPrime=" + isPrime +
                '}';
    }

}
