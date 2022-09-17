package com.youqiancheng.form.shop.system.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 商城--重置密码验证
 *
 */
@Data
public class ShopResetPasswordForm {
    @ApiModelProperty("账号")
    @NotEmpty(message = "请输入账号")
    private String username;

    @ApiModelProperty("手机验证码")
    @NotEmpty(message = "请输入手机验证码")
    private String phonecode;

    @ApiModelProperty("新密码")
    @NotEmpty(message = "请输入新密码")
    private String newpwd;

}
