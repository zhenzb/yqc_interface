package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商品选择属性查询实体")
public class C08SelectAttributeSearchForm {
    @ApiModelProperty(value = "商品ID",name ="goodsId")
    private Long goodsId;
}
