package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "APP——消息查询实体")
public class A15MessageAppSearchForm {
    @NotNull(message = "请输入用户id")
    @ApiModelProperty(value = "用户ID",name ="userId")
    private Long userId;
    @Range(max = 3,min = 1,message = "类型取值范围为1,2,3")
    @ApiModelProperty(value = "类型：1平台消息，2、系统消息  3、活动通知",name ="type")
    private int type;
}
