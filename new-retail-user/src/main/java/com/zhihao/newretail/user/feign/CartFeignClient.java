package com.zhihao.newretail.user.feign;

import com.zhihao.newretail.api.user.vo.CartApiVO;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.user.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<CartApiVO> cartApiVOList = cartService.listCartApiVOS(userId);
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
