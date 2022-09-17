package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "PC——用户信息保存实体")
public class B01UserSaveForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "手机号",name ="mobile")
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @ApiModelProperty(value = "验证码",name ="code")
    @NotBlank(message = "验证码不能为空")
    private String code;
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @ApiModelProperty(value = "性别",name ="sex")
    private int sex;
    @ApiModelProperty(value = "生日",name ="birthday")
    private Date birthday;
    @ApiModelProperty(value = "微信OpenID",name ="wechatOpenid")
    @NotBlank(message = "微信OpenID不能为空")
    private String wechatOpenid;
}
