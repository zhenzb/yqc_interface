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
 * Date  2020-06-01
 */
@Data
@ApiModel(value = "异步信息错误记录实体")
@TableName("a18_sys_message")
public class A18SysMessageDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "类型",name ="type")
    private Integer type;
    @ApiModelProperty(value = "单据号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "错误类型",name ="errorCode")
    private Integer errorCode;
    @ApiModelProperty(value = "错误描述",name ="errorDes")
    private String errorDes;
    @ApiModelProperty(value = "信息",name ="content")
    private String content;
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
