package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "PC——消息查询实体")
public class A15MessageAppSearchForm {
    @ApiModelProperty(value = "用户ID",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
    @ApiModelProperty(value = "类型:1平台消息,2、系统消息  3、活动通知",name ="type")
    @Min(value = 1,message ="type不能为空")
    private int type;


}
