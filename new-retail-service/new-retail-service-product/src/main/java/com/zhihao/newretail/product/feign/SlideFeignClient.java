package com.zhihao.newretail.product.feign;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.service.SlideService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SlideFeignClient {

    @Autowired
    private SlideService slideService;

    @RequiresLogin
    @GetMapping("/api/slide/list")
    PageUtil<SlideApiVO> listSlideApiVOS(@RequestParam(required = false) Integer slideId,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        PageUtil<SlideApiVO> pageData = slideService.listSlideApiVOS(slideId, pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

    @RequiresLogin
    @PostMapping("/api/slide")
    Integer addSlide(@RequestBody SlideAddApiDTO slideAddApiDTO) {
        int insertRow = slideService.insertSlide(slideAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/api/slide/{slideId}")
    Integer updateSlide(@PathVariable Integer slideId, @RequestBody SlideUpdateApiDTO slideUpdateApiDTO) {
        int updateRow = slideService.updateSlide(slideId, slideUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/api/slide/{slideId}")
    Integer deleteSlide(@PathVariable Integer slideId) {
        int deleteRow = slideService.deleteSlide(slideId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
