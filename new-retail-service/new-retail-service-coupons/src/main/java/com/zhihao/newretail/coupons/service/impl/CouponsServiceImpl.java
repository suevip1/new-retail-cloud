package com.zhihao.newretail.coupons.service.impl;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
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
    public PageUtil<CouponsApiVO> listCouponsApiVOS(Integer saleable, Integer pageNum, Integer pageSize) {
        PageUtil<CouponsApiVO> pageUtil = new PageUtil<>();
        int count = couponsMapper.countBySaleable(saleable);
        pageUtil.setPageNum(pageNum);
        pageUtil.setPageSize(pageSize);
        pageUtil.setTotal((long) count);
        List<CouponsApiVO> couponsApiVOList = couponsMapper.selectListBySaleable(saleable, pageNum, pageSize).stream().map(this::coupons2CouponsApiVO).collect(Collectors.toList());
        pageUtil.setList(couponsApiVOList);
        return pageUtil;
    }

    @Override
    public List<CouponsApiVO> listCouponsApiVOS(Set<Integer> couponsIdSet) {
        List<Coupons> couponsList = couponsMapper.selectListByCouponsIdSet(couponsIdSet);
        return couponsList.stream()
                .map(this::coupons2CouponsApiVO).collect(Collectors.toList());
    }

    @Override
    public int insertCoupons(CouponsAddApiDTO couponsAddApiDTO) {
        Coupons coupons = new Coupons();
        BeanUtils.copyProperties(couponsAddApiDTO, coupons);
        return couponsMapper.insertSelective(coupons);
    }

    private CouponsApiVO coupons2CouponsApiVO(Coupons coupons) {
        CouponsApiVO couponsApiVO = new CouponsApiVO();
        BeanUtils.copyProperties(coupons, couponsApiVO);
        return couponsApiVO;
    }

}
