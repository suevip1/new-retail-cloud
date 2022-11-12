package com.zhihao.newretail.core.util;

import lombok.Data;

import java.util.List;

@Data
public class PageUtil<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private List<T> list;

}
