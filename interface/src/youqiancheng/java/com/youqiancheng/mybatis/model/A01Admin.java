package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.handongkeji.dto.BaseEntity;
import com.handongkeji.dto.RouterDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "管理员表")
@TableName("a01_admin")
public class A01Admin extends BaseEntity {
    @TableId(value = "id")
    @ApiModelProperty("管理员ID")
    private Long id;
    @ApiModelProperty("管理员姓名")
    private String userName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("管理员密码")
    private String password;
    @ApiModelProperty("管理员电话")
    private String tel;
    @ApiModelProperty("管理员邮箱")
    private String email;
    @ApiModelProperty("管理员头像")
    private String avatar;
    @ApiModelProperty("管理员性别")
    private Short sex;
    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;
    @ApiModelProperty("省")
    private Integer province;

    @ApiModelProperty("市")
    private Integer city;
    @TableField(value = "status",fill = FieldFill.INSERT)
    @ApiModelProperty("状态  0被禁用 1 启用 默认是1")
    private Integer status;
    @ApiModelProperty("备注")
    private String remark;


    //权限集合
    @TableField(exist = false)
    List<RouterDTO> routerDTOList;

    //token
    @TableField(exist = false)
    @ApiModelProperty("返回的token")
    private String token;

    @TableField(exist = false)
    @ApiModelProperty("角色idList")
    private List<Long> roleList;
}