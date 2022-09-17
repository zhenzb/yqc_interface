package com.youqiancheng.service.manager.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.mybatis.dao.system.A03PermissionMapper;
import com.youqiancheng.mybatis.dao.system.A05RolePermissionMapper;
import com.youqiancheng.mybatis.model.A03Permission;
import com.youqiancheng.mybatis.model.A05RolePermission;
import com.youqiancheng.service.manager.service.system.A03PermissionService;
import com.youqiancheng.util.BuildTree;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Transactional
public class A03PermissionServiceImpl implements A03PermissionService {

    @Resource
    private A03PermissionMapper a03PermissionMapper;
    @Resource
    private A05RolePermissionMapper a05RolePermissionMapper;

    /**
    　* @Description: 查询所有菜单
    　* @author shalongteng
    　* @date 2020/4/1 11:45
    　*/
    @Override
    public List<RouterDTO> RouterList() {
        List<A03Permission> a03PermissionList = a03PermissionMapper.selectList(null);
        if(a03PermissionList == null ||a03PermissionList.size() ==0){
            return null;
        }
        List<RouterDTO> routerDTOList = new ArrayList<>();
        for (A03Permission a03Permission : a03PermissionList) {
            RouterDTO routerDTO = new RouterDTO();
            routerDTO.setId(a03Permission.getMenuId());
            routerDTO.setParentId(a03Permission.getParentId());
            routerDTO.setChildren(new ArrayList<>());
            //type 类型   0：目录   1：菜单   2：按钮
            routerDTO.setLeaf(null != a03Permission.getType() && a03Permission.getType() == 1);
            routerDTO.setComponent(a03Permission.getComponent());
            routerDTO.setIconCls(a03Permission.getIcon());
            //排序规则
            routerDTO.setOrderNum(a03Permission.getOrderNum());
            routerDTO.setPath(a03Permission.getUrl());
            routerDTO.setName(a03Permission.getName());
            routerDTO.setMenuShow(true);
            routerDTO.setType(a03Permission.getType());
            routerDTOList.add(routerDTO);
        }
        //将权限 封装成树形结构
        return RouterDTO.buildList(routerDTOList,0l);
    }
    /**
    　* @Description: 插入菜单
    　* @author shalongteng
    　* @date 2020/4/1 14:02
    　*/
    @Override
    public boolean insertAuthPermission(A03Permission a03Permission) {
        // 查询是否存在, 可以重复
//        A03Permission byName = a03PermissionMapper.findPermissionByName(a03Permission.getName());
//        if (byName != null) {
//            throw new JsonException(ResultEnum.DATA_REPEAT, "当前权限规则已存在");
//        }

        return a03PermissionMapper.insertSelective(a03Permission) == 1? true :false;
    }
    /**
    　* @Description: 根据id 删除菜单
    　* @author shalongteng
    　* @date 2020/4/1 14:24
    　*/
    @Override
    public boolean deleteById(Long menuId) {
        if(menuId == 0){
            return false;
        }
        EntityWrapper<A03Permission> ew = new EntityWrapper<>();
        ew.eq("menu_id",menuId);
        a03PermissionMapper.delete(ew);
        //菜单要级联删除
        deleteByPid(menuId);

        return true;
    }

    //级联删除 菜单
    private void deleteByPid(Long menuId) {
        EntityWrapper<A03Permission> ew = new EntityWrapper<>();
        ew.eq("parent_id",menuId);
        List<A03Permission> a03PermissionList = a03PermissionMapper.selectList(ew);
        if(null == a03PermissionList || a03PermissionList.size() == 0){
            return;
        }else {
            for (A03Permission a03Permission : a03PermissionList) {
                EntityWrapper<A03Permission> ew2 = new EntityWrapper<>();
                ew2.eq("menu_id",a03Permission.getMenuId());
                a03PermissionMapper.delete(ew2);
                deleteByPid(a03Permission.getMenuId());//递归删除 子菜单
            }
        }

    }

    /**
    　* @Description: 编辑菜单
    　* @author shalongteng
    　* @date 2020/4/1 14:48
    　*/
    @Override
    public boolean updateAuthPermission(A03Permission a03Permission) {
        EntityWrapper<A03Permission> ew = new EntityWrapper<>();
        ew.eq("menu_id",a03Permission.getMenuId());
        return a03PermissionMapper.update(a03Permission,ew) == 1? true : false;
    }


    @Override
    public Tree<A03Permission> getTree() {
        List<Tree<A03Permission>> trees = new ArrayList<Tree<A03Permission>>();
        List<A03Permission> menuDOs = a03PermissionMapper.selectByMap(new HashMap<>());
        for (A03Permission menuDO : menuDOs) {
            Tree<A03Permission> tree = new Tree<>();
            tree.setId(menuDO.getMenuId().toString());
            tree.setParentId(menuDO.getParentId().toString());
            tree.setText(menuDO.getName());
            tree.setObject(menuDO);
//			Map<String,Object> map =new HashMap<>(16);
//			map.put("url",menuDO.getUrl());
//			map.put("perms",menuDO.getPerms());
//
//			tree.setAttributes(map);
            trees.add(tree);
        }
        // 默认顶级菜单为0，根据数据库实际情况调整
        Tree<A03Permission> t = new BuildTree().build(trees);
        return t;
    }

    /**
    　* @Description: 根据权限 查找对应角色 是否存在
    　* @author shalongteng
    　* @date 2020/4/3 18:03
    　*/
    @Override
    public List<Long> findRoleByPermissionId(Long menuId) {
        EntityWrapper<A05RolePermission> ew = new EntityWrapper<>();
        ew.eq("permission_id",menuId);
        List<A05RolePermission> a05RolePermissions = a05RolePermissionMapper.selectList(ew);
        return a05RolePermissions.stream().map(p->p.getId()).collect(Collectors.toList());
    }
}
