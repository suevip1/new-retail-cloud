package com.zhihao.newretail.product.controller;

import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.product.pojo.vo.HomeProductVO;
import com.zhihao.newretail.product.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public R home() {
        List<HomeProductVO> homeProductVOList = homeService.listHomeProductVOS();
        return R.ok().put("data", homeProductVOList);
    }

}
