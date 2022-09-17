package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "PC——实名认证保存实体")
public class B07AuthenticationSaveForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
    @ApiModelProperty(value = "名字",name ="name")
    @NotBlank(message = "名字不能为空")
    private String name;
    @ApiModelProperty(value = "身份证号",name ="cardNo")
    @NotBlank(message = "身份证号不能为空")
    private String cardNo;
    @ApiModelProperty(value = "照片",name ="url")
    @NotBlank(message = "身份证照片正面不能为空")
    private String url;
    @ApiModelProperty(value = "反面照片",name ="backUrl")
    @NotBlank(message = "身份证照片反面不能为空")
    private String backUrl;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "银行卡号",name ="bankCardNum")
    @NotBlank(message = "银行卡号不能为空")
    private String bankCardNum;



}
