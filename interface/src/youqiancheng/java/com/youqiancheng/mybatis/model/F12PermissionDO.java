package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "菜单管理实体")
@TableName("f12_permission")
public class F12PermissionDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //@ApiModelProperty(value = "(数据库自增)",name ="menuId",example = "1",notes = "自增主键在新增时无需传入")
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;
    @ApiModelProperty(value = "父菜单ID，一级菜单为0",name ="parentId")
    private Long parentId;
    @ApiModelProperty(value = "菜单名称",name ="name")
    private String name;
    @ApiModelProperty(value = "菜单URL",name ="url")
    private String url;
    @ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)",name ="perms")
    private String perms;
    @ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮",name ="type")
    private Integer type;
    @ApiModelProperty(value = "菜单图标",name ="icon")
    private String icon;
    @ApiModelProperty(value = "排序",name ="orderNum")
    private int orderNum;
    @ApiModelProperty(value = "",name ="component")
    private String component;
    @ApiModelProperty(value = "重定向",name ="redirect")
    private String redirect;
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
    @ApiModelProperty(value = "删除标记",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "菜单类型：业务菜单，管理菜单",name ="typeFlag")
    private int typeFlag;



}
