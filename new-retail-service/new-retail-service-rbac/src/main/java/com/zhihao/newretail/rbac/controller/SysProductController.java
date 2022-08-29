package com.zhihao.newretail.rbac.controller;

import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import com.zhihao.newretail.rbac.context.SysUserTokenContext;
import com.zhihao.newretail.rbac.form.SkuForm;
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
    public R spuInfo(@PathVariable Integer spuId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        SpuApiVO spuApiVO = sysProductService.getSpuApiVO(spuId);
        UserLoginContext.sysClean();
        return R.ok().put("data", spuApiVO);
    }

    @RequiresLogin
    @PostMapping("/spu")
    public R spu(@Valid @RequestBody SpuForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer insertRow = sysProductService.addSpu(form);
        UserLoginContext.sysClean();
        if (insertRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (insertRow <= 0) {
            throw new ServiceException("新增商品失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/spu/{spuId}")
    public R spu(@PathVariable Integer spuId, @Valid @RequestBody SpuForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer updateRow = sysProductService.updateSpu(spuId, form);
        UserLoginContext.sysClean();
        if (updateRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (updateRow <= 0) {
            throw new ServiceException("修改商品失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/spu/{spuId}")
    public R spu(@PathVariable Integer spuId) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        Integer deleteRow = sysProductService.deleteSpu(spuId);
        UserLoginContext.sysClean();
        if (deleteRow == null) {
            throw new ServiceException("商品服务繁忙");
        }
        if (deleteRow <= 0) {
            throw new ServiceException("删除商品失败");
        }
        return R.ok();
    }

    @RequiresLogin
    @PostMapping("/sku")
    public R skuAdd(@Valid @RequestBody SkuForm form) {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.addSku(form);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @PutMapping("/sku/{skuId}")
    public R skuUpdate(@PathVariable Integer skuId, @Valid @RequestBody SkuForm form) throws ExecutionException, InterruptedException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.updateSku(skuId, form);
        UserLoginContext.sysClean();
        return R.ok();
    }

    @RequiresLogin
    @DeleteMapping("/sku/{skuId}")
    public R skuDelete(@PathVariable Integer skuId) throws ExecutionException, InterruptedException {
        String userToken = UserLoginContext.getSysUserLoginVO().getUserToken();
        SysUserTokenContext.setUserToken(userToken);
        sysProductService.deleteSku(skuId);
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
