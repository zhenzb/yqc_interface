package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "PC——红包发放记录查询实体")
public class E04GrantRecordSearchForm {
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    @Min(value = 1,message = "红包街区ID不能为空")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    @Min(value = 1,message = "商家ID不能为空")
    private long shopId;
    //前端调用类型
    private String appType;
}
