package com.youqiancheng.form.manager.redstreet;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class StreetSaveForm {
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
    private Integer orderNum;
    @ApiModelProperty(value = "是否免费",name ="isFree")
    private Integer isFree;
    @ApiModelProperty(value = "状态",name ="status")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "免费开始时间",name ="startTime")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "免费结束时间",name ="endTime")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "红包个数",name ="packageNum")
    private Integer packageNum;
}
