package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.product.service.SlideService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlideFeignClient {

    @Autowired
    private SlideService slideService;

    @RequiresLogin
    @PostMapping("/api/slide")
    Integer addSlide(@RequestBody SlideAddApiDTO slideAddApiDTO) {
        int insertRow = slideService.insertSlide(slideAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

}
