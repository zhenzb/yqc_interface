package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商品选择属性项实体")
public class C07SelectProjectSearchForm implements Serializable {
    @ApiModelProperty(value = "商品表id",name ="goodsId")
    private Long goodsId;
}
