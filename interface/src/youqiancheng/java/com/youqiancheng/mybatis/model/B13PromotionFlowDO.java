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
@TableName("b13_promotion_flow")
public class B13PromotionFlowDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "用户id",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "交易金额",name ="flowAmount")
    private BigDecimal flowAmount;
    @ApiModelProperty(value = "剩余金额",name ="remainingAmount")
    private BigDecimal remainingAmount;
    @ApiModelProperty(value = "操作类型 1:收入  2：支出",name ="operationType")
    private Integer operationType;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="createTime")
    private LocalDateTime createTime;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "",name ="editTime")
    private LocalDateTime editTime;
    @ApiModelProperty(value = "是否删除 1:正常 2:删除",name ="isDel")
    private Integer isDel;



}
