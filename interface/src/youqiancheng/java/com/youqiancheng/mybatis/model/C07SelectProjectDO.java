package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
@ApiModel(value = "商品选择属性项实体")
@TableName("c07_select_project")
public class C07SelectProjectDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "商品规格ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "属性项ID",name ="projectId")
    private long projectId;

    @ApiModelProperty(value = "商品表id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;
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
    @TableField(exist = false)
    @ApiModelProperty(value = "内容",name ="Content")
    private String content;
    @TableField(exist = false)
    @ApiModelProperty(value = "属性项",name ="name")
    private String name;


}
