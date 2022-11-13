package com.zhihao.newretail.admin.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SpuForm {

    @NotNull(message = "请选择所属分类")
    private Integer categoryId;

    @NotBlank(message = "商品标题不能为空")
    private String title;

    private String subTitle;

    private String showImage;

    private String detailTitle;

    private List<String> sliderImageUrlList;

    private List<String> detailImageUrlList;

}
