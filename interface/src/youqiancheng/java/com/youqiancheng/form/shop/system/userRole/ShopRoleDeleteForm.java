package com.youqiancheng.form.shop.system.userRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色的提交保存表单
 */
@Data
public class ShopRoleDeleteForm {
    @ApiModelProperty("角色ID")
    private Long[] ids;
}
