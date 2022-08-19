package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.core.enums.DeleteEnum;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.SpuInfoMapper;
import com.zhihao.newretail.product.dao.SpuMapper;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.SpuInfo;
import com.zhihao.newretail.product.service.SpuService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSpu(SpuAddApiDTO spuAddApiDTO) {
        Spu spu = buildSpu(spuAddApiDTO);
        int insertSpuRow = spuMapper.insertSelective(spu);
        if (insertSpuRow > 0) {
            SpuInfo spuInfo = buildSpuInfo(spu.getId(), spuAddApiDTO);
            int insertSpuInfoRow = spuInfoMapper.insertSelective(spuInfo);
            if (insertSpuInfoRow <= 0) {
                throw new ServiceException("新增商品失败");
            }
        } else {
            throw new ServiceException("新增商品失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (ObjectUtils.isEmpty(spu) || DeleteEnum.DELETE.getCode().equals(spu.getIsDelete())) {
            throw new ServiceException(HttpStatus.SC_NOT_FOUND, "商品不存在");
        }
        spu = buildSpu(spuUpdateApiDTO);
        int updateSpuRow = spuMapper.updateByPrimaryKeySelective(spu);
        if (updateSpuRow > 0) {
            SpuInfo spuInfo = buildSpuInfo(spuId, spuUpdateApiDTO);
            int updateSpuInfoRow = spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
            if (updateSpuInfoRow <= 0) {
                throw new ServiceException("修改商品失败");
            }
        } else {
            throw new ServiceException("修改商品失败");
        }
    }

    private Spu buildSpu(SpuAddApiDTO spuAddApiDTO) {
        Spu spu = new Spu();
        spu.setCategoryId(spuAddApiDTO.getCategoryId());
        spu.setTitle(spuAddApiDTO.getTitle());
        spu.setSubTitle(spuAddApiDTO.getSubTitle());
        return spu;
    }

    private SpuInfo buildSpuInfo(Integer spuId, SpuAddApiDTO spuAddApiDTO) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setSpuId(spuId);
        spuInfo.setShowImage(spuAddApiDTO.getShowImage());
        spuInfo.setSliderImage(spuAddApiDTO.getSliderImage());
        spuInfo.setDetailTitle(spuAddApiDTO.getDetailTitle());
        spuInfo.setDetailPram(spuAddApiDTO.getDetailPram());
        spuInfo.setDetailImage(spuAddApiDTO.getDetailImage());
        return spuInfo;
    }

}
