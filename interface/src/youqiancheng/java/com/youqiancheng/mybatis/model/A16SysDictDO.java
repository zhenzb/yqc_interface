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
@ApiModel(value = "字典表实体")
@TableName("a16_sys_dict")
public class A16SysDictDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "编号(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "标签名",name ="name")
    private String name;
    @ApiModelProperty(value = "数据值",name ="value")
    private int value;
    @ApiModelProperty(value = "类型",name ="type")
    private String type;
    @ApiModelProperty(value = "描述",name ="description")
    private String description;
    @ApiModelProperty(value = "排序（升序）",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "父级编号",name ="pid")
    private long pid;
    @ApiModelProperty(value = "创建者",name ="createPerson")
    private int createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "更新者",name ="updatePerson")
    private long updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "备注信息",name ="remark")
    private String remark;
    @ApiModelProperty(value = "删除标记",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;



}
