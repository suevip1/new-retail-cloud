package com.zhihao.newretail.user.service;

import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import com.zhihao.newretail.user.pojo.User;
import com.zhihao.newretail.user.pojo.dto.UpdateNickNameDTO;
import com.zhihao.newretail.user.pojo.dto.UserRegisterDTO;
import com.zhihao.newretail.user.pojo.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    /*
    * 新增用户
    * 基于用户名、密码注册
    * */
    Integer insertUser(UserRegisterDTO userRegisterDTO);

    /*
    * 获取用户基本信息
    * */
    UserApiVO getUserApiVO(User scope);

    /*
    * 用户基本信息
    * */
    UserInfoVO getUserInfoVO(Integer userId);

    /*
    * 用户修改头像
    * */
    UserInfoVO updateUserInfo(Integer userId, MultipartFile file) throws IOException;

    /*
    * 用户修改昵称
    * */
    UserInfoVO updateUserInfo(Integer userId, UpdateNickNameDTO updateNickNameDTO);

    /*
    * 批量获取用户信息
    * */
    List<UserInfoApiVO> listUserInfoApiVOS(Set<Integer> userIdSet);

}
