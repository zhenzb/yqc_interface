package com.youqiancheng.vo.client;

import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——购物车视图实体")
public class ShoppingCartVO {
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "购物车记录",name ="cartList")
    private List<B04ShoppingCartDO> cartList;
}
