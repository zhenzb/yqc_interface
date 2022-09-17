package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——红包领取个数视图实体")
public class ReceiveRecordNumVO {
    @ApiModelProperty(value = "总数",name ="totalNum")
    private int totalNum;
    @ApiModelProperty(value = "领取数量",name ="receiveNum")
    private int receiveNum;
    @ApiModelProperty(value = "未领取数量",name ="unReceiveNum")
    private int unReceiveNum;
    @ApiModelProperty(value = "对否免费标记:1未设置免费；设置2免费",name ="isFree")
    private int isFree;
}
