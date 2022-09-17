package com.youqiancheng.form.manager.publicity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PublicityQueryForm {
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @ApiModelProperty(value = "店铺名称",name ="shopName")
    private String shopName;
    private Long goodsId;

}
