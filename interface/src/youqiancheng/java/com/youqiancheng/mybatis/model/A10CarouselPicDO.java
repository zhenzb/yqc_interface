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
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "轮播图实体")
@TableName("a10_carousel_pic")
public class A10CarouselPicDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "轮播图id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "标题",name ="title")
    private String title;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
    @ApiModelProperty(value = "内容地址（图片/视频）",name ="contentUrl")
    private String contentUrl;
    @ApiModelProperty(value = "APP链接",name ="appUrl")
    private String appUrl;
    @ApiModelProperty(value = "PC端链接",name ="picUrl")
    private String picUrl;
    @ApiModelProperty(value = "是否禁用",name ="status")
    private short status;
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
