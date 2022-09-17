package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
@ApiModel(value = "用户商品评价记录实体")
@TableName("d04_goods_evaluate")
public class D04GoodsEvaluateDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评价id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
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
    @ApiModelProperty(value = "评价记录图片",name ="d05EvaluatePicDO")
    @TableField(exist = false)
    private List<D05EvaluatePicDO> d05EvaluatePicDO;

    @ApiModelProperty(value = "用户头像",name ="pic")
    @TableField(exist = false)
    private String pic;
    @ApiModelProperty(value = "用户昵称",name ="nick")
    @TableField(exist = false)
    private String nick;

    @ApiModelProperty(value = "评价回复",name ="reply")
    private String reply;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "回复时间",name ="replyTime")
    private LocalDateTime replyTime;
}
