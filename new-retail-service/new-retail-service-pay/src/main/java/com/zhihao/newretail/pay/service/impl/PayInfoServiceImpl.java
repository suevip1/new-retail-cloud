package com.zhihao.newretail.pay.service.impl;

import com.zhihao.newretail.pay.dao.PayInfoMapper;
import com.zhihao.newretail.pay.pojo.PayInfo;
import com.zhihao.newretail.pay.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertPayInfo(PayInfo payInfo) {
        payInfoMapper.insertSelective(payInfo);
    }

    @Override
    public void updatePayInfo(PayInfo payInfo) {
        payInfoMapper.updateByPrimaryKeySelective(payInfo);
    }

}
