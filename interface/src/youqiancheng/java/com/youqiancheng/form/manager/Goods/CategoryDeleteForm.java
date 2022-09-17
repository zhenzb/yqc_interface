package com.youqiancheng.form.manager.Goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 权限规则的提交保存表单
 */
@Data
public class CategoryDeleteForm {
    @NotNull(message = "请输入id")
    @ApiModelProperty("ID")
    private Integer id;

}
