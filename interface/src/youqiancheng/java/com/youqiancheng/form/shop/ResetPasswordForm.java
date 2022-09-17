package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商城--重置密码验证
 *
 */
@Data
@ApiModel(value = "重置密码实体")
public class ResetPasswordForm {
    @ApiModelProperty("旧密码")
    @NotBlank(message="旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty("新密码")
    @NotBlank(message="新密码不能为空")
    private String newPwd;

}
