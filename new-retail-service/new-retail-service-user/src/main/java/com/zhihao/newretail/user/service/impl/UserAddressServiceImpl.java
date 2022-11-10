package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.user.dao.UserAddressMapper;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.pojo.UserAddress;
import com.zhihao.newretail.user.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressVO> listUserAddressVOS(Integer userId) {
        return userAddressMapper.selectListByUserId(userId).stream().map(this::userAddress2UserAddressVO).collect(Collectors.toList());
    }

    @Override
    public UserAddressVO getUserAddressVO(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (ObjectUtils.isEmpty(userAddress) || !userId.equals(userAddress.getUserId())) {
            throw new ServiceException("收货地址不存在");
        }
        return userAddress2UserAddressVO(userAddress);
    }

    @Override
    public int insertUserAddress(Integer userId, UserAddressAddForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setUserId(userId);
        return userAddressMapper.insertSelective(userAddress);
    }

    @Override
    public int updateUserAddress(Integer userId, Integer addressId, UserAddressUpdateForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public int deleteUserAddress(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (ObjectUtils.isEmpty(userAddress) || !userId.equals(userAddress.getUserId())) {
            throw new ServiceException("收货地址不存在");
        }
        return userAddressMapper.deleteByPrimaryKey(addressId);
    }

    @Override
    public List<UserAddressApiVO> listUserAddressApiVOS(Integer userId) {
        return userAddressMapper.selectListByUserId(userId).stream().map(this::userAddress2UserAddressApiVO).collect(Collectors.toList());
    }

    @Override
    public List<UserAddressApiVO> listUserAddressApiVOS(Set<Integer> userIdSet) {
        return userAddressMapper.selectListByUserIdSet(userIdSet).stream().map(this::userAddress2UserAddressApiVO).collect(Collectors.toList());
    }

    @Override
    public UserAddressApiVO getUserAddressApiVO(Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        return userAddress2UserAddressApiVO(userAddress);
    }

    private UserAddressVO userAddress2UserAddressVO(UserAddress userAddress) {
        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtils.copyProperties(userAddress, userAddressVO);
        return userAddressVO;
    }

    private UserAddressApiVO userAddress2UserAddressApiVO(UserAddress userAddress) {
        UserAddressApiVO userAddressApiVO = new UserAddressApiVO();
        BeanUtils.copyProperties(userAddress, userAddressApiVO);
        return userAddressApiVO;
    }

}
