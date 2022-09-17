package com.youqiancheng.util;


import java.util.*;

public class BuildTree {
//    public static <T> Tree<T> build(List<Tree<T>> nodes) {
//        if (nodes == null) {
//            return null;
//        }
//        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
//        for (Tree<T> child : nodes) {
//            String pid = child.getParentId();
//            if (pid == null || "0".equals(pid)) {
//                topNodes.add(child);
//                continue;
//            }
//            for (Tree<T> parent : nodes) {
//                String id = parent.getId();
//                if (id != null && id.equals(pid)) {
//                    parent.getChildren().add(child);
//                    child.setHasParent(true);
//                    parent.setChildren(true);
//                    continue;
//                }
//            }
//        }
//        Tree<T> root = new Tree<T>();
//        if (topNodes.size() == 1) {
//            root = topNodes.get(0);
//        } else {
//            root.setId("-1");
//            root.setParentId("");
//            root.setHasParent(false);
//            root.setChildren(true);
//            root.setChecked(true);
//            root.setChildren(topNodes);
//            root.setText("顶级节点");
//            Map<String, Object> state = new HashMap<>(16);
//            state.put("opened", true);
//            root.setState(state);
//        }
//        return root;
//    }

    /**
     *将参数整合成树形结构
      * @param nodes
     *@return ({@link com.youqiancheng.util.Tree<T>})
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public  <T> Tree<T> build(List<Tree<T>> nodes) {
        //如果参数为空，返回空
        if (nodes == null) {
            return null;
        }
        Collections.sort(nodes, Comparator.comparingInt(Tree::getOrderNumber));

        //定义返回树形列表
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
        //循环树形列表——定位子节点
        for (Tree<T> child : nodes) {
            //获取子节点的父节点ID
            String pid = child.getParentId();
            //如果父节点ID为空或者0则将节点放入顶级节点数组中
            if (pid == null || "0".equals(pid)) {
                topNodes.add(child);
                continue;
            }
            //否则重新循环节点；定位父节点
            for (Tree<T> parent : nodes) {
                //获取父节点ID
                String id = parent.getId();
                //如果父节点ID不为空且父节点ID等于子节点的父节点ID
                //则将子节点添加到父节点的子节点列表中
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(child);
                    //设置子节点存在父节点标记
                    child.setHasParent(true);
                    //设置父节点存在子节点标记
                    parent.setChildren(true);
                    continue;
                }
            }
        }
        //设置顶级节点的父节点——将顶级节点放入该节点子节点数组中
        //虚拟设定一层顶级节点，方便前端展示——可去除直接返回topNodes
        Tree<T> root = new Tree<T>();
        root.setId("-1");
        root.setParentId("");
        root.setHasParent(false);
        root.setChildren(true);
        root.setChecked(true);
        root.setChildren(topNodes);
        root.setText("虚拟顶级节点");
        Map<String, Object> state = new HashMap<>(16);
        state.put("opened", true);
        root.setState(state);
        return root;
    }
    /**
     *同上，去除虚拟设置顶级节点后的效果
     * 区别：传入参数多了一个顶级节点的标记
     * 如果节点的父节点为idparam则该节点为顶级父节点
     * @param nodes
     * @param idParam
     *@return ({@link java.util.List<com.youqiancheng.util.Tree<T>>})
     *@throws
     *@author yutf
     *@date 2020/5/23
     *
     */
    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
        //如果节点数据为空则返回空
        if (nodes == null) {
            return null;
        }
        //定义顶级节点
        List<Tree<T>> topNodes = new ArrayList<Tree<T>>();
        //循环参数节点
        for (Tree<T> child : nodes) {
            //获取节点的父节点ID
            String pid = child.getParentId();
            //查验是否为顶级节点
            if (pid == null || idParam.equals(pid)) {
                //如果节点的父节点ID等于传入的节点ID,则保存为顶级节点
                topNodes.add(child);
                continue;
            }
            //否则为子节点，定位父节点并绑定
            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                //如果父节点ID不为空且父节点ID等于子节点的父节点ID
                //则将子节点添加到父节点的子节点列表中
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(child);
                    //设置子节点存在父节点标记
                    child.setHasParent(true);
                    //设置父节点存在子节点标记
                    parent.setChildren(true);
                    continue;
                }
            }
        }
        return topNodes;
    }

}
