package com.youqiancheng.form.manager.AuthPermission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限规则的提交保存表单
 */
@Data
public class A03PermissionDeleteForm {
//    @NotEmpty(message = "请输入主键值")
    @ApiModelProperty("主键")
    private Long menuId;
}
