package com.youqiancheng.form.shop.system.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 管理员的提交保存表单
 */
@Data
public class ShopUserSaveForm {
    @NotEmpty(message = "请输入用户名")
    @ApiModelProperty("管理员姓名")
    private String userName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("管理员密码")
    private String password;

    @NotEmpty(message = "请输入电话")
    @ApiModelProperty("管理员电话")
    private String tel;
    @ApiModelProperty("管理员邮箱")
    private String email;
    @ApiModelProperty("角色idList")
    private List<Long> roleList;
}
