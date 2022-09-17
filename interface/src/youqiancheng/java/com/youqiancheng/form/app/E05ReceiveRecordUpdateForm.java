package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-24
 */
@Data
@ApiModel(value = "APP——红包领取记录修改实体")
public class E05ReceiveRecordUpdateForm {
    @ApiModelProperty(value = "id(数据库自增)")
    private long id;
    @ApiModelProperty(value = "领取人ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "国家ID;一级分类ID(1)",name ="categoryId")
    private long categoryId;
}
