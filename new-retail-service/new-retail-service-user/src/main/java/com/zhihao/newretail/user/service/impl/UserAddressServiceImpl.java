package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.user.vo.UserAddressApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.user.dao.UserAddressMapper;
import com.zhihao.newretail.user.form.UserAddressAddForm;
import com.zhihao.newretail.user.form.UserAddressUpdateForm;
import com.zhihao.newretail.user.pojo.UserAddress;
import com.zhihao.newretail.user.pojo.vo.UserAddressVO;
import com.zhihao.newretail.user.service.UserAddressService;
import org.apache.http.HttpStatus;
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
    public List<UserAddressVO> listUserAddressVOs(Integer userId) {
        List<UserAddress> userAddresses = userAddressMapper.selectListByUserId(userId);
        return userAddresses.stream()
                .map(userAddress -> {
                    UserAddressVO userAddressVO = new UserAddressVO();
                    BeanUtils.copyProperties(userAddress, userAddressVO);
                    return userAddressVO;
                }).collect(Collectors.toList());
    }

    @Override
    public UserAddressVO getUserAddressVO(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);

        if (ObjectUtils.isEmpty(userAddress) || !userId.equals(userAddress.getUserId())) {
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "收货地址不存在");
        }
        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtils.copyProperties(userAddress, userAddressVO);
        return userAddressVO;
    }

    @Override
    public void insertUserAddress(Integer userId, UserAddressAddForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setUserId(userId);
        int insertUserAddressRow = userAddressMapper.insertSelective(userAddress);

        if (insertUserAddressRow <= 0) {
            throw new ServiceException("新增收货地址失败");
        }
    }

    @Override
    public void updateUserAddress(Integer userId, Integer addressId, UserAddressUpdateForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        int updateUserAddressRow = userAddressMapper.updateByPrimaryKeySelective(userAddress);

        if (updateUserAddressRow <= 0) {
            throw new ServiceException("更新收货地址失败");
        }
    }

    @Override
    public void deleteUserAddress(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);

        if (ObjectUtils.isEmpty(userAddress) || !userId.equals(userAddress.getUserId())) {
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "收货地址不存在");
        }
        int deleteRow = userAddressMapper.deleteByPrimaryKey(addressId);
        if (deleteRow <= 0) {
            throw new ServiceException("删除收货地址失败");
        }
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
        UserAddressApiVO userAddressApiVO = new UserAddressApiVO();
        BeanUtils.copyProperties(userAddress, userAddressApiVO);
        return userAddressApiVO;
    }

    private UserAddressApiVO userAddress2UserAddressApiVO(UserAddress userAddress) {
        UserAddressApiVO userAddressApiVO = new UserAddressApiVO();
        BeanUtils.copyProperties(userAddress, userAddressApiVO);
        return userAddressApiVO;
    }

}
