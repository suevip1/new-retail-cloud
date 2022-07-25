package com.zhihao.newretail.user.service;

import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;

import java.util.List;

public interface UserAddressService {

    /*
    * 用户收货地址列表
    * */
    List<UserAddressVO> listUserAddressVOs(Integer userId);

    /*
    * 用户收货地址
    * */
    UserAddressVO getUserAddressVO(Integer userId, Integer addressId);

    /*
    * 新增收货地址
    * */
    void insertUserAddress(Integer userId, UserAddressAddForm form);

}
