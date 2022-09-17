package com.youqiancheng.form.manager.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ShopPassRefuseForm {
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "审核状态",name ="status")
    private Integer status;
    @ApiModelProperty(value = "审批理由",name ="refuseReason")
    private String reason;
    @ApiModelProperty(value = "上架状态",name ="isSale")
    private Integer isSale;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;

    @ApiModelProperty(value = "提现类型 1:店铺提现，2:用户红包提现，3:推广收益提现",name ="type")
    private int type;
}
