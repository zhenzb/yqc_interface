package com.youqiancheng.form.manager.AuthAdmin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理员的提交保存表单
 * @NotEmpty 支持的是字符串类型字段，
 */
@Data
public class A01AdminEditForm {
    @NotNull(message = "请输入用户id")
    @ApiModelProperty("管理员ID")
    private Long id;


    @ApiModelProperty("管理员姓名")
    private String userName;
    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("管理员电话")
    private String tel;
    @ApiModelProperty("管理员邮箱")
    private String email;
    @ApiModelProperty("角色idList")
    private List<Long> roleList;
}
