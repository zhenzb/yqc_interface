package com.youqiancheng.form.shop.system.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员的提交保存表单
 */
@Data
public class ShopChangeForm {
    @ApiModelProperty("管理员ID")
    private Long id;
    @ApiModelProperty("状态  0禁用 1启用  默认是1")
    private Integer status;
}
