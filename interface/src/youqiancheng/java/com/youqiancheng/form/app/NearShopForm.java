package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——附近查询实体")
public class NearShopForm  {
//    @ApiModelProperty(value = "附近距离——KM",name ="radii")
//    private double radii;
    @ApiModelProperty(value = "经度",name ="lon")
    private double lon;
    @ApiModelProperty(value = "纬度",name ="lat")
    private double lat;
}
