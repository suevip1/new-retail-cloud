package com.zhihao.newretail.api.cart.fallback;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFeignFallback implements CartFeignService {

    @Override
    public List<CartApiVO> listCartApiVOS() {
        return null;
    }

    @Override
    public void deleteCartBySelected() {

    }

}
