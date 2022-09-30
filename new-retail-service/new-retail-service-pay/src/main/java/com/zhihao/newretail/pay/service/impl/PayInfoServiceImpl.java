package com.zhihao.newretail.pay.service.impl;

import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.pay.dao.PayInfoMapper;
import com.zhihao.newretail.pay.pojo.PayInfo;
import com.zhihao.newretail.pay.service.PayInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    public PayInfo getPayInfo(Long orderId) {
        return payInfoMapper.selectByOrderId(orderId);
    }

    @Override
    public PageUtil<PayInfoApiVO> listPayInfoApiVOS(Long orderId, Integer userId, Integer payPlatform,
                                                    Integer status, Integer platformNumber, Integer pageNum, Integer pageSize) {
        PageUtil<PayInfoApiVO> pageUtil = new PageUtil<>();
        int count = payInfoMapper.countByRecord(orderId, userId, payPlatform, status, platformNumber);
        pageUtil.setPageNum(pageNum);
        pageUtil.setPageSize(pageSize);
        pageUtil.setTotal((long) count);
        List<PayInfo> payInfoList = payInfoMapper.selectListByRecord(orderId, userId, payPlatform, status, platformNumber, pageNum, pageSize);
        if (!CollectionUtils.isEmpty(payInfoList)) {
            List<PayInfoApiVO> payInfoApiVOList = payInfoList.stream().map(this::PayInfo2PayInfoApiVO).collect(Collectors.toList());
            pageUtil.setList(payInfoApiVOList);
        }
        return pageUtil;
    }

    @Override
    public void insertPayInfo(PayInfo payInfo) {
        payInfoMapper.insertSelective(payInfo);
    }

    @Override
    public int updatePayInfo(PayInfo payInfo) {
        return payInfoMapper.updateByPrimaryKeySelective(payInfo);
    }

    private PayInfoApiVO PayInfo2PayInfoApiVO(PayInfo payInfo) {
        PayInfoApiVO payInfoApiVO = new PayInfoApiVO();
        BeanUtils.copyProperties(payInfo, payInfoApiVO);
        return payInfoApiVO;
    }

}
