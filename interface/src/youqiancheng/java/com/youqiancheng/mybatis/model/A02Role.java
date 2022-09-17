package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "角色表")
@TableName("a02_role")
public class A02Role extends BaseEntity {
    @TableId(value = "id")
    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色父ID")
    private Long pid;
    @TableField(value = "status",fill = FieldFill.INSERT)
    @ApiModelProperty("角色状态 2表示禁用 1表示启用")
    private int status;
    @ApiModelProperty("角色备注")
    private String remark;
    @ApiModelProperty("排序")
    private Integer listorder;


    @TableField(exist = false)
    @ApiModelProperty(value = "菜单ID列表",name="menuIds")
    private List<Long> menuIds;
}
