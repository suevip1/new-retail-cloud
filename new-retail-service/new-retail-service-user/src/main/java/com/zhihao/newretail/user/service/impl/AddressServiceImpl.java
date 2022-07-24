package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.user.dao.UserAddressMapper;
import com.zhihao.newretail.user.pojo.UserAddress;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;
import com.zhihao.newretail.user.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressVO> listUserAddressVOs(Integer userId) {
        List<UserAddress> userAddresses = userAddressMapper.selectListByUserId(userId);
        return userAddresses.stream().map(userAddress -> {
            UserAddressVO userAddressVO = new UserAddressVO();
            BeanUtils.copyProperties(userAddress, userAddressVO);
            return userAddressVO;
        }).collect(Collectors.toList());
    }

}
