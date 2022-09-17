package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "PC——商品规格实体")
public class C09GoodsSkuSearchForm {
    @NotBlank(message = "商品属性不能为空")
    @ApiModelProperty(value = "已选择的商品属性",name ="specifications")
    private String specifications;
    @ApiModelProperty(value = "商品Id",name ="goodsId")
    @Min(value = 1,message = "商品ID不能为空")
    private long goodsId;
}
