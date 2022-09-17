package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现规则实体")
@TableName("f10_withdrawal_rule")
public class F10WithdrawalRuleDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "下限（大于等于）",name ="lowerLimit")
    private BigDecimal lowerLimit;
    @ApiModelProperty(value = "上限（小于）",name ="upperLimit")
    private BigDecimal upperLimit;
    @ApiModelProperty(value = "服务费比例",name ="serviceRatio")
    private BigDecimal serviceRatio;

}
