package com.youqiancheng.vo.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "用户信息包含商家视图")
public class UserWithShopVO  {
    @ApiModelProperty(value = "用户Id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long userId;
    @ApiModelProperty(value = "昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "手机号",name ="mobile")
    private String mobile;
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @ApiModelProperty(value = "是否为商家",name ="isShop")
    private int isShop;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "是否展示",name ="isShow")
    private int isShow;
    @ApiModelProperty(value = "商家类型",name ="shopType")
    private int shopType;
    @ApiModelProperty(value = "店铺状态",name ="shopState")
    private Integer shopState;


}
