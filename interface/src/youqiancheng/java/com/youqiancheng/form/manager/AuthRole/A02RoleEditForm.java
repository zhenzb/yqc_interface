package com.youqiancheng.form.manager.AuthRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色的提交保存表单
 */
@Data
public class A02RoleEditForm {
//    @NotEmpty(message = "角色id不能为空")
    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色备注")
    private String remark;

    @ApiModelProperty(value = "菜单ID列表",name="menuIds")
    private List<Long> menuIds;
}
