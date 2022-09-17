package com.youqiancheng.form.manager.withdraw;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--账户管理--提现列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class WithdrawalApplicationPageForm {
    @ApiModelProperty("审核状态")
    private Integer examineStatus; //1:待审核 2：审核通过  3：审核拒绝
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "用户手机号",name = "mobile")
    private String mobile;
    @ApiModelProperty(value = "提现角色 1:商家 2：用户",name ="type")
    private Integer type;
}


