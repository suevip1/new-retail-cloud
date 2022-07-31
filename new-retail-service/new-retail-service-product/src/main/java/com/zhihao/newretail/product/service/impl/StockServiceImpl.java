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
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

        if (!CollectionUtils.isEmpty(skuStockList)) {
            return skuStockList.stream()
                    .map(skuStock -> {
                        SkuStockApiVO skuStockApiVO = new SkuStockApiVO();
                        BeanUtils.copyProperties(skuStock, skuStockApiVO);
                        return skuStockApiVO;
                    }).collect(Collectors.toList());
        }
        throw new ServiceException(HttpStatus.SC_NOT_FOUND, "商品库存信息异常");
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
    public void batchStockLock(List<SkuStockLockApiDTO> skuStockLockApiDTOList) {
        List<SkuStockLock> skuStockLockList = skuStockLockApiDTOList.stream()
                .map(skuStockLockApiDTO -> {
                    SkuStockLock skuStockLock = new SkuStockLock();
                    BeanUtils.copyProperties(skuStockLockApiDTO, skuStockLock);
                    skuStockLock.setStatus(SkuStockLockEnum.LOCK.getCode());
                    return skuStockLock;
                }).collect(Collectors.toList());
        int insertBatchSkuStockLockRow = skuStockLockMapper.insertBatch(skuStockLockList);

        if (insertBatchSkuStockLockRow <= 0) {
            throw new ServiceException("库存锁定失败");
        }
    }

}
