package com.zhihao.newretail.cart.feign;

import com.zhihao.newretail.api.cart.feign.CartFeignService;
import com.zhihao.newretail.api.cart.vo.CartApiVO;
import com.zhihao.newretail.cart.service.CartService;
import com.zhihao.newretail.security.UserLoginContext;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartFeignController implements CartFeignService {

    @Autowired
    private CartService cartService;

    @RequiresLogin
    @Override
    public List<CartApiVO> listCartApiVOs() {
        Integer userId = UserLoginContext.getUserLoginInfo().getUserId();
        List<CartApiVO> cartApiVOList = cartService.listCartApiVOs(userId);
        UserLoginContext.clean();
        return cartApiVOList;
    }

}
