package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "红包规则实体")
@TableName("e03_redenvelopes_rule")
public class E03RedenvelopesRuleDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "规则",name ="content")
    private String content;
    @ApiModelProperty(value = "红包图片",name ="url")
    private String url;
    @ApiModelProperty(value = "红包图片灰色",name ="disableUrl")
    private String disableUrl;

}
