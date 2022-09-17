package com.youqiancheng.mybatis.model;

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
@ApiModel(value = "分享实体")
public class B10ShareDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "类型：店铺，商品，文章",name ="type")
    private int type;
    @ApiModelProperty(value = "关联ID",name ="relationId")
    private long relationId;
    @ApiModelProperty(value = "分享至那：类型",name ="toType")
    private String toType;
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
