package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "用户信息实体")
@TableName("b01_user")
public class B01UserDO implements Serializable {
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
    @ApiModelProperty(value = "性别",name ="sex")
    private int sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "生日",name ="birthday")
    private LocalDate birthday;
    @ApiModelProperty(value = "微信OpenID",name ="wechatOpenid")
    private String wechatOpenid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "用户当前登录时间",name ="currentLoginTime")
    private LocalDateTime currentLoginTime;
    @ApiModelProperty(value = "用户登录类型",name ="appType")
    private int appType;
    @ApiModelProperty(value = "是否为商家",name ="isShop")
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
    @TableField(exist = false)
    @ApiModelProperty(value = "认证名称",name ="authenticationName")
    private String authenticationName;
    @TableField(exist = false)
    @ApiModelProperty(value = "提现账户",name ="alipayAccount")
    private String alipayAccount;


    @TableField(exist = false)
    @ApiModelProperty(value = "店铺状态",name ="shopState")
    private String shopState;

  @ApiModelProperty(value = "苹果ID",name ="appleId")
    private String appleId;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户流量值",name ="accountBalance")
    private BigDecimal accountBalance;




}
