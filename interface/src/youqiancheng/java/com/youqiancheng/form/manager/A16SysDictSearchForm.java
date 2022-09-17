package com.youqiancheng.form.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "字典表查询实体")
public class A16SysDictSearchForm {

    @ApiModelProperty(value = "描述",name ="description")
    private String description;
    @ApiModelProperty(value = "类型",name ="type")
    private String type;
}
