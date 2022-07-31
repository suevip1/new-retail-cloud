package com.zhihao.newretail.api.cart.feign;

import com.zhihao.newretail.api.cart.vo.CartApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "new-retail-cart", path = "/cart")
public interface CartFeignService {

    @GetMapping("/api/cart")
    List<CartApiVO> listCartApiVOs();

}
