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
 * Date  2020-05-28
 */
@Data
@ApiModel(value = "商家账户收益流水实体")
@TableName("f15_shop_profit")
public class F15ShopProfitDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "账户ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private Long shopId;
    @ApiModelProperty(value = "总收益",name ="totalIncome")
    private BigDecimal totalIncome;
    @ApiModelProperty(value = "当日收益",name ="dayIncome")
    private BigDecimal dayIncome;
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
    private Integer deleteFlag;



}
