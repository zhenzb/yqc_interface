package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "宣传实体")
@TableName("c10_publicity")
public class C10PublicityDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "一级分类",name ="categoryId")
    private long categoryId;
    @ApiModelProperty(value = "二级分类ID",name ="secondCategoryId")
    private long secondCategoryId;
    @ApiModelProperty(value = "三级分类",name ="thirdCategoryId")
    private long thirdCategoryId;
    @ApiModelProperty(value = "店铺Id",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "店铺名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "简介",name ="goodsDesc")
    private String goodsDesc;
    @ApiModelProperty(value = "动态主题图",name ="icon")
    private String icon;
    @ApiModelProperty(value = "内容链接地址：视频",name ="contentUrl")
    private String contentUrl;
    @ApiModelProperty(value = "时长",name ="duration")
    private String duration;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private long orderNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间",name ="publicTime")
    private LocalDateTime publicTime;
    @ApiModelProperty(value = "上架状态",name ="isSale")
    private int isSale;
    @ApiModelProperty(value = "审核状态",name ="examineStatus")
    private int examineStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "审核时间",name ="examineTime")
    private LocalDateTime examineTime;
    @ApiModelProperty(value = "收藏量",name ="collectionVolume")
    private String collectionVolume;
    @ApiModelProperty(value = "浏览量",name ="browseVolume")
    private int browseVolume;
/*    @TableField(exist = false )
    @ApiModelProperty(value = "一级分类",name ="categoryIdName")
    private String categoryIdName;
    @TableField(exist = false )
    @ApiModelProperty(value = "二级分类",name ="secondCategoryIdName")
    private String secondCategoryIdName;
    @TableField(exist = false )
    @ApiModelProperty(value = "三级分类",name ="thirdCategoryIdName")
    private String thirdCategoryIdName;*/
    @TableField(exist = false)
    @ApiModelProperty(value = "图片数组",name ="picArr")
    private String[] picArr;
    @TableField(exist = false)
    @ApiModelProperty(value = "评论数",name ="commentCount")
    private int commentCount;
    @ApiModelProperty(value = "拒绝理由",name ="reason")
    private String reason;

  @ApiModelProperty(value = "内容链接地址：音频",name ="audio")
   private String audio;
}
