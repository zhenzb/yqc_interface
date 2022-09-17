package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "PC——用户商品评价记录查询实体")
public class D04GoodsEvaluateSearchForm {
    @ApiModelProperty(value = "商品id",name ="goodsId")
    @Min(value = 1,message = "商品ID不能为空")
    private long goodsId;
}
