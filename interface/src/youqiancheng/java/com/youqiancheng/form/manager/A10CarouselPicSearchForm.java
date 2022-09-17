package com.youqiancheng.form.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "轮播图查询实体")
public class A10CarouselPicSearchForm  {
    @ApiModelProperty(value = "类型：1、首页，2附近",name ="type")
    private int type;



}
