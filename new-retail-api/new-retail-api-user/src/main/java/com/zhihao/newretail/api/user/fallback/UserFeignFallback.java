package com.zhihao.newretail.api.user.fallback;

import com.zhihao.newretail.api.user.dto.UserApiDTO;
import com.zhihao.newretail.api.user.feign.UserFeignService;
import com.zhihao.newretail.api.user.vo.UserApiVO;
import com.zhihao.newretail.api.user.vo.UserInfoApiVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserFeignFallback implements UserFeignService {

    @Override
    public UserApiVO getUserApiVO(UserApiDTO userApiDTO) {
        return null;
    }

    @Override
    public UserInfoApiVO getUserInfoApiVO(Integer userId) {
        return null;
    }

    @Override
    public List<UserInfoApiVO> listUserInfoApiVOS(Set<Integer> userIdSet) {
        return null;
    }

}
