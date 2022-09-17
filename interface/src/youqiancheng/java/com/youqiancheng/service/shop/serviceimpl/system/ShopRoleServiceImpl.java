package com.youqiancheng.service.shop.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.shop.system.F11RoleDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F14RolePermissionDao;
import com.youqiancheng.mybatis.model.F11RoleDO;
import com.youqiancheng.mybatis.model.F14RolePermissionDO;
import com.youqiancheng.service.shop.system.ShopRoleService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShopRoleServiceImpl implements ShopRoleService {
    @Resource
    private F11RoleDao f11RoleDao;
    @Resource
    private F14RolePermissionDao f14RolePermissionDao;
    @Resource
    private F13RoleShopUserDao f13RoleShopUserDao;

    /**
    　* @Description: 分页查询 角色
    　*/
    @Override
    public List<F11RoleDO> listRolePage(Map<String, Object> map) {
        return f11RoleDao.listUserHDPage(map);
    }
    /**
    　* @Description: 根据角色名称 查询角色
    　*/
    @Override
    public F11RoleDO findByName(String name) {
        F11RoleDO f11RoleDO = new F11RoleDO();
        f11RoleDO.setName(name);
        return f11RoleDao.selectOne(f11RoleDO);
    }
    /**
    　* @Description: 插入一条角色，和对应的权限
    　*/
    @Override
    public boolean insertUserRole(F11RoleDO f11RoleDO) {
        f11RoleDO.setStatus(true);
        f11RoleDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int res = f11RoleDao.insert(f11RoleDO);
        if(res != 1){
            throw new JsonException(ResultEnum.UPDATE_FAIL,"插入角色失败");
        }
        //级联 插入角色 的权限
        List<F14RolePermissionDO> addList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(f11RoleDO.getMenuIds())){
            for (Long menuId : f11RoleDO.getMenuIds()) {
                F14RolePermissionDO f14RolePermission = new F14RolePermissionDO();
                f14RolePermission.setRoleId(f11RoleDO.getId());
                f14RolePermission.setPermissionId(menuId.intValue());
                addList.add(f14RolePermission);
                f14RolePermissionDao.insert(f14RolePermission);
                f14RolePermission = null;
            }
        }
        if (f14RolePermissionDao.insertBatch(addList) <=0 ){
            throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"级联插入角色失败");
        }
        return true;
    }
    /**
    　* @Description: 根据id 删除角色，并级联删除 角色权限中间表
        因为已经没有用户拥有该角色，所以不需要删除 用户角色中间表（在删除用户时候删除）
    　*/
    @Override
    public boolean deleteRoleById(Long id) {
        if (id == null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"ID为空");
        }
        if (f11RoleDao.deleteById(id) != 1){
            throw new JsonException(ResultEnum.DELETE_FAIL,"删除角色失败");
        }
        EntityWrapper<F14RolePermissionDO> ew = new EntityWrapper<>();
        ew.eq("role_id",id);
        List<F14RolePermissionDO> f14RolePermissionList = f14RolePermissionDao.selectList(ew);
        if(!CollectionUtils.isEmpty(f14RolePermissionList)){
            for (F14RolePermissionDO f14RolePermission : f14RolePermissionList) {
                f14RolePermissionDao.deleteById(f14RolePermission.getId());
            }
        }
        return true;
    }
    /**
    　* @Description: 根据角色id，查询用户
    　*/
    @Override
    public List<Long> findUserByRoleId(Long id) {
        return f13RoleShopUserDao.findUserByRoleId(id);
    }

    /**
    　* @Description: 根据角色名称 查询角色实体
    　*/
    @Override
    public F11RoleDO findRoleByName(String name) {
        F11RoleDO f11Role = new F11RoleDO();
        f11Role.setName(name);
        return f11RoleDao.selectOne(f11Role);
    }

    @Override
    public F11RoleDO get(Long id) {
        return f11RoleDao.selectById(id);
    }

    /**
    　* @Description: 修改角色，以及角色对应的权限
    　*/
    @Override
    public boolean updateUserRole(F11RoleDO f11Role) {
        f11Role.setUpdateTime(LocalDateTime.now());
        int res = f11RoleDao.updateById(f11Role);
        if(res != 1){
            throw new JsonException(ResultEnum.UPDATE_FAIL,"权限修改失败");
        }
        //先删除 角色权限中间表
        EntityWrapper<F14RolePermissionDO> ew = new EntityWrapper<>();
        ew.eq("role_id",f11Role.getId());
        List<F14RolePermissionDO> f14RolePermissionList = f14RolePermissionDao.selectList(ew);
        if (!CollectionUtils.isEmpty(f14RolePermissionList)){
            for (F14RolePermissionDO f14RolePermission : f14RolePermissionList) {
                f14RolePermissionDao.deleteById(f14RolePermission.getId());
            }
        }
        List<F14RolePermissionDO> addList = new ArrayList();
        if (!CollectionUtils.isEmpty(f11Role.getMenuIds())){
            //级联 插入角色 的权限
            for (Long menuId : f11Role.getMenuIds()) {
                F14RolePermissionDO f14RolePermission = new F14RolePermissionDO();
                f14RolePermission.setRoleId(f11Role.getId());
                f14RolePermission.setPermissionId(menuId.intValue());
                f14RolePermission.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                addList.add(f14RolePermission);
            }
        }
        int i = f14RolePermissionDao.insertBatch(addList);
        if(i<=0){
            throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"权限批量插入失败");
        }
        return true;
    }

    /**
    　* @Description: 获取所有的角色
    　*/
    @Override
    public List<F11RoleDO> listShopUserRole() {
        return f11RoleDao.selectList(null);
    }

    /**
    　* @Description: 获取当前用户所有的角色
    　*/
    @Override
    public List<F11RoleDO> getRoleListByUserId(Long userId) {
        return f11RoleDao.getRoleListByUserId(userId);
    }
}
