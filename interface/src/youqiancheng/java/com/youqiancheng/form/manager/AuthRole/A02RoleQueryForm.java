package com.youqiancheng.form.manager.AuthRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色的查询表单
 */
@Data
public class A02RoleQueryForm {
    @ApiModelProperty("角色名称")
    private String name;
}
