package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "角色表实体")
@TableName("f11_role")
public class F11RoleDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "角色ID(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "角色名称",name ="name")
    private String name;
    @ApiModelProperty(value = "角色父ID",name ="pid")
    private int pid;
    @ApiModelProperty(value = "角色状态",name ="status")
    private boolean status;
    @ApiModelProperty(value = "角色备注",name ="remark")
    private String remark;
    @ApiModelProperty(value = "排序",name ="listorder")
    private int listorder;
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
    @ApiModelProperty(value = "类型",name ="type")
    private int type;

    @TableField(exist = false)
    @ApiModelProperty(value = "菜单ID列表",name="menuIds")
    private List<Long> menuIds;

}
