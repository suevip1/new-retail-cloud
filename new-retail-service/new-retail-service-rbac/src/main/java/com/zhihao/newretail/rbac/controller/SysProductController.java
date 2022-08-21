package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.form.SpuForm;
import com.zhihao.newretail.rbac.service.SysProductService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class SysProductController {

    @Autowired
    private SysProductService sysProductService;

    @RequiresLogin
    @GetMapping("/spu/{spuId}")
    public R spu(@PathVariable Integer spuId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        SpuApiVO spuApiVO = sysProductService.getSpuApiVO(spuId);
        UserLoginContext.sysClean();
        return R.ok().put("data", spuApiVO);
    }

    @RequiresLogin
    @PostMapping("/spu")
    public R spuAdd(@Valid @RequestBody SpuForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.addSpu(form);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/spu/{spuId}")
    public R spuUpdate(@PathVariable Integer spuId, @Valid @RequestBody SpuForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.updateSpu(spuId, form);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/spu/{spuId}")
    public R spuDelete(@PathVariable Integer spuId) throws ExecutionException, InterruptedException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.deleteSpu(spuId);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @PostMapping("/spu/upload/image")
    public R uploadSpuImage(@RequestPart MultipartFile file) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        String imageUrl = sysProductService.uploadSpuImage(file);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrl);
    }

    @RequiresLogin
    @PostMapping("/sku/upload/image")
    public R uploadSkuImage(@RequestPart MultipartFile file) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        String imageUrl = sysProductService.uploadSkuImage(file);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrl);
    }

    @RequiresLogin
    @PostMapping("/spu/upload/slider")
    public R uploadSliderImage(@RequestPart MultipartFile[] files) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        List<String> imageUrlList = sysProductService.uploadSpuSliderImage(files);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrlList);
    }

    @RequiresLogin
    @PostMapping("/spu/upload/detail")
    public R uploadDetailImage(@RequestPart MultipartFile[] files) throws IOException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        List<String> imageUrlList = sysProductService.uploadSpuDetailImage(files);
        UserLoginContext.sysClean();
        return R.ok().put("data", imageUrlList);
    }

}
