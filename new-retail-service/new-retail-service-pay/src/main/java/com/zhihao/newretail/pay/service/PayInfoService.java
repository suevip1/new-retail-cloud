package com.zhihao.newretail.pay.service;

import com.zhihao.newretail.pay.pojo.PayInfo;

/*
 * @Project: NewRetail-Cloud
 * @Author: Zhihao
 * @Email: cafebabe0508@163.com
 * */
public interface PayInfoService {

    /*
    * 获取支付信息
    * */
    PayInfo getPayInfo(Long orderId);

    /*
    * 保存支付信息
    * */
    void insertPayInfo(PayInfo payInfo);

    /*
    * 更新支付信息
    * */
    void updatePayInfo(PayInfo payInfo);

}
