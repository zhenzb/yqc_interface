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
 * Date  2020-04-20
 */
@Data
@ApiModel(value = "商家账户实体")
@TableName("f05_shop_account")
public class F05ShopAccountDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "账户ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "账户总余额",name ="accountBalance")
    private BigDecimal accountBalance;
    @ApiModelProperty(value = "可用余额",name ="availableBalance")
    private BigDecimal availableBalance;
    @ApiModelProperty(value = "可提现金额",name ="availableWithdrawMoney")
    private BigDecimal availableWithdrawMoney;
    @ApiModelProperty(value = "累计提现金额",name ="hasWithdrawMoney")
    private BigDecimal hasWithdrawMoney;
    @ApiModelProperty(value = "提现中金额",name ="inWithdrawMoney")
    private BigDecimal inWithdrawMoney;
    @ApiModelProperty(value = "微信账户（用于提现）",name ="wechatAccount")
    private String wechatAccount;
    @ApiModelProperty(value = "支付宝账户（用于提现）",name ="alipayAccount")
    private String alipayAccount;
    @ApiModelProperty(value = "银联账户（用于提现）",name ="unionpayAccount")
    private String unionpayAccount;
    @ApiModelProperty(value = "国际银联账户（用于提现）",name ="internationalUnionpayAccount")
    private String internationalUnionpayAccount;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
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

    @TableField(exist = false)
    @ApiModelProperty(value = "总收益",name ="totalProfit")
    private BigDecimal totalProfit;
    @TableField(exist = false)
    @ApiModelProperty(value = "今日收益",name ="dayProfit")
    private BigDecimal dayProfit;
    @TableField(exist = false)
    @ApiModelProperty(value = "昨日收益",name ="yesterdayProfit")
    private BigDecimal yesterdayProfit;

}
