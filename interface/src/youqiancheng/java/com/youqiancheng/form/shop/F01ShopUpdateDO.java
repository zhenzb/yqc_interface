package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商家表修改实体")
public class F01ShopUpdateDO implements Serializable {
    @ApiModelProperty(value = "商家ID",name ="id")
    private long id;
    @ApiModelProperty(value = "名称", name = "name")
    private String name;
    @ApiModelProperty(value = "联系人", name = "contacts")
    private String contacts;
    @ApiModelProperty(value = "电话", name = "phone")
    private String phone;
    @ApiModelProperty(value = "logoURl", name = "logo")
    private String logo;
    @ApiModelProperty(value = "简介", name = "shopDesc")
    private String shopDesc;
    @ApiModelProperty(value = "是否隐藏电话：1不隐藏，2隐藏", name = "hide")
    private Integer hide;
    @ApiModelProperty(value = "支付宝支付二维码",name ="alipayUrl")
    private String alipayUrl;
    @ApiModelProperty(value = "微信二维码",name ="wechatUrl")
    private String wechatUrl;
}
