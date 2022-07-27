package com.zhihao.newretail.cart.controller;

import com.zhihao.newretail.cart.pojo.vo.CartVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequiresLogin
    @GetMapping("/cart")
    public R getCartVO() {
        Integer userId = UserLoginContext.getUserLoginInfo();
        CartVO cartVO = cartService.getCartVO(userId);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

}
