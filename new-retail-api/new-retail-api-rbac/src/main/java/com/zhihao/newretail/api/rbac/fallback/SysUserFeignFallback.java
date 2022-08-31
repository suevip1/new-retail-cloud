package com.zhihao.newretail.api.rbac.fallback;

import com.zhihao.newretail.api.rbac.dto.SysUserApiDTO;
import com.zhihao.newretail.api.rbac.feign.SysUserFeignService;
import com.zhihao.newretail.api.rbac.vo.SysUserApiVO;
import org.springframework.stereotype.Component;

@Component
public class SysUserFeignFallback implements SysUserFeignService {

    @Override
    public SysUserApiVO getSysUserApiVO(SysUserApiDTO userApiDTO) {
        return null;
    }

}
