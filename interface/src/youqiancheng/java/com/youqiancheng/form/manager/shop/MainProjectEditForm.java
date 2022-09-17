package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class MainProjectEditForm {
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "商家类型",name ="type")
    private Integer type;
    @ApiModelProperty(value = "主营项目名称",name ="name")
    private String name;
    @ApiModelProperty(value = "主营项目-图标",name ="picUrl")
    private String picUrl;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private Integer orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private Integer status;
}
