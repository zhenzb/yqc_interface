package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
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
@ApiModel(value = "主营项目实体")
@TableName("f03_main_project")
public class F03MainProjectDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "商家类型",name ="type")
    private int type;
    @ApiModelProperty(value = "主营项目名称",name ="name")
    private String name;
    @ApiModelProperty(value = "主营项目-图标",name ="picUrl")
    private String picUrl;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;

    @TableField(value = "status",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "状态",name ="status")
    private int status;

}
