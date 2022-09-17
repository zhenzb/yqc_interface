package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "APP视图——消息计数实体")
public class A15MessageAppCountlVO {
    @ApiModelProperty(value = "平台消息数量",name ="platformMessageCount")
    private int platformMessageCount;
    @ApiModelProperty(value = "系统消息数量",name ="systemMessagesCount")
    private int systemMessagesCount;
    @ApiModelProperty(value = "活动消息数量",name ="activityMessageCount")
    private int activityMessageCount;
    @ApiModelProperty(value = "平台消息类型",name ="platformMessageType")
    private int platformMessageType;
    @ApiModelProperty(value = "系统消息类型",name ="systemMessagesType")
    private int systemMessagesType;
    @ApiModelProperty(value = "活动消息类型",name ="activityMessageType")
    private int activityMessageType;
}
