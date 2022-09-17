package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——宣传查询实体")
public class C10PublicitySearchForm  {

    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;

}
