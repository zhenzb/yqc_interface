package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "红包街实体")
@TableName("e01_redenvelopes_street")
public class E01RedenvelopesStreetDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "一级产品分类ID",name ="categoryId")
    private long categoryId;
    @ApiModelProperty(value = "街道名称",name ="name")
    private String name;
    @ApiModelProperty(value = "街道图片",name ="url")
    private String url;
    @ApiModelProperty(value = "红包金额",name ="money")
    private BigDecimal money;
    @ApiModelProperty(value = "消费金额",name ="consumeMoney")
    private BigDecimal consumeMoney;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "是否免费",name ="isFree")
    private int isFree;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "免费开始时间",name ="startTime")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "免费结束时间",name ="endTime")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "红包个数",name ="packageNum")
    private int packageNum;
}
