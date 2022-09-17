package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现设置实体")

@TableName("f09_withdrawal_set")
public class F09WithdrawalSetDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "提现方式:手动打款，自动打款",name ="type")
    private int type;
    @ApiModelProperty(value = "最低提现金额",name ="lowerLimit")
    private BigDecimal lowerLimit;
    @ApiModelProperty(value = "服务费比例",name ="serviceRatio")
    private BigDecimal serviceRatio;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;

}
