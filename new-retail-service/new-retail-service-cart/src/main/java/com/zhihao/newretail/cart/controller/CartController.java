package com.zhihao.newretail.cart.controller;

import com.zhihao.newretail.cart.form.CartAddForm;
import com.zhihao.newretail.cart.form.CartUpdateForm;
import com.zhihao.newretail.cart.pojo.vo.CartVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequiresLogin
    @PostMapping("/cart")
    public R addCart(@Valid @RequestBody CartAddForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        CartVO cartVO = cartService.addCart(userId, form);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @PutMapping("/cart")
    public R updateCart(@Valid @RequestBody CartUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo();
        CartVO cartVO = cartService.updateCart(userId, form);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

}
