package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.handongkeji.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "菜单管理")
@TableName("a03_permission")
public class A03Permission extends BaseEntity {
    @TableId(value = "menu_id",type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long menuId;
    @ApiModelProperty("父菜单ID，一级菜单为0")
    private Long parentId;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单URL")
    private String url;
    @ApiModelProperty("授权(多个用逗号分隔，如：user:list,user:create)")
    private String perms;
    @ApiModelProperty("类型   0：目录   1：菜单   2：按钮")
    private Integer type;
    @ApiModelProperty("菜单图标")
    private String icon;
    @ApiModelProperty("排序")
    private Integer orderNum;
    @ApiModelProperty("")
    private String component;
    @ApiModelProperty("重定向")
    private String redirect;



    @TableField(exist = false)
    List<A03Permission> children;
    public static List<A03Permission> buildList(List<A03Permission> nodes, Long idParam) {
        if (nodes == null) {
            return null;
        }
        List<A03Permission> topNodes = new ArrayList<>();
        for (A03Permission child : nodes) {
            Long pid = child.getParentId();
            // 第一层级 节点
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(child);
                continue;
            }
            //如果不是第一层级节点，遍历所有节点，寻找他的父节点
            for (A03Permission parent : nodes) {
                Long id = parent.getMenuId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(child);
                    continue;
                } else {
                }
            }
        }
        return topNodes;
    }
}