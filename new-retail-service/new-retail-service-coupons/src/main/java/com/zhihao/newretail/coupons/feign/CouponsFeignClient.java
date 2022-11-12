package com.zhihao.newretail.coupons.feign;

import com.zhihao.newretail.api.coupons.dto.CouponsAddApiDTO;
import com.zhihao.newretail.api.coupons.dto.CouponsUpdateApiDTO;
import com.zhihao.newretail.api.coupons.vo.CouponsApiVO;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.coupons.service.CouponsService;
import com.zhihao.newretail.security.annotation.RequiresLogin;
import com.zhihao.newretail.security.context.UserLoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/feign")
public class CouponsFeignClient {

    @Autowired
    private CouponsService couponsService;

    @GetMapping("/coupons/{couponsId}")
    public CouponsApiVO getCouponsApiVO(@PathVariable Integer couponsId) {
        return couponsService.getCouponsApiVO(couponsId);
    }

    @RequiresLogin
    @GetMapping("/coupons/list")
    PageUtil<CouponsApiVO> listCouponsApiVOS(@RequestParam(required = false) Integer saleable,
                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageUtil<CouponsApiVO> pageData = couponsService.listCouponsApiVOS(saleable, pageNum, pageSize);
        UserLoginContext.sysClean();
        return pageData;
    }

    @PostMapping("/coupons/list")
    public List<CouponsApiVO> listCouponsApiVOS(@RequestBody Set<Integer> couponsIdSet) {
        return couponsService.listCouponsApiVOS(couponsIdSet);
    }

    @RequiresLogin
    @PostMapping("/coupons")
    Integer insertCoupons(@RequestBody CouponsAddApiDTO couponsAddApiDTO) {
        int insertRow = couponsService.insertCoupons(couponsAddApiDTO);
        UserLoginContext.sysClean();
        return insertRow;
    }

    @RequiresLogin
    @PutMapping("/coupons/{couponsId}")
    Integer updateCoupons(@PathVariable Integer couponsId, @RequestBody CouponsUpdateApiDTO couponsUpdateApiDTO) {
        int updateRow = couponsService.updateCoupons(couponsId, couponsUpdateApiDTO);
        UserLoginContext.sysClean();
        return updateRow;
    }

    @RequiresLogin
    @DeleteMapping("/coupons/{couponsId}")
    Integer deleteCoupons(@PathVariable Integer couponsId) {
        int deleteRow = couponsService.deleteCoupons(couponsId);
        UserLoginContext.sysClean();
        return deleteRow;
    }

}
