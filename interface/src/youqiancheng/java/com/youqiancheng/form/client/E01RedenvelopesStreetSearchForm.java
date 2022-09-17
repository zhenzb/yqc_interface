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
@ApiModel(value = "PC——红包街查询实体")
public class E01RedenvelopesStreetSearchForm  {
    @ApiModelProperty(value = "一级产品分类ID",name ="categoryId")
    @Min(value = 1,message = "分类ID不能为空")
    private long categoryId;




}
