package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MainProjectSaveForm {
    @ApiModelProperty(value = "商家类型",name ="type")
    private int type;
    @ApiModelProperty(value = "主营项目名称",name ="name")
    private String name;
    @ApiModelProperty(value = "主营项目-图标",name ="picUrl")
    private String picUrl;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private Integer orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private Integer status;
}
