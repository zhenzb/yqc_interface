package com.handongkeji.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class RouterDTO implements Serializable {
    @ApiModelProperty("菜单URL")
    private String path;
    private String component;
    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("菜单名称")
    private String name;

    private String redirect;
    /**
     * 是否为叶子节点
     */
    private boolean leaf;
    private boolean menuShow;
    @ApiModelProperty("父菜单ID，一级菜单为0")
    private Long parentId;
    @ApiModelProperty("菜单图标")
    private String iconCls;
    /**
     * 子节点
     */
    List<RouterDTO> children;
    @ApiModelProperty("排序")
    private Integer orderNum;
    @ApiModelProperty("类型   0：目录   1：菜单   2：按钮")
    private Integer type;

    /**
     * 封装成 树形结构
     * idParam : pid的值
     */
    public static List<RouterDTO> buildList(List<RouterDTO> nodes, Long idParam) {
        if (nodes == null) {
            return null;
        }
        List<RouterDTO> topNodes = new ArrayList<>();
        for (RouterDTO child : nodes) {
            Long pid = child.getParentId();
            // 第一层级 节点
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(child);
                continue;
            }
            //如果不是第一层级节点，遍历所有节点，寻找他的父节点
            for (RouterDTO parent : nodes) {
                Long id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(child);
                    //parent.setLeaf(false);
                    continue;
                } else {
                   // parent.setLeaf(true);
                }
            }
        }
        return topNodes;
    }

//    public static List<RouterDTO> buildList2(List<RouterDTO> nodes, Long idParam) {
//        if (nodes == null) {
//            return null;
//        }
//        List<RouterDTO> topNodes = new ArrayList<>();
//        for (RouterDTO child : nodes) {
//            Long pid = child.getParentId();
//            // 第一层级 节点
//            if (pid == null || idParam.equals(pid)) {
//                topNodes.add(child);
//            }
//            //如果不是第一层级节点，遍历所有节点，寻找他的子节点
//            for (RouterDTO parent : nodes) {
//                Long id = child.getId();
//                Long parentId = parent.getParentId();
//                if (id != null && id.equals(parentId)) {
//                    child.getChildren().add(parent);
//                    parent.setLeaf(false);
//                    continue;
//                } else {
//                    parent.setLeaf(true);
//                }
//            }
//        }
//        return topNodes;
//    }
}
