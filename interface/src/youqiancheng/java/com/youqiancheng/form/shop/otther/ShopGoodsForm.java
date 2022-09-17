package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 14:58
 * @Version: V1.0
 */
@Data
public class ShopGoodsForm extends ShopCommonForm {
    @ApiModelProperty("上架状态")
    private Integer isSale;
}


