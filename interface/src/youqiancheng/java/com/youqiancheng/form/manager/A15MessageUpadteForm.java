package com.youqiancheng.form.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "消息实体")
public class A15MessageUpadteForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;

}
