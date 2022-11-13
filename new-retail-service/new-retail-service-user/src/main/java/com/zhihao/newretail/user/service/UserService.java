package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.user.form.UserRegisterForm;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.form.UpdateNickNameForm;
import com.zhihao.newretail.user.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    /*
    * 新增用户(基于用户名、密码注册注册)
    * */
    int insertUser(UserRegisterForm form);

    /*
    * 获取用户信息
    * */
    UserInfoVO getUserInfoVO(Integer userId);

    /*
    * 用户修改头像
    * */
    int updateUserPhoto(Integer userId, MultipartFile file) throws IOException;

    /*
    * 用户修改昵称
    * */
    int updateUserNickName(Integer userId, UpdateNickNameForm form);

    /*
     * 获取用户基本信息(Feign)
     * */
    UserApiVO getUserApiVO(User scope);

    /*
     * 支付宝用户id获取用户信息(Feign)
     * */
    UserApiVO getUserApiVOByAliPayUserId(UserApiDTO userApiDTO);

    /*
    * 获取用户信息(Feign)
    * */
    UserInfoApiVO getUserInfoApiVO(Integer userId);

    /*
    * 批量获取用户信息(Feign)
    * */
    List<UserInfoApiVO> listUserInfoApiVOS(Set<Integer> userIdSet);

}
