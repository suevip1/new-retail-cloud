package com.zhihao.newretail.api.cart.feign;

import com.zhihao.newretail.api.cart.fallback.CartFeignFallback;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Primary
@FeignClient(name = "new-retail-cart", path = "/cart", fallback = CartFeignFallback.class)
public interface CartFeignService {

    @GetMapping("/api/cart/list")
    List<CartApiVO> listCartApiVOS();

    @PutMapping("/api/cart")
    void deleteCartBySelected();

}
