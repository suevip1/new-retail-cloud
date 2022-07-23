package com.zhihao.newretail.pay.dao;

import com.zhihao.newretail.pay.pojo.PayInfo;
import com.zhihao.newretail.pay.pojo.PayInfoKey;

public interface PayInfoMapper {

    int deleteByPrimaryKey(PayInfoKey key);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(PayInfoKey key);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

}
