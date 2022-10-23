package com.zhihao.newretail.cart.feign;

import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class CartFeignClient {

    @Autowired
    private CartService cartService;

    @RequiresLogin
    @GetMapping("/cart")
    public List<CartApiVO> listCartApiVOS() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<CartApiVO> cartApiVOList = cartService.listCartApiVOs(userId);
        UserLoginContext.clean();
        return cartApiVOList;
    }

    @RequiresLogin
    @DeleteMapping("/cart")
    public void deleteCartBySelected() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        cartService.deleteCartBySelected(userId);
        UserLoginContext.clean();
    }

}
