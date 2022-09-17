package com.youqiancheng.service.shop.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.mybatis.dao.shop.system.F12PermissionDao;
import com.youqiancheng.mybatis.dao.shop.system.F14RolePermissionDao;
import com.youqiancheng.mybatis.model.F12PermissionDO;
import com.youqiancheng.mybatis.model.F14RolePermissionDO;
import com.youqiancheng.service.shop.system.ShopPermissionService;
import com.youqiancheng.util.BuildTree;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Transactional
public class ShopPermissionServiceImpl implements ShopPermissionService {

    @Resource
    private F12PermissionDao f12PermissionDao;
    @Resource
    private F14RolePermissionDao f14RolePermissionDao;

    /**
    　* @Description: 查询所有菜单
    　*/
    @Override
    public List<RouterDTO> RouterList() {
        List<F12PermissionDO> shopPermissionList = f12PermissionDao.selectList(null);
        if(CollectionUtils.isEmpty(shopPermissionList)){
            return null;
        }
        List<RouterDTO> routerDTOList = new ArrayList<>();
        for (F12PermissionDO f12PermissionDO : shopPermissionList) {
            RouterDTO routerDTO = new RouterDTO();
            routerDTO.setId(f12PermissionDO.getMenuId());
            routerDTO.setParentId(f12PermissionDO.getParentId());
            routerDTO.setChildren(new ArrayList<>());
            //type 类型   0：目录   1：菜单   2：按钮
            routerDTO.setLeaf(null != f12PermissionDO.getType() && f12PermissionDO.getType() == 1);
            routerDTO.setComponent(f12PermissionDO.getComponent());
            routerDTO.setIconCls(f12PermissionDO.getIcon());
            //排序规则
            routerDTO.setOrderNum(f12PermissionDO.getOrderNum());
            routerDTO.setPath(f12PermissionDO.getUrl());
            routerDTO.setName(f12PermissionDO.getName());
            routerDTO.setMenuShow(true);
            routerDTO.setType(f12PermissionDO.getType());
            routerDTOList.add(routerDTO);
        }
        //将权限 封装成树形结构
        return RouterDTO.buildList(routerDTOList,0l);
    }
    /**
    　* @Description: 插入菜单
    　*/
    @Override
    public boolean insertShopPermission(F12PermissionDO f12PermissionDO) {
        // 查询是否存在
        F12PermissionDO byName = f12PermissionDao.findShopPermissionByName(f12PermissionDO.getName());
        if (byName != null) {
            throw new JsonException(ResultEnum.DATA_REPEAT, "当前权限规则已存在");
        }
        f12PermissionDO.setCreateTime(LocalDateTime.now());
        f12PermissionDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        return f12PermissionDao.insert(f12PermissionDO) == 1? true :false;
    }
    /**
    　* @Description: 根据id 删除菜单
    　*/
    @Override
    public boolean deleteById(Long menuId) {
        if(menuId == 0){
            return false;
        }
        EntityWrapper<F12PermissionDO> ew = new EntityWrapper<>();
        ew.eq("menu_id",menuId);
        f12PermissionDao.delete(ew);
        //菜单要级联删除
        deleteByPid(menuId);
        return true;
    }

    //级联删除 菜单
    private void deleteByPid(Long menuId) {
        EntityWrapper<F12PermissionDO> ew = new EntityWrapper<>();
        ew.eq("parent_id",menuId);
        List<F12PermissionDO> f12PermissionList = f12PermissionDao.selectList(ew);
        if(!CollectionUtils.isEmpty(f12PermissionList)){
            for (F12PermissionDO f12PermissionDO : f12PermissionList) {
                EntityWrapper<F12PermissionDO> ew2 = new EntityWrapper<>();
                ew2.eq("menu_id",f12PermissionDO.getMenuId());
                f12PermissionDao.delete(ew2);
                deleteByPid(f12PermissionDO.getMenuId());//递归删除 子菜单
            }
        }
    }

    /**
    　* @Description: 编辑菜单
    　*/
    @Override
    public boolean updateShopPermission(F12PermissionDO f12Permission) {
        if (f12Permission == null){
            return false;
        }
        EntityWrapper<F12PermissionDO> ew = new EntityWrapper<>();
        ew.eq("menu_id",f12Permission.getMenuId());
        return f12PermissionDao.update(f12Permission,ew) == 1? true : false;
    }


    @Override
    public Tree<F12PermissionDO> getTree() {
        List<Tree<F12PermissionDO>> trees = new ArrayList<>();
        List<F12PermissionDO> menuList = f12PermissionDao.selectByMap(new HashMap<>());
        if (!CollectionUtils.isEmpty(menuList)){
            for (F12PermissionDO menuDO : menuList) {
                Tree<F12PermissionDO> tree = new Tree<>();
                tree.setId(menuDO.getMenuId().toString());
                tree.setParentId(menuDO.getParentId().toString());
                tree.setText(menuDO.getName());
                tree.setObject(menuDO);
                trees.add(tree);
            }
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return new BuildTree().build(trees);
    }

    /**
    　* @Description: 根据权限 查找对应角色 是否存在
    　*/
    @Override
    public List<Long> findShopRoleByPermissionId(Long menuId) {
        EntityWrapper<F14RolePermissionDO> ew = new EntityWrapper<>();
        ew.eq("permission_id",menuId);
        List<F14RolePermissionDO> f14RolePermissionList = f14RolePermissionDao.selectList(ew);
        if (CollectionUtils.isEmpty(f14RolePermissionList)){
            return null;
        }
        return f14RolePermissionList.stream().map(p->p.getId()).collect(Collectors.toList());
    }
}
