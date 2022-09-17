package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商家查询商品评价记录实体")
public class D04GoodsEvaluateSearchDO implements Serializable {
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;

}
