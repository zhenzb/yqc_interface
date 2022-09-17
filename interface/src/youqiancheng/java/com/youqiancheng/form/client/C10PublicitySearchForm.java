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
@ApiModel(value = "PC——宣传查询实体")
public class C10PublicitySearchForm  {

    @ApiModelProperty(value = "店铺Id",name ="shopId")
    @Min(value = 1,message = "商家ID不能为空")
    private long shopId;

}
