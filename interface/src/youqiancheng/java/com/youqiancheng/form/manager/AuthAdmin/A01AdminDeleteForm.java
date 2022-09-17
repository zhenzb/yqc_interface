package com.youqiancheng.form.manager.AuthAdmin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员的提交保存表单
 */
@Data
public class A01AdminDeleteForm {
//    @NotEmpty(message = "请输入用户名")
    @ApiModelProperty("管理员ID")
    private Long id;
}
