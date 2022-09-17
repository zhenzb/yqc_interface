package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——根据商家查询商品实体")
public class C01GoodsAppListForm {

    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;

}
