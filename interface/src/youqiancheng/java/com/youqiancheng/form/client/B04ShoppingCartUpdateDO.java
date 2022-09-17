package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——购物车实体修改")
public class B04ShoppingCartUpdateDO {
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    @Min(value = 1,message = "主键不能为空")
    private long id;
    @ApiModelProperty(value = "数量",name ="commodityNumber")
    private int commodityNumber;
    @ApiModelProperty(value = "单价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "总价",name ="totalPrice")
    private BigDecimal totalPrice;
}
