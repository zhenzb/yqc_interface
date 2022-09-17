package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "APP——商品规格查询库存实体")
public class C09GoodsSkuSearchForm  {
    @ApiModelProperty(value = "已选择的商品属性",name ="specifications")
    @NotBlank(message = "已选择的商品属性不能为空")
    private String specifications;
    @ApiModelProperty(value = "商品Id",name ="goodsId")
    @Range(min = 1,message = "商品ID最小为1")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;
}
