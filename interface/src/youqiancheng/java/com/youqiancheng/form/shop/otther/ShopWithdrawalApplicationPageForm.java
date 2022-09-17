package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 商家管理--账户管理--提现列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class ShopWithdrawalApplicationPageForm {
    @ApiModelProperty("审核状态")
    private Integer examineStatus;
    @ApiModelProperty("账户ID")
    private Integer accountId;
}


