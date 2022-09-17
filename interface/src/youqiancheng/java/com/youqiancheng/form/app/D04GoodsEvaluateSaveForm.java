package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "APP——用户商品评价记录保存实体")
public class D04GoodsEvaluateSaveForm {
    @ApiModelProperty(value = "商品id",name ="goodsId")
    @Min(value = 1,message = "商品ID不为0")
    private long goodsId;
    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
    @ApiModelProperty(value = "商品缩略图图片",name ="icon")
    private String icon;
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户ID不为0")
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
    @Min(value = 1,message = "订单明细ID不为0")
    private long orderItemId;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "图片列表",name ="picList")
    private List<String> picList;



}
