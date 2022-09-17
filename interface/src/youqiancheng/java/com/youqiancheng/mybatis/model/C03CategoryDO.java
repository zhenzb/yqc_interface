package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商品分类实体")
@TableName("c03_category")
public class C03CategoryDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "分类ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private int id;
    @ApiModelProperty(value = "父级ID",name ="parentId")
    private int parentId;
    @ApiModelProperty(value = "分类名称",name ="name")
    private String name;
    @ApiModelProperty(value = "图标",name ="url")
    private String url;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "状态",name ="status")
    private int status;

}
