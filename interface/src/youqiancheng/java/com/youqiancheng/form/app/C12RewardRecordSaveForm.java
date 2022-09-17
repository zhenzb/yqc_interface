package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "APP——打赏记录保存实体")
public class C12RewardRecordSaveForm {
    @ApiModelProperty(value = "店铺",name ="shopId")
    @NotNull(message = "店铺ID不能为空")
    private long shopId;
    @ApiModelProperty(value = "打赏用户",name ="userId")
    @NotNull(message = "用户ID不能为空")
    private long userId;
    @ApiModelProperty(value = "打赏金额",name ="money")
    @NotNull(message = "打赏金额不能为空")
    private BigDecimal money;
    @ApiModelProperty(value = "打赏用户",name ="userName")
    private String userName;



}
