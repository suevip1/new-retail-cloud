package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.api.product.vo.SlideApiVO;
import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.core.util.PageUtil;
import com.zhihao.newretail.product.dao.SlideMapper;
import com.zhihao.newretail.product.pojo.Slide;
import com.zhihao.newretail.product.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideMapper slideMapper;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public List<Slide> listSlideS() {
        return slideMapper.selectListByAll();
    }

    @Override
    public PageUtil<SlideApiVO> listSlideApiVOS(Integer slideId, Integer pageNum, Integer pageSize) {
        PageUtil<SlideApiVO> pageUtil = new PageUtil<>();
        CompletableFuture<Void> totalFuture = CompletableFuture.runAsync(() -> {
            int total = slideMapper.countSlide(null);
            pageUtil.setPageNum(pageNum);
            pageUtil.setPageSize(pageSize);
            pageUtil.setTotal((long) total);
        }, executor);
        CompletableFuture<Void> listFuture = CompletableFuture.runAsync(() -> {
            List<Slide> slideList = slideMapper.selectListByRecord(slideId, pageNum, pageSize);
            List<SlideApiVO> slideApiVOList = slideList.stream().map(slide -> BeanCopyUtil.source2Target(slide, SlideApiVO.class)).collect(Collectors.toList());
            pageUtil.setList(slideApiVOList);
        }, executor);
        CompletableFuture.allOf(totalFuture, listFuture).join();
        return pageUtil;
    }

    @Override
    public int insertSlide(SlideAddApiDTO slideAddApiDTO) {
        Slide slide = BeanCopyUtil.source2Target(slideAddApiDTO, Slide.class);
        return slideMapper.insertSelective(slide);
    }

    @Override
    public int updateSlide(Integer slideId, SlideUpdateApiDTO slideUpdateApiDTO) {
        Slide slide = BeanCopyUtil.source2Target(slideUpdateApiDTO, Slide.class);
        assert slide != null;
        slide.setId(slideId);
        return slideMapper.updateByPrimaryKeySelective(slide);
    }

    @Override
    public int deleteSlide(Integer slideId) {
        return slideMapper.deleteByPrimaryKey(slideId);
    }

}
