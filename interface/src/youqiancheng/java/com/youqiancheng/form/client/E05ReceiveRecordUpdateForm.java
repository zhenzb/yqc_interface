package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-24
 */
@Data
@ApiModel(value = "PC——红包领取记录修改实体")
public class E05ReceiveRecordUpdateForm {
    @ApiModelProperty(value = "id(数据库自增)")
    @Min(value = 1,message = "主键不能为空")
    private long id;
    @ApiModelProperty(value = "领取人ID",name ="userId")
    @Min(value = 1,message = "领取人ID不能为空")
    private long userId;
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "国家ID;一级分类ID(1)",name ="categoryId")
    private long categoryId;
}
