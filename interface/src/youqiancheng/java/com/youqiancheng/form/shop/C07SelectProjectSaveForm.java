package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商品选择属性项实体")
public class C07SelectProjectSaveForm  {
    @ApiModelProperty(value = "属性项ID数组",name ="projectIdList")
    private Long[] projectIdList;
    @ApiModelProperty(value = "商品表id",name ="goodsId")
    private long goodsId;
}
