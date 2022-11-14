package com.zhihao.newretail.api.user.feign;

import com.zhihao.newretail.api.user.fallback.CartFeignFallback;
import com.zhihao.newretail.api.user.vo.CartApiVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Primary
@FeignClient(name = "new-retail-user", path = "/new-retail-user", fallback = CartFeignFallback.class)
public interface CartFeignService {

    @GetMapping("/feign/cart")
    List<CartApiVO> listCartApiVOS();

    @DeleteMapping("/feign/cart")
    void deleteCartBySelected();

}
