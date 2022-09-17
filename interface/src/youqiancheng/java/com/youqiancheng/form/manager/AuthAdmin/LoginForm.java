package com.youqiancheng.form.manager.AuthAdmin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录验证
 */
@Data
public class LoginForm {
    @ApiModelProperty("账号")
    @NotEmpty(message = "请输入账号")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "请输入密码")
    private String pwd;

}
