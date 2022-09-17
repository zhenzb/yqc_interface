package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现申请实体")
@TableName("f06_withdrawal_application")
public class F06WithdrawalApplicationDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "账户ID",name ="accountId")
    private long accountId;
    @ApiModelProperty(value = "提现金额",name ="withdrawalMoney")
    private BigDecimal withdrawalMoney;
    @ApiModelProperty(value = "服务费",name ="serviceMoney")
    private BigDecimal serviceMoney;
    @ApiModelProperty(value = "实际提现金额",name ="actualWithdrawalMoney")
    private BigDecimal actualWithdrawalMoney;
    @ApiModelProperty(value = "原服务费比例",name ="originalServiceRatio")
    private BigDecimal originalServiceRatio;
    @ApiModelProperty(value = "实际服务费比例",name ="actualServiceRatio")
    private BigDecimal actualServiceRatio;
    @ApiModelProperty(value = "提现时本月累计红包金额",name ="currentRedenvelopes")
    private BigDecimal currentRedenvelopes;
    @ApiModelProperty(value = "提现方式",name ="type")
    private int type;
    @ApiModelProperty(value = "单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "提现账户",name ="account")
    private String account;
    @ApiModelProperty(value = "转账单号",name ="transferNo")
    private String transferNo;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "申请时间",name ="applyTime")
    private LocalDateTime applyTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "审核时间",name ="examineTime")
    private LocalDateTime examineTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "理由",name ="reason")
    private String reason;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nick;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String name;
}
