package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "评论实体")
@TableName("c05_comment")
public class C05CommentDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商品ID",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
    @ApiModelProperty(value = "商品缩略图图片",name ="goodsIcon")
    private String goodsIcon;
    @ApiModelProperty(value = "评论内容",name ="content")
    private String content;
    @ApiModelProperty(value = "评论回复",name ="reply")
    private String reply;
    @ApiModelProperty(value = "评价星级",name ="level")
    private int level;
    @ApiModelProperty(value = "评价用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "用户名称",name ="userName")
    private String userName;
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
    @TableField(exist = false)
    @ApiModelProperty(value = "用户头像",name ="pic")
    private String pic;
    @TableField(exist = false)
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;

    @TableField(exist = false)
    @ApiModelProperty(value = "被评论的总数",name ="CommensTotalNumber")
    private int replyComentNumber;


}
