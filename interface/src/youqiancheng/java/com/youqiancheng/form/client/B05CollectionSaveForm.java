package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "PC——收藏保存实体",description ="PC——收藏保存实体")
public class B05CollectionSaveForm {
    @ApiModelProperty(value = "用户ID",name ="userId" ,required = true)
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
    @ApiModelProperty(value = "关联ID:商品ID/商家ID",name ="relationId")
    @Min(value = 1,message = "关联ID:商品ID/商家ID不能为空")
    private long relationId;
}
