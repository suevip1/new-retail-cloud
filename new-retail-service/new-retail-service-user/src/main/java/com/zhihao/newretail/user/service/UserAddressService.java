package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.vo.UserAddressVO;

import java.util.List;
import java.util.Set;

public interface UserAddressService {

    /*
    * 用户收货地址列表
    * */
    List<UserAddressVO> listUserAddressVOS(Integer userId);

    /*
    * 用户收货地址
    * */
    UserAddressVO getUserAddressVO(Integer userId, Integer addressId);

    /*
    * 新增收货地址
    * */
    void insertUserAddress(Integer userId, UserAddressAddForm form);

    /*
    * 更新收货地址信息
    * */
    void updateUserAddress(Integer userId, Integer addressId, UserAddressUpdateForm form);

    /*
    * 删除收货地址
    * */
    void deleteUserAddress(Integer userId, Integer addressId);

    /*
    * 获取收货地址列表
    * */
    List<UserAddressApiVO> listUserAddressApiVOS(Integer userId);

    /*
    * 批量获取收货地址
    * */
    List<UserAddressApiVO> listUserAddressApiVOS(Set<Integer> userIdSet);

    /*
    * 获取收货地址信息
    * */
    UserAddressApiVO getUserAddressApiVO(Integer addressId);

}
