package com.zhihao.newretail.coupons.service.impl;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.coupons.dao.CouponsMapper;
import com.zhihao.newretail.coupons.pojo.Coupons;
import com.zhihao.newretail.coupons.service.CouponsService;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    private CouponsMapper couponsMapper;

    @Override
    public CouponsApiVO getCouponsApiVO(Integer couponsId) {
        Coupons coupons = couponsMapper.selectByPrimaryKey(couponsId);

        if (ObjectUtils.isEmpty(coupons))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "优惠券不存在");

        CouponsApiVO couponsApiVO = new CouponsApiVO();
        BeanUtils.copyProperties(coupons, couponsApiVO);
        return couponsApiVO;
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOs(Set<Integer> couponsIdSet) {
        if (CollectionUtils.isEmpty(couponsIdSet))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "暂无数据");

        List<Coupons> couponsList = couponsMapper.selectListByCouponsIdSet(couponsIdSet);

        if (CollectionUtils.isEmpty(couponsList))
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "暂无数据");

        return couponsList.stream()
                .filter(coupons -> DeleteEnum.NOT_DELETE.getCode().equals(coupons.getIsDelete()))
                .map(coupons -> {
                    CouponsApiVO couponsApiVO = new CouponsApiVO();
                    BeanUtils.copyProperties(coupons, couponsApiVO);
                    return couponsApiVO;
                }).collect(Collectors.toList());
    }

}
