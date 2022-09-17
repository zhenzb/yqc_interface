package com.youqiancheng.util.email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2019-12-19
 */
@ApiModel(value = "邮箱设置实体")
@Data
public class EmailSettingDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "主机名",name ="hostName")
    private String hostName;
    @ApiModelProperty(value = "邮箱地址",name ="sendMail")
    private String sendMail;
    @ApiModelProperty(value = "用户名",name ="userName")
    private String userName;
    @ApiModelProperty(value = "密码",name ="userNamePwd")
    private String userNamePwd;
    @ApiModelProperty(value = "授权名",name ="authenticationName")
    private String authenticationName;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
}
