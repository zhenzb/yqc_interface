package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——收藏查询实体")
public class B05CollectionSearchForm {
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
}
