package com.zhihao.newretail.product.service.impl;

import com.zhihao.newretail.api.product.dto.SlideAddApiDTO;
import com.zhihao.newretail.api.product.dto.SlideUpdateApiDTO;
import com.zhihao.newretail.core.util.BeanCopyUtil;
import com.zhihao.newretail.product.dao.SlideMapper;
import com.zhihao.newretail.product.pojo.Slide;
import com.zhihao.newretail.product.pojo.vo.SlideVO;
import com.zhihao.newretail.product.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideMapper slideMapper;

    @Override
    public List<SlideVO> listSlideVOS() {
        List<Slide> slideList = slideMapper.selectListByAll();
        return slideList.stream().map(slide -> BeanCopyUtil.source2Target(slide, SlideVO.class)).collect(Collectors.toList());
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

}
