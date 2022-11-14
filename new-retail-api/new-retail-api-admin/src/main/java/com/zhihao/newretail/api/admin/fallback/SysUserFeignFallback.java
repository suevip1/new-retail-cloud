package com.zhihao.newretail.api.admin.fallback;

import com.zhihao.newretail.api.admin.feign.SysUserFeignService;
import com.zhihao.newretail.api.admin.vo.SysUserApiVO;
import org.springframework.stereotype.Component;

@Component
public class SysUserFeignFallback implements SysUserFeignService {

    @Override
    public SysUserApiVO getSysUserApiVO(String username) {
        return null;
    }

}
