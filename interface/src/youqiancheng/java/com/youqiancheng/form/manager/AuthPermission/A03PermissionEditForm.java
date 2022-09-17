package com.youqiancheng.form.manager.AuthPermission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限规则的提交保存表单
 */
@Data
public class A03PermissionEditForm {
//    @NotEmpty(message = "请输入主键值")
    @ApiModelProperty("主键")
    private Long menuId;

//    @NotEmpty(message = "请输入菜单名称")
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单URL")
    private String url;
    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    private String perms;
    @ApiModelProperty("类型   0：目录   1：菜单   2：按钮")
    private Integer type;
    @ApiModelProperty("菜单图标")
    private String icon;
    @ApiModelProperty("排序")
    private Integer orderNum;
    @ApiModelProperty("标记")
    private String component;

}
