package com.youqiancheng.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP视图——用户信息实体")
public class B01UserAppVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户Id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "手机号",name ="mobile")
    private String mobile;
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "注册时间",name ="registTime")
    private LocalDateTime registTime;
    @ApiModelProperty(value = "性别 男1 女2 未知3",name ="sex")
    private int sex;
    @ApiModelProperty(value = "生日",name ="birthday")
    private Date birthday;
    @ApiModelProperty(value = "微信OpenID",name ="wechatOpenid")
    private String wechatOpenid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "用户当前登录时间",name ="currentLoginTime")
    private LocalDateTime currentLoginTime;
    @ApiModelProperty(value = "用户登录类型",name ="appType")
    private int appType;
    @ApiModelProperty(value = "是否为商家 1 否 2 是",name ="isShop")
    private int isShop;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "是否实名认证",name ="isAuthentication")
    private int isAuthentication;
    @ApiModelProperty(value = "token",name ="token")
    private String token;
    @ApiModelProperty(value = "token类型",name ="tokenType")
    private String tokenType;
    @ApiModelProperty(value = "商家类型",name ="shopType")
    private int shopType;
    @ApiModelProperty(value = "联系客服签名",name ="userSig")
    private String userSig;
    @ApiModelProperty(value = "腾讯IM用户ID",name ="tengXunImId")
    private String tengXunImId;



}
