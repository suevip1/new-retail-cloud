package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.dto.UserRegisterDTO;
import com.zhihao.newretail.user.pojo.vo.UserInfoVO;

public interface UserService {

    /*
    * 新增用户
    * 基于用户名、密码注册
    * */
    int insertUser(UserRegisterDTO userRegisterDTO);

    /*
    * 获取用户基本信息
    * */
    UserApiVO getUserApiVO(User scope);

    /*
    * 用户基本信息
    * */
    UserInfoVO getUserInfoVO(Integer userId);

}
