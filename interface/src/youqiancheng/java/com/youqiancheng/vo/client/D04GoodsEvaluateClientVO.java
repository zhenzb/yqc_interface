package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "PC视图——用户商品评价记录实体")
public class D04GoodsEvaluateClientVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评价id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商品id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
    @ApiModelProperty(value = "商品缩略图图片",name ="icon")
    private String icon;
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "订单id",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "评价星级",name ="star")
    private int star;
    @ApiModelProperty(value = "评价内容",name ="content")
    private String content;
    @ApiModelProperty(value = "评论是否有图",name ="hasPic")
    private short hasPic;
    @ApiModelProperty(value = "订单明细id",name ="orderItemId")
    private long orderItemId;
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
    @ApiModelProperty(value = "评价图片列表",name ="picList")
    private List<String> picList;


}
