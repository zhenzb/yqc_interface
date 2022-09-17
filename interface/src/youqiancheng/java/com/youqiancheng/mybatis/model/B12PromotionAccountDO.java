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
 * Date  2021-05-09
 */
@Data
@ApiModel(value = "实体")
@TableName("b12_promotion_account")
public class B12PromotionAccountDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "账户余额",name ="accountBalance")
    private BigDecimal accountBalance;
    @ApiModelProperty(value = "累计已提现金额",name ="accumulatedRevenue")
    private BigDecimal accumulatedRevenue;
    @ApiModelProperty(value = "",name ="userId")
    private Long userId;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="createTime")
    private LocalDateTime createTime;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="editTime")
    private LocalDateTime editTime;
    @ApiModelProperty(value = "是否已删除 1:正常 2:删除",name ="isDel")
    private Integer isDel;



}
