package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handongkeji.dto.BaseEntity;
import com.youqiancheng.mybatis.model.F01ShopDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "PC视图——红包街视图实体——包含商家")
public class E01RedenvelopesStreetClientVO extends BaseEntity {
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
    @ApiModelProperty(value = "街区下——商家信息",name ="shopList")
    private List<F01ShopDO> shopList;
}
