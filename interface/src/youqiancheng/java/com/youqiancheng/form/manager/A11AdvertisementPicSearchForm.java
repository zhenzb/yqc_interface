package com.youqiancheng.form.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "广告图查询实体")
public class A11AdvertisementPicSearchForm  {
    @ApiModelProperty(value = "类型:1、启动页广告，2、附近广告",name ="type")
    private Integer type;




}
