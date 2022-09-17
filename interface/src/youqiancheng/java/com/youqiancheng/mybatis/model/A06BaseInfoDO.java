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
@ApiModel(value = "A11_基本信息实体")
@TableName("a06_base_info")
public class A06BaseInfoDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private int id;
    @ApiModelProperty(value = "平台名称",name ="platformName")
    private String platformName;
    @ApiModelProperty(value = "平台电话",name ="platformPhone")
    private String platformPhone;
    @ApiModelProperty(value = "简介",name ="content")
    private String content;
    @ApiModelProperty(value = "浏览量",name ="browseVolume" ,allowEmptyValue = true)
    private int browseVolume;
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
