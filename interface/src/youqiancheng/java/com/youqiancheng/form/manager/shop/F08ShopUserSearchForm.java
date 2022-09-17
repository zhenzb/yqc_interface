package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商家用户查询实体")
public class F08ShopUserSearchForm {
    @ApiModelProperty(value = "账号：手机号",name ="userName")
    private String userName;
}
