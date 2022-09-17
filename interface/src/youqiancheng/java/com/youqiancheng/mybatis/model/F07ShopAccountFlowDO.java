package com.youqiancheng.mybatis.model;

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
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "账户流水实体")
@TableName("f07_shop_account_flow")
public class F07ShopAccountFlowDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "流水ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "账户Id",name ="accountId")
    private long accountId;
    @ApiModelProperty(value = "原账户金额",name ="originalMoney")
    private BigDecimal originalMoney;
    @ApiModelProperty(value = "流水金额",name ="money")
    private BigDecimal money;
    @ApiModelProperty(value = "最终账户金额",name ="finalMoney")
    private BigDecimal finalMoney;
    @ApiModelProperty(value = "流水出入类型:提现支出；销售收入",name ="type")
    private int type;
    @ApiModelProperty(value = "来源ID",name ="sourceId")
    private long sourceId;
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
    @ApiModelProperty(value = "是否面对面发货",name ="isFace")
    private int isFace;

    @ApiModelProperty(value = "是否已转化为可提现金额",name ="flowStatus")
    private int flowStatus;



}
