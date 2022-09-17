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
 * Date  2020-07-23
 */
@Data
@ApiModel(value = "实体")
@TableName("c13_publicity_autio")
public class C13PublicityAutioDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "自增主键(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "商家id",name ="shopId")
    private Long shopId;
    @ApiModelProperty(value = "宣传id",name ="publicityId")
    private Long publicityId;
    @ApiModelProperty(value = "音频文件名称",name ="autioName")
    private String autioName;
    @ApiModelProperty(value = "音频路径",name ="autioUrl")
    private String autioUrl;
    @ApiModelProperty(value = "排序",name ="carouselSort")
    private Integer carouselSort;
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
    private Integer deleteFlag;



}
