package com.youqiancheng.form.shop.system.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员的提交保存表单
 */
@Data
public class ShopUserDeleteForm {
    @ApiModelProperty("管理员ID")
    private Long[] ids;
}
