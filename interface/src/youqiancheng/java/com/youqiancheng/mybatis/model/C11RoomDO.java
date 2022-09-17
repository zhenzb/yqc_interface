package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Data
@ApiModel(value = "旅游房间实体")
@TableName("c11_room")
public class C11RoomDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
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
    @ApiModelProperty(value = "房间号",name ="roomNo")
    private String roomNo;
    @ApiModelProperty(value = "放间名字",name ="name")
    private String name;
    @ApiModelProperty(value = "分类",name ="type")
    private String type;
    @ApiModelProperty(value = "图",name ="img")
    private String img;
    @ApiModelProperty(value = "描述",name ="goodsDesc")
    private String goodsDesc;
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



}
