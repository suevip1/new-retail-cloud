package com.zhihao.newretail.user.pojo.vo;

public class UserAddressVO {

    private Integer id;

    private String name;

    private String tel;

    private String address;

    private String addressDetail;

    private Integer isPrime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIsPrime() {
        return isPrime;
    }

    public void setIsPrime(Integer isPrime) {
        this.isPrime = isPrime;
    }

    @Override
    public String toString() {
        return "UserAddressVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", isPrime=" + isPrime +
                '}';
    }

}
