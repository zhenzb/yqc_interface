package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "角色权限表")
@TableName("a05_role_permission")
public class A05RolePermission extends BaseEntity {
    @TableId(value = "id")
    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("角色id")
    private long roleId;
    @ApiModelProperty("权限ID")
    private long permissionId;


}
