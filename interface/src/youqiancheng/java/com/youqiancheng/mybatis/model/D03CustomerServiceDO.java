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
@ApiModel(value = "售后表实体")
@TableName("d03_customer_service")
public class D03CustomerServiceDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "订单id",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "订单编码",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "售后单号",name ="serviceNo")
    private String serviceNo;
    @ApiModelProperty(value = "售后方式，类型：退款，换货",name ="type")
    private int type;
    @ApiModelProperty(value = "退款金额",name ="money")
    private BigDecimal money;
    @ApiModelProperty(value = "是否需要退货",name ="isReturnGoods")
    private int isReturnGoods;
    @ApiModelProperty(value = "原因",name ="reason")
    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "申请售后时间",name ="time")
    private LocalDateTime time;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "审核时间",name ="examineTime")
    private LocalDateTime examineTime;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "订单明细id",name ="orderItemId")
    private long orderItemId;
    @ApiModelProperty(value = "拒绝理由",name ="refuseReason")
    private String refuseReason;
    @TableField(exist = false)
    @ApiModelProperty(value = "流水号",name ="tradeNo")
    private String tradeNo;
    @ApiModelProperty(value = "退款号",name ="refundNo")
    private String refundNo;
    @ApiModelProperty(value = "退款类型",name ="refundType")
    private int refundType;
    @ApiModelProperty(value = "退款说明",name ="explain")
    private String explain;

}
