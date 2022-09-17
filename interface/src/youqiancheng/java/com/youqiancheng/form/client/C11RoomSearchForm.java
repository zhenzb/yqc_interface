package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "PC——旅游房间查询实体")
public class C11RoomSearchForm  {

    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;

}
