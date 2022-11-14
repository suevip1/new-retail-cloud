package com.zhihao.newretail.user.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import com.zhihao.newretail.user.form.CartAddForm;
import com.zhihao.newretail.user.form.CartUpdateForm;
import com.zhihao.newretail.user.service.CartService;
import com.zhihao.newretail.user.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        if (!CollectionUtils.isEmpty(cartVO.getCartProductVOList())) {
            return R.ok().put("data", cartVO);
        } else {
            return R.ok("购物车为空").put("data", cartVO);
        }
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
    @PutMapping("/cart/unSelectedAll")
    public R updateCartUnSelectedAll() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        CartVO cartVO = cartService.updateCartNotSelectedAll(userId);
        UserLoginContext.clean();
        return R.ok().put("data", cartVO);
    }

    @RequiresLogin
    @GetMapping("/cart/quantity")
    public R cartQuantity() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        Integer quantity = cartService.getQuantity(userId);
        UserLoginContext.clean();
        return R.ok().put("data", quantity);
    }

}
