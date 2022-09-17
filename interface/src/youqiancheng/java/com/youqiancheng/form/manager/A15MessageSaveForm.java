package com.youqiancheng.form.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "消息实体")
public class A15MessageSaveForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
}
