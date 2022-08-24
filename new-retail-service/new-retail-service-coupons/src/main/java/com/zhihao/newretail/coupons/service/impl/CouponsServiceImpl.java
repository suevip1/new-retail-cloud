package com.zhihao.newretail.coupons.service.impl;

import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.coupons.dao.CouponsMapper;
import com.zhihao.newretail.coupons.pojo.Coupons;
import com.zhihao.newretail.coupons.service.CouponsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        CouponsApiVO couponsApiVO = new CouponsApiVO();
        BeanUtils.copyProperties(coupons, couponsApiVO);
        return couponsApiVO;
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet) {
        List<Coupons> couponsList = couponsMapper.selectListByCouponsIdSet(couponsIdSet);
        return couponsList.stream()
                .map(coupons -> {
                    CouponsApiVO couponsApiVO = new CouponsApiVO();
                    BeanUtils.copyProperties(coupons, couponsApiVO);
                    return couponsApiVO;
                }).collect(Collectors.toList());
    }

}
