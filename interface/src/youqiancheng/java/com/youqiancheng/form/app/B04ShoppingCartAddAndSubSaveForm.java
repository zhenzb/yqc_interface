package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——购物车加减保存实体")
public class B04ShoppingCartAddAndSubSaveForm {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @Min(message = "商品id最小为1",value = 1)
    @ApiModelProperty(value = "商品表id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "加入购物车数量",name ="commodityNumber")
    @Min(message = "加入购物车数量最小为1",value = 1)
    private int commodityNumber;

}
