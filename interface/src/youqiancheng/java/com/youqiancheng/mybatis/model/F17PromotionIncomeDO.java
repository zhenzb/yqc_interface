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
 * Date  2021-03-06
 */
@Data
@ApiModel(value = "推广收益金额表实体")
@TableName("f17_promotion_income")
public class F17PromotionIncomeDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "推广收益id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "店铺可提现金额",name ="availableWithdrawMoney")
    private BigDecimal availableWithdrawMoney;
    @ApiModelProperty(value = "阿里税款",name ="alipayTaxes")
    private BigDecimal alipayTaxes;
    @ApiModelProperty(value = "平台利润",name ="yqcMoney")
    private BigDecimal yqcMoney;
    @ApiModelProperty(value = "用户推广毛利润金额",name ="userPromotionExpenses")
    private BigDecimal userPromotionExpenses;
    @ApiModelProperty(value = "推广者税后费用",name ="userAfterTax")
    private BigDecimal userAfterTax;
    @ApiModelProperty(value = "推广用户可提现金额",name ="userActualAmount")
    private BigDecimal userActualAmount;
    @ApiModelProperty(value = "推广店铺id",name ="shopId")
    private Long shopId;
    @ApiModelProperty(value = "推广用户id",name ="userId")
    private Long userId;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "是否显示 1:显示 2:隐藏",name ="isShow")
    private Integer isShow;
    @ApiModelProperty(value = "冗余字段",name ="redundancy")
    private String redundancy;

    @TableField(exist = false )
    private String nick;

    @TableField(exist = false )
    private String shopName;

}
