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
public class E04GrantRecordSearchForm   {
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "支付模式1:微信，2：流量值",name ="model")
    private String model;
    @ApiModelProperty(value = "用户Id",name ="userId")
    private long userId;
}
