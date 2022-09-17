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
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "版本更新实体")
@TableName("a13_sys_version")
public class A13SysVersionDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "版本说明",name ="versionExplain")
    private String versionExplain;
    @ApiModelProperty(value = "下载地址",name ="url")
    private String url;
    @ApiModelProperty(value = "最低运行版本",name ="lowest")
    private String lowest;
    @ApiModelProperty(value = "App版本",name ="version")
    private String version;
    @ApiModelProperty(value = "App名称",name ="name")
    private String name;
    @ApiModelProperty(value = "2：强制更新  1：非强制",name ="isUpdate")
    private int isUpdate;
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
