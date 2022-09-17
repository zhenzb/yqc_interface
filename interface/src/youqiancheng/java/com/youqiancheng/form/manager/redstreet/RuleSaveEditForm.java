package com.youqiancheng.form.manager.redstreet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RuleSaveEditForm {
    @ApiModelProperty(value = "规则",name ="content")
    private String content;
    @ApiModelProperty(value = "红包图片",name ="url")
    private String url;
    @ApiModelProperty(value = "红包图片灰色",name ="disableUrl")
    private String disableUrl;
}
