package com.zhihao.newretail.product.service.impl;

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

}
