package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SkuStockLockApiDTO;
import com.zhihao.newretail.api.product.vo.SkuStockApiVO;
import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.product.dao.SkuStockLockMapper;
import com.zhihao.newretail.product.dao.SkuStockMapper;
import com.zhihao.newretail.product.enums.SkuStockLockEnum;
import com.zhihao.newretail.product.pojo.SkuStock;
import com.zhihao.newretail.product.pojo.SkuStockLock;
import com.zhihao.newretail.product.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Autowired
    private SkuStockLockMapper skuStockLockMapper;

    @Override
    public SkuStock getSkuStock(Integer skuId) {
        return skuStockMapper.selectBySkuId(skuId);
    }

    @Override
    public List<SkuStockApiVO> listSkuStockApiVOs(Set<Integer> skuIdSet) {
        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        return skuStockList.stream()
                .map(skuStock -> {
                    SkuStockApiVO skuStockApiVO = new SkuStockApiVO();
                    BeanUtils.copyProperties(skuStock, skuStockApiVO);
                    return skuStockApiVO;
                }).collect(Collectors.toList());
    }

    @Override
    public void stockLock(SkuStockLockApiDTO skuStockLockApiDTO) {
        SkuStockLock skuStockLock = new SkuStockLock();
        BeanUtils.copyProperties(skuStockLockApiDTO, skuStockLock);
        skuStockLock.setStatus(SkuStockLockEnum.LOCK.getCode());
        int insertSkuStockLockRow = skuStockLockMapper.insertSelective(skuStockLock);

        if (insertSkuStockLockRow <= 0) {
            throw new ServiceException("库存锁定失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchStockLock(List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        Set<Integer> skuIdSet = skuStockLockApiDTOList.stream()
                .map(SkuStockLockApiDTO::getSkuId).collect(Collectors.toSet());
        Map<Integer, SkuStockLockApiDTO> skuStockLockApiDTOMap = skuStockLockApiDTOList.stream()
                .collect(Collectors.toMap(SkuStockLockApiDTO::getSkuId, skuStockLockApiDTO -> skuStockLockApiDTO));

        List<SkuStock> skuStockList = skuStockMapper.selectListBySkuIdSet(skuIdSet);
        List<SkuStock> skuStocks = new ArrayList<>();
        for (SkuStock skuStock : skuStockList) {
            SkuStockLockApiDTO skuStockLockApiDTO = skuStockLockApiDTOMap.get(skuStock.getSkuId());
            buildSkuStock(skuStock, skuStockLockApiDTO);
            skuStocks.add(skuStock);
        }
        int updateBatchRow = skuStockMapper.updateBatch(skuStocks);

        List<SkuStockLock> skuStockLockList = skuStockLockApiDTOList.stream()
                .map(skuStockLockApiDTO -> {
                    SkuStockLock skuStockLock = new SkuStockLock();
                    BeanUtils.copyProperties(skuStockLockApiDTO, skuStockLock);
                    skuStockLock.setStatus(SkuStockLockEnum.LOCK.getCode());
                    return skuStockLock;
                }).collect(Collectors.toList());
        skuStockLockMapper.insertBatch(skuStockLockList);

        return updateBatchRow;
    }

    private void buildSkuStock(SkuStock skuStock, SkuStockLockApiDTO skuStockLockApiDTO) {
        if (skuStock.getLockStock() == 0) {
            skuStock.setStock(skuStock.getActualStock() - skuStockLockApiDTO.getCount());
        } else {
            skuStock.setStock(skuStock.getActualStock() - (skuStock.getLockStock() + skuStockLockApiDTO.getCount()));
        }
        skuStock.setLockStock(skuStock.getLockStock() + skuStockLockApiDTO.getCount());
    }

}
