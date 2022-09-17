package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——收藏查询计数实体")
public class B05CollectionSearchCountForm {
    @ApiModelProperty(value = "关联ID:商品ID/商家ID",name ="relationId")
    @Min(value = 1,message = "收藏ID（商品ID/商家ID）最小为1")
    private long relationId;

}
