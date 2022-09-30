package com.zhihao.newretail.pay.service;

import com.zhihao.newretail.api.pay.vo.PayInfoApiVO;
import com.zhihao.newretail.core.util.PageUtil;
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
    * 支付信息列表
    * */
    PageUtil<PayInfoApiVO> listPayInfoApiVOS(Long orderId, Integer userId, Integer payPlatform,
                                             Integer status, Integer platformNumber, Integer pageNum, Integer pageSize);

    /*
    * 保存支付信息
    * */
    void insertPayInfo(PayInfo payInfo);

    /*
    * 更新支付信息
    * */
    int updatePayInfo(PayInfo payInfo);

}
