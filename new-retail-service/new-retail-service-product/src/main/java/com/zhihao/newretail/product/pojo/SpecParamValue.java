package com.zhihao.newretail.product.pojo;

public class SpecParamValue {

    private Integer id;

    private Integer specParamKeyId;

    private String name;

    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecParamKeyId() {
        return specParamKeyId;
    }

    public void setSpecParamKeyId(Integer specParamKeyId) {
        this.specParamKeyId = specParamKeyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "SpecParamValue{" +
                "id=" + id +
                ", specParamKeyId=" + specParamKeyId +
                ", name='" + name + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }

}
