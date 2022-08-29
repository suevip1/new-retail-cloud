package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SpuAddApiDTO;
import com.zhihao.newretail.api.product.dto.SpuUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SpuApiVO;
import com.zhihao.newretail.api.product.vo.SpuInfoApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.SpuInfoMapper;
import com.zhihao.newretail.product.dao.SpuMapper;
import com.zhihao.newretail.product.pojo.Spu;
import com.zhihao.newretail.product.pojo.SpuInfo;
import com.zhihao.newretail.product.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public SpuApiVO getSpuApiVO(Integer spuId) {
        Spu spu = spuMapper.selectSpuSpuInfoByPrimaryKey(spuId);
        return spu2SpuApiVO(spu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSpu(SpuAddApiDTO spuAddApiDTO) {
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
        return insertSpuRow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        Spu spu = buildSpu(spuUpdateApiDTO);
        spu.setId(spuId);
        int updateSpuRow = spuMapper.updateByPrimaryKeySelective(spu);
        if (updateSpuRow > 0) {
            SpuInfo spuInfo = buildSpuInfo(spuId, spuUpdateApiDTO);
            int updateSpuInfoRow = spuInfoMapper.updateBySpuId(spuInfo);
            if (updateSpuInfoRow <= 0) {
                throw new ServiceException("修改商品失败");
            }
        } else {
            throw new ServiceException("修改商品失败");
        }
        return updateSpuRow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSpu(Integer spuId) {
        CompletableFuture<Integer> deleteSpuFuture = CompletableFuture.supplyAsync(() -> {
            int deleteSpuRow = spuMapper.deleteByPrimaryKey(spuId);
            if (deleteSpuRow <= 0) {
                throw new ServiceException("删除商品失败");
            }
            return deleteSpuRow;
        }, executor);
        CompletableFuture<Void> deleteSupInfoFuture = CompletableFuture.runAsync(() -> {
            int deleteSupInfoRow = spuInfoMapper.deleteBySpuId(spuId);
            if (deleteSupInfoRow <= 0) {
                throw new ServiceException("删除商品失败");
            }
        }, executor);
        CompletableFuture.allOf(deleteSpuFuture, deleteSupInfoFuture).join();
        return deleteSpuFuture.join();
    }

    @Override
    public int countByCategoryId(Integer categoryId) {
        return spuMapper.countByCategoryId(categoryId);
    }

    @Override
    public Spu getSpu(Integer spuId) {
        return spuMapper.selectSpuSpuInfoByPrimaryKey(spuId);
    }

    @Override
    public List<Spu> listSpuS(Integer categoryId, Integer pageNum, Integer pageSize) {
        return spuMapper.selectSpuSpuInfoSkuListByCategoryId(categoryId, pageNum, pageSize);
    }

    @Override
    public List<Spu> listSpuSByCategoryIdSet(Set<Integer> categoryIdSet) {
        return spuMapper.selectSpuSpuInfoListByCategoryIdSet(categoryIdSet);
    }

    @Override
    public List<Spu> listSpuS(Set<Integer> idSet) {
        return spuMapper.selectListByIdSet(idSet);
    }

    private SpuApiVO spu2SpuApiVO(Spu spu) {
        SpuApiVO spuApiVO = new SpuApiVO();
        SpuInfoApiVO spuInfoApiVO = new SpuInfoApiVO();
        BeanUtils.copyProperties(spu, spuApiVO);
        BeanUtils.copyProperties(spu.getSpuInfo(), spuInfoApiVO);
        spuApiVO.setSpuInfoApiVO(spuInfoApiVO);
        return spuApiVO;
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
