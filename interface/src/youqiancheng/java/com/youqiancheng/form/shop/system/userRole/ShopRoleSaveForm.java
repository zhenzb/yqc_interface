package com.youqiancheng.form.shop.system.userRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色的提交保存表单
 */
@Data
public class ShopRoleSaveForm {
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色备注")
    private String remark;
    @ApiModelProperty("类型")
    private int type;

    @ApiModelProperty(value = "菜单ID列表",name="menuIds")
    private List<Long> menuIds;
}
