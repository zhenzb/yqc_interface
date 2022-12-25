package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "微信用户实体")
public class UserWechatForm implements Serializable {
    @ApiModelProperty(value = "国家，如中国为 CN",name ="country")
    private String country;
    @ApiModelProperty(value = "普通用户昵称",name ="nikeName")
    private String nikeName;
    @ApiModelProperty(value = "用户统一标识",name ="unionid")
    private String unionid;
    @ApiModelProperty(value = "普通用户个人资料填写的省份",name ="province")
    private String province;
    @ApiModelProperty(value = "普通用户个人资料填写的城市",name ="city")
    private String city;
    @ApiModelProperty(value = "普通用户的标识，对当前开发者帐号唯一",name ="openid")
    private String openid;
    @ApiModelProperty(value = "普通用户性别，1 为男性，2 为女性",name ="sex")
    private int sex;
    @ApiModelProperty(value = "用户头像",name ="headimgurl")
    private String headimgurl;
    @ApiModelProperty(value = "邀请者id",name ="invitedUserId")
    private String invitedUserId;
}
