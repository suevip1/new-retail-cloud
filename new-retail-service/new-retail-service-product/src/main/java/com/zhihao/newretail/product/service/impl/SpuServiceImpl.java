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

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Override
    public SpuApiVO getSpuApiVO(Integer spuId) {
        Spu spu = spuMapper.selectSpuSpuInfoByPrimaryKey(spuId);
        return spu2SpuApiVO(spu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSpu(SpuAddApiDTO spuAddApiDTO) {
        Spu spu = spuAddApiDTO2Spu(spuAddApiDTO);
        int insertSpuRow = spuMapper.insertSelective(spu);
        if (insertSpuRow >= 1) {
            insertSpuInfo(spu.getId(), spuAddApiDTO);
            return insertSpuRow;
        }
        throw new ServiceException("新增商品失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSpu(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        Spu spu = spuAddApiDTO2Spu(spuUpdateApiDTO);
        spu.setId(spuId);
        int updateSpuRow = spuMapper.updateByPrimaryKeySelective(spu);
        if (updateSpuRow >= 1) {
            updateSpuInfo(spuId, spuUpdateApiDTO);
            return updateSpuRow;
        }
        throw new ServiceException("修改商品失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSpu(Integer spuId) {
        int deleteSpuRow = spuMapper.deleteByPrimaryKey(spuId);
        if (deleteSpuRow >= 1) {
            deleteSpuInfo(spuId);
            return deleteSpuRow;
        }
        throw new ServiceException("删除商品失败");
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
        return spuMapper.selectSpuSpuInfoSkuListByCategoryIdSet(categoryIdSet);
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

    private Spu spuAddApiDTO2Spu(SpuAddApiDTO spuAddApiDTO) {
        Spu spu = new Spu();
        spu.setCategoryId(spuAddApiDTO.getCategoryId());
        spu.setTitle(spuAddApiDTO.getTitle());
        spu.setSubTitle(spuAddApiDTO.getSubTitle());
        return spu;
    }

    private SpuInfo spuAddApiDTO2SpuInfo(Integer spuId, SpuAddApiDTO spuAddApiDTO) {
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuAddApiDTO, spuInfo);
        spuInfo.setSpuId(spuId);
        return spuInfo;
    }

    /*
     * 保存商品信息
     * */
    private void insertSpuInfo(Integer spuId, SpuAddApiDTO spuAddApiDTO) {
        SpuInfo spuInfo = spuAddApiDTO2SpuInfo(spuId, spuAddApiDTO);
        int insertSpuInfoRow = spuInfoMapper.insertSelective(spuInfo);
        if (insertSpuInfoRow <= 0) {
            throw new ServiceException("商品信息保存失败");
        }
    }

    /*
     * 修改商品信息
     * */
    private void updateSpuInfo(Integer spuId, SpuUpdateApiDTO spuUpdateApiDTO) {
        SpuInfo spuInfo = spuAddApiDTO2SpuInfo(spuId, spuUpdateApiDTO);
        int updateSpuInfoRow = spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
        if (updateSpuInfoRow <= 0) {
            throw new ServiceException("商品信息修改失败");
        }
    }

    /*
     * 删除商品信息
     * */
    private void deleteSpuInfo(Integer spuId) {
        int deleteSpuInfoRow = spuInfoMapper.deleteBySpuId(spuId);
        if (deleteSpuInfoRow <= 0) {
            throw new ServiceException("商品信息删除失败");
        }
    }

}
