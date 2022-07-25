package com.zhihao.newretail.user.service.impl;

import com.zhihao.newretail.api.coupons.dto.CouponsBatchApiDTO;
import com.zhihao.newretail.api.coupons.feign.CouponsFeignService;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.user.dao.UserCouponsMapper;
import com.zhihao.newretail.user.pojo.UserCoupons;
import com.zhihao.newretail.user.service.UserCouponsService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

        if (CollectionUtils.isEmpty(userCouponsList))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "暂无数据");

        Set<Integer> couponsIdSet = userCouponsList.stream().map(UserCoupons::getCouponsId).collect(Collectors.toSet());
        CouponsBatchApiDTO couponsBatchApiDTO = new CouponsBatchApiDTO();
        couponsBatchApiDTO.setCouponsIdSet(couponsIdSet);
        List<CouponsApiVO> couponsApiVOList = couponsFeignService.listCouponsApiVOs(couponsBatchApiDTO);

        if (CollectionUtils.isEmpty(couponsApiVOList))
            throw new ServiceException(HttpStatus.SC_NO_CONTENT, "暂无数据");
        return couponsApiVOList;
    }

    @Override
    public CouponsApiVO getUserCouponsVO(Integer couponsId) {
        return couponsFeignService.getCouponsApiVO(couponsId);
    }

}
