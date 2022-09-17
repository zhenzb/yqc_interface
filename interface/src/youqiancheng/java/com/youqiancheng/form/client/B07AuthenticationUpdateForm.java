package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "PC——实名认证修改实体")
public class B07AuthenticationUpdateForm {
    @ApiModelProperty(value = "主键id(数据库自增)")
    @Min(value = 1,message = "id不能为空")
    private long id;
    @ApiModelProperty(value = "名字",name ="name")
    private String name;
    @ApiModelProperty(value = "身份证号",name ="cardNo")
    private String cardNo;
    @ApiModelProperty(value = "照片",name ="url")
    private String url;
    @ApiModelProperty(value = "反面照片",name ="backUrl")
    private String backUrl;



}
