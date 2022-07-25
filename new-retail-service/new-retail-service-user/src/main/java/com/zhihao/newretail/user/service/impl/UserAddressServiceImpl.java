package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.core.enums.DeleteEnum;
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
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddressVO> listUserAddressVOs(Integer userId) {
        List<UserAddress> userAddresses = userAddressMapper.selectListByUserId(userId);
        return userAddresses.stream()
                .filter(userAddress -> DeleteEnum.NOT_DELETE.getCode().equals(userAddress.getIsDelete()))
                .map(userAddress -> {
                    UserAddressVO userAddressVO = new UserAddressVO();
                    BeanUtils.copyProperties(userAddress, userAddressVO);
                    return userAddressVO;
                }).collect(Collectors.toList());
    }

    @Override
    public UserAddressVO getUserAddressVO(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);

        if (ObjectUtils.isEmpty(userAddress)
                || !userId.equals(userAddress.getUserId())
                || DeleteEnum.DELETE.getCode().equals(userAddress.getIsDelete()))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "收货地址不存在");

        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtils.copyProperties(userAddress, userAddressVO);
        return userAddressVO;
    }

    @Override
    public void updateUserAddress(Integer userId, Integer addressId, UserAddressUpdateForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setId(addressId);
        userAddress.setUserId(userId);
        int updateUserAddressRow = userAddressMapper.updateByPrimaryKeySelective(userAddress);

        if (updateUserAddressRow <= 0)
            throw new ServiceException("更新失败");
    }

    @Override
    public void deleteUserAddress(Integer userId, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);

        if (ObjectUtils.isEmpty(userAddress) || !userId.equals(userAddress.getUserId()))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "收货地址不存在");

        /* 使用逻辑删除 */
        userAddress.setIsDelete(DeleteEnum.DELETE.getCode());
        int deleteUserAddressRow = userAddressMapper.updateByPrimaryKeySelective(userAddress);

        if (deleteUserAddressRow <= 0)
            throw new ServiceException("删除失败");
    }

    @Override
    public void insertUserAddress(Integer userId, UserAddressAddForm form) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(form, userAddress);
        userAddress.setUserId(userId);
        int insertUserAddressRow = userAddressMapper.insertSelective(userAddress);

        if (insertUserAddressRow <= 0)
            throw new ServiceException("新增失败");
    }

}
