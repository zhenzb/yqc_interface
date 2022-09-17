package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员角色表")
@TableName("a04_role_admin")
public class A04RoleAdmin extends BaseEntity {
    @TableId(value = "id")
    @ApiModelProperty("管理员ID")
    private Long id;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("角色ID")
    private Long adminId;

}
