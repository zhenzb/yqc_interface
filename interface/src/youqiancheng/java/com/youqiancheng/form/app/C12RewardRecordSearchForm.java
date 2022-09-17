package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——打赏记录查询实体")
public class C12RewardRecordSearchForm  {
    @ApiModelProperty(value = "店铺",name ="shopId")
    private long shopId;




}
