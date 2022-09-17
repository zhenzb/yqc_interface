package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——红包发放记录查询实体")
public class ReceiveRecordSearchForm {
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
}
