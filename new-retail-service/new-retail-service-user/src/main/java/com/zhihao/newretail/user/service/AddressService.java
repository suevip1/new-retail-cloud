package com.zhihao.newretail.user.service;

import com.zhihao.newretail.user.pojo.vo.UserAddressVO;

import java.util.List;

public interface AddressService {

    /*
    * 用户收货地址列表
    * */
    List<UserAddressVO> listUserAddressVOs(Integer userId);

}
