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
    public R getCart() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.getCartVO(userId);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @PostMapping("/cart")
    public R addCart(@Valid @RequestBody CartAddForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.addCart(userId, form);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @PutMapping("/cart")
    public R updateCart(@Valid @RequestBody CartUpdateForm form) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.updateCart(userId, form);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @DeleteMapping("/cart/{skuId}")
    public R deleteCart(@PathVariable Integer skuId) {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.deleteCart(userId, skuId);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @PutMapping("/cart/selectedAll")
    public R updateCartSelectedAll() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.updateCartSelectedAll(userId);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @PutMapping("/cart/notSelectedAll")
    public R updateCartNotSelectedAll() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.updateCartNotSelectedAll(userId);
        UserLoginContext.clean();

        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @GetMapping("/cart/quantity")
    public R getQuantity() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        Integer quantity = cartService.getQuantity(userId);
        UserLoginContext.clean();

        return R.ok().put("data", quantity);
    }

}
