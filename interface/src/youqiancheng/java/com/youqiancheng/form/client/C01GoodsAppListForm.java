package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "PC——商家查询商品实体")
public class C01GoodsAppListForm {

    @ApiModelProperty(value = "店铺Id",name ="shopId")
    @Min(value = 1,message = "商品ID不能为空")
    private long shopId;

}
