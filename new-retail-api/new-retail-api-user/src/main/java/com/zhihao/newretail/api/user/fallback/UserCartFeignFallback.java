package com.zhihao.newretail.api.user.fallback;

import com.zhihao.newretail.api.user.feign.UserCartFeignService;
import com.zhihao.newretail.api.user.vo.CartApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCartFeignFallback implements UserCartFeignService {

    @Override
    public List<CartApiVO> listCartApiVOS() {
        return null;
    }

    @Override
    public void deleteCartBySelected() {}

}
