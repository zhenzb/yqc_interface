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
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商品规格实体")
@TableName("c09_goods_sku")
public class C09GoodsSkuDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "已选择的商品属性",name ="specifications")
    private String specifications;
    @ApiModelProperty(value = "对应规格商品数量",name ="num")
    private int num;
    @ApiModelProperty(value = "对应规格金额",name ="goodsPrice")
    private BigDecimal goodsPrice;
    @ApiModelProperty(value = "商品Id",name ="goodsId")
    private long goodsId;
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
    @ApiModelProperty(value = "版本号",name ="version")
    private int version;
    @ApiModelProperty(value = "商品描述",name ="goodsDesc")
    private String goodsDesc;



}
