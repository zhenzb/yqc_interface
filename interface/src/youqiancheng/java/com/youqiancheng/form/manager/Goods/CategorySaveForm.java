package com.youqiancheng.form.manager.Goods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 权限规则的提交保存表单
 */
@Data
public class CategorySaveForm {

    @ApiModelProperty(value = "父级ID",name ="parentId")
    private Integer parentId;
    @ApiModelProperty(value = "分类名称",name ="name")
    private String name;
    @ApiModelProperty(value = "图标",name ="url")
    private String url;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private Integer orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
}
