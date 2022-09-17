package com.youqiancheng.form.shop.system.userRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色的查询表单
 */
@Data
public class ShopRoleQueryForm {
    @ApiModelProperty("角色名称")
    private String name;
}
