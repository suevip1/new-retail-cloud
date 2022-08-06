package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.api.user.dto.UserCouponsApiDTO;
import com.zhihao.newretail.api.user.vo.UserCouponsApiVO;
import com.zhihao.newretail.user.dao.UserCouponsMapper;
import com.zhihao.newretail.user.pojo.UserCoupons;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserCouponsServiceImpl implements UserCouponsService {

    @Autowired
    private UserCouponsMapper userCouponsMapper;

    @Autowired
    private CouponsFeignService couponsFeignService;

    @Override
    public List<CouponsApiVO> listUserCouponsVOs(Integer userId) {
        List<UserCoupons> userCouponsList = userCouponsMapper.selectListByUserId(userId);
        Set<Integer> couponsIdSet = userCouponsList.stream().map(UserCoupons::getCouponsId).collect(Collectors.toSet());
        CouponsBatchApiDTO couponsBatchApiDTO = new CouponsBatchApiDTO();
        couponsBatchApiDTO.setCouponsIdSet(couponsIdSet);
        return couponsFeignService.listCouponsApiVOs(couponsBatchApiDTO);
    }

    @Override
    public CouponsApiVO getUserCouponsVO(Integer couponsId) {
        return couponsFeignService.getCouponsApiVO(couponsId);
    }

    @Override
    public List<UserCouponsApiVO> listUserCouponsApiVOs(Integer userId) {
        List<UserCoupons> userCouponsList = userCouponsMapper.selectListByUserId(userId);
        return userCouponsList.stream()
                .map(userCoupons -> {
                    UserCouponsApiVO userCouponsApiVO = new UserCouponsApiVO();
                    BeanUtils.copyProperties(userCoupons, userCouponsApiVO);
                    return userCouponsApiVO;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserCoupons(UserCouponsApiDTO userCouponsApiDTO) {
        UserCoupons userCoupons = userCouponsMapper.selectByCouponsId(userCouponsApiDTO.getCouponsId());
        userCoupons.setQuantity(userCoupons.getQuantity() - userCouponsApiDTO.getQuantity());
        userCoupons.setMqVersion(userCouponsApiDTO.getMqVersion());
        return userCouponsMapper.updateByPrimaryKeySelective(userCoupons);
    }

    @Override
    public UserCoupons getUserCouponsByCouponsId(Integer couponsId) {
        return userCouponsMapper.selectByCouponsId(couponsId);
    }

    @Override
    public void updateUserCoupons(UserCoupons userCoupons) {
        userCouponsMapper.updateByPrimaryKeySelective(userCoupons);
    }

}
