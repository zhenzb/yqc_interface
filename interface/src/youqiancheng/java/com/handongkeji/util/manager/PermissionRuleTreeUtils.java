//package com.handongkeji.util.manager;
//
//import com.youqiancheng.mybatis.model.AuthPermissionRule;
//import com.youqiancheng.mybatis.model.BusCommodityCategory;
//import com.youqiancheng.mybatis.model.SysArea;
//import com.youqiancheng.vo.manager.AuthPermissionRuleMergeVo;
//import com.youqiancheng.vo.result.CommodityCategoryVo;
//import com.youqiancheng.vo.result.SysAreaVo;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 权限规则生成树形节点工具类
// */
//public class PermissionRuleTreeUtils {
//
//    /**
//     * 多维数组
//     */
//    public static List<AuthPermissionRuleMergeVo> merge(List<AuthPermissionRule> authPermissionRuleList,
//                                                        Long pid) {
//
//        List<AuthPermissionRuleMergeVo> authPermissionRuleMergeVoList = new ArrayList<>();
//        for (AuthPermissionRule v : authPermissionRuleList) {
//            AuthPermissionRuleMergeVo authPermissionRuleMergeVO = new AuthPermissionRuleMergeVo();
//            BeanUtils.copyProperties(v, authPermissionRuleMergeVO);
//            if (pid.equals(v.getPid())) {
//                authPermissionRuleMergeVO.setChildren(merge(authPermissionRuleList, v.getId()));
//                authPermissionRuleMergeVoList.add(authPermissionRuleMergeVO);
//            }
//        }
//
//        return authPermissionRuleMergeVoList;
//    }
//
//
//    public static List<CommodityCategoryVo> mergeCommodityCategory(List<BusCommodityCategory> categoryList, Integer pid) {
//        List<CommodityCategoryVo> categoryVoList = new ArrayList<>();
//        for (BusCommodityCategory v : categoryList) {
//            CommodityCategoryVo categoryVo = new CommodityCategoryVo();
//            BeanUtils.copyProperties(v, categoryVo);
//            if (pid.equals(v.getParentid())) {
//                categoryVo.setChildren(mergeCommodityCategory(categoryList, v.getCategoryid()));
//                categoryVoList.add(categoryVo);
//            }
//        }
//
//        return categoryVoList;
//    }
//
//    public static List<SysAreaVo> mergeSysArea(List<SysArea> areaList, Integer pid) {
//        List<SysAreaVo> sysAreaVos = new ArrayList<>();
//        for (SysArea v : areaList) {
//            SysAreaVo sysAreaVo = new SysAreaVo();
//            BeanUtils.copyProperties(v, sysAreaVo);
//            if (pid.equals(v.getParentareaid())) {
//                sysAreaVo.setChildren(mergeSysArea(areaList, v.getAreaid()));
//                sysAreaVos.add(sysAreaVo);
//            }
//        }
////           sysAreaVos=areaList.stream().map(item->{
////            SysAreaVo sysAreaVo = new SysAreaVo();
////            BeanUtils.copyProperties(item, sysAreaVo);
////            if (pid.equals(item.getParentareaid())) {
////                sysAreaVo.setChildren(mergeSysArea(areaList, item.getAreaid()));
////            }
////            return sysAreaVo;
////        }).collect(Collectors.toList());
//        return sysAreaVos;
//    }
//}
