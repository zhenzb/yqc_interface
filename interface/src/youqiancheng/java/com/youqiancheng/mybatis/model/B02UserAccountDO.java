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
@ApiModel(value = "用户账户实体")
@TableName("b02_user_account")
public class B02UserAccountDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "账户ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户Id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "国家ID（商品一级分类）",name ="countryId")
    private int countryId;
    @ApiModelProperty(value = "国家（商品一级分类）",name ="countryName")
    private String countryName;
    @ApiModelProperty(value = "账户余额",name ="accountBalance")
    private BigDecimal accountBalance;
    @ApiModelProperty(value = "可提现金额",name ="withdrawal_balance")
    private BigDecimal withdrawalBalance;

    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "可视化收益金额",name ="promotionMoeny")
    private BigDecimal promotionMoeny;





}
