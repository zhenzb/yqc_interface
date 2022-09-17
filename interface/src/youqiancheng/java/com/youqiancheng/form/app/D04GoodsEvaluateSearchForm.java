package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "APP——用户商品评价记录查询实体")
public class D04GoodsEvaluateSearchForm {
    @ApiModelProperty(value = "商品id",name ="goodsId")
    @Min(value = 1,message = "商品ID最小为1")
    private long goodsId;
}
