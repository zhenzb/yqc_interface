package com.youqiancheng.form.manager.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--订单管理--订单列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
@ApiModel("售后总管理查询表")
public class CustomerServiceManageForm {
    @ApiModelProperty("售后单号")
    private String serviceNo;
    @ApiModelProperty("商家名称")
    private String shopName;
    @ApiModelProperty("用户昵称")
    private String userName;
    @ApiModelProperty("状态：1、待审核，2通过，3、拒绝，4、已退款")
    private int status;
}


