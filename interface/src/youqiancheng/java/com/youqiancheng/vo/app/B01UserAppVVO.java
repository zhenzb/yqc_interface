package com.youqiancheng.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "用户信息实体")
public class B01UserAppVVO implements Serializable {
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
    @ApiModelProperty(value = "生日",name ="birthday")
    private String birthday;
    @ApiModelProperty(value = "余额",name ="balance")
    private BigDecimal balance;
    @ApiModelProperty(value = "是否实名认证",name ="isAuthentication")
    private int isAuthentication;
    @ApiModelProperty(value = "认证名称",name ="authenticationName")
    private String authenticationName;
    @ApiModelProperty(value = "微信OpenID",name ="wechatOpenid")
    private String wechatOpenid;



}
