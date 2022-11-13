package com.zhihao.newretail.admin.controller;

import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.admin.context.SysUserTokenContext;
import com.zhihao.newretail.admin.form.SlideForm;
import com.zhihao.newretail.admin.service.SysSlideService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class SysSlideController {

    @Autowired
    private SysSlideService sysSlideService;

    @RequiresLogin
    @GetMapping("/slide/list")
    public R slide(@RequestParam(required = false) Integer slideId,
                   @RequestParam(defaultValue = "1") Integer pageNum,
                   @RequestParam(defaultValue = "10") Integer pageSize) {
        if (pageNum == 0 || pageSize == 0) {
            throw new ServiceException("分页参数不能为0");
        }
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        PageUtil<SlideApiVO> pageData = sysSlideService.listSlideApiVOS(slideId, pageNum, pageSize);
        UserLoginContext.sysClean();
        return R.ok().put("data", pageData);
    }

    @RequiresLogin
    @PostMapping("/slide")
    public R slide(@Valid @RequestBody SlideForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = sysSlideService.insertSlide(form);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增轮播图失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/slide/{slideId}")
    public R slide(@PathVariable Integer slideId, @Valid @RequestBody SlideForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = sysSlideService.updateSlide(slideId, form);
        UserLoginContext.sysClean();
        if (updateRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (updateRow <= 0) {
            throw new ServiceException("修改轮播图失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/slide/{slideId}")
    public R slide(@PathVariable Integer slideId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = sysSlideService.deleteSlide(slideId);
        UserLoginContext.sysClean();
        if (deleteRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (deleteRow <= 0) {
            throw new ServiceException("删除轮播图失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PostMapping("/slide/upload/image")
    public R uploadSlideImage(@RequestPart MultipartFile file) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        String imageUrl = sysSlideService.uploadSlideImage(file);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrl);
    }

}
