package com.zhihao.newretail.api.user.fallback;

import com.zhihao.newretail.api.user.feign.CartFeignService;
import com.zhihao.newretail.api.user.vo.CartApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFeignFallback implements CartFeignService {

    @Override
    public List<CartApiVO> listCartApiVOS() {
        return null;
    }

    @Override
    public void deleteCartBySelected() {}

}
