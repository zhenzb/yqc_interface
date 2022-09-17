package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——红包街查询实体")
public class E01RedenvelopesStreetSearchForm  {
    @NotNull(message = "请输入一级产品分类ID")
    @ApiModelProperty(value = "一级产品分类ID",name ="categoryId")
    private Long categoryId;
}
