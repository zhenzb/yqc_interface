package com.youqiancheng.service.manager.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.dao.system.A02RoleMapper;
import com.youqiancheng.mybatis.dao.system.A04RoleAdminMapper;
import com.youqiancheng.mybatis.dao.system.A05RolePermissionMapper;
import com.youqiancheng.mybatis.model.A02Role;
import com.youqiancheng.mybatis.model.A05RolePermission;
import com.youqiancheng.service.manager.service.system.A02RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class A02RoleServiceImpl implements A02RoleService {
    @Resource
    private A02RoleMapper a02RoleMapper;
    @Resource
    private A05RolePermissionMapper a05RolePermissionMapper;
    @Resource
    private A04RoleAdminMapper a04RoleAdminMapper;

    /**
    　* @Description: 分页查询 角色
    　* @author shalongteng
    　* @date 2020/3/31 15:41
    　*/
    @Override
    public List<A02Role> listRolePage(Map<String, Object> map) {
        List<A02Role> list = a02RoleMapper.listAdminHDPage(map);
        return list;
    }
    /**
    　* @Description: 根据角色名称 查询角色
    　* @author shalongteng
    　* @date 2020/3/31 16:08
    　*/
    @Override
    public A02Role findByName(String name) {
        A02Role a02Role = new A02Role();
        a02Role.setName(name);

        return a02RoleMapper.selectOne(a02Role);
    }
    /**
    　* @Description: 插入一条角色，和对应的权限
    　* @author shalongteng
    　* @date 2020/3/31 16:26
    　*/
    @Override
    public boolean insertAuthRole(A02Role a02Role) {
        a02Role.setStatus(1);
        int res = a02RoleMapper.insert(a02Role);
        if(res != 1)
            return false;
        //级联 插入角色 的权限
        for (Long menuId : a02Role.getMenuIds()) {
            A05RolePermission a05RolePermission = new A05RolePermission();
            a05RolePermission.setRoleId(a02Role.getId());
            a05RolePermission.setPermissionId(menuId.intValue());
            a05RolePermissionMapper.insert(a05RolePermission);
        }
        return true;
    }
    /**
    　* @Description: 根据id 删除角色，并级联删除 角色权限中间表
        因为已经没有用户拥有该角色，所以不需要删除 用户角色中间表（在删除用户时候删除）
    　* @author shalongteng
    　* @date 2020/4/1 9:51
    　*/
    @Override
    public boolean deleteRoleById(Integer id) {
        a02RoleMapper.deleteById(id);
        EntityWrapper<A05RolePermission> ew = new EntityWrapper<>();
        ew.eq("role_id",id);
        List<A05RolePermission> a05RolePermissionList = a05RolePermissionMapper.selectList(ew);
        if(a05RolePermissionList.size() > 0){
            for (A05RolePermission a05RolePermission : a05RolePermissionList) {
                a05RolePermissionMapper.deleteById(a05RolePermission.getId());
            }
        }
        return true;
    }
    /**
    　* @Description: 根据角色id，查询用户
    　* @author shalongteng
    　* @date 2020/4/1 9:52
    　*/
    @Override
    public List<Long> findAdminByRoleId(Integer id) {
        return a04RoleAdminMapper.findAdminByRoleId(id);
    }

    /**
    　* @Description: 根据角色名称 查询角色实体
    　* @author shalongteng
    　* @date 2020/4/1 10:51
    　*/
    @Override
    public A02Role findRoleByName(String name) {
        A02Role role = new A02Role();
        role.setName(name);

        return a02RoleMapper.selectOne(role);
    }

    @Override
    public A02Role get(Long id) {
        return a02RoleMapper.selectById(id);
    }

    /**
    　* @Description: 修改角色，以及角色对应的权限
    　* @author shalongteng
    　* @date 2020/4/1 10:55
    　*/
    @Override
    public boolean updateAuthRole(A02Role a02Role) {
        a02Role.setUpdateTime(LocalDateTime.now());
        int res = a02RoleMapper.updateById(a02Role);
//        if(res != 1)
//            return false;
        //先删除 角色权限中间表
        EntityWrapper<A05RolePermission> ew = new EntityWrapper<>();
        ew.eq("role_id",a02Role.getId());
        List<A05RolePermission> a05RolePermissionList = a05RolePermissionMapper.selectList(ew);
        for (A05RolePermission a05RolePermission : a05RolePermissionList) {
            a05RolePermissionMapper.deleteById(a05RolePermission.getId());
        }
        //级联 插入角色 的权限
        for (Long menuId : a02Role.getMenuIds()) {
            A05RolePermission a05RolePermission = new A05RolePermission();
            a05RolePermission.setRoleId(a02Role.getId());
            a05RolePermission.setPermissionId(menuId.intValue());
            a05RolePermissionMapper.insertSelective(a05RolePermission);
        }
        return true;
    }

    /**
    　* @Description: 获取所有的角色
    　* @author shalongteng
    　* @date 2020/4/1 15:29
    　*/
    @Override
    public List<A02Role> listAuthAdminRole() {
        return a02RoleMapper.selectList(null);
    }

    /**
    　* @Description: 获取当前用户所有的角色
    　* @author shalongteng
    　* @date 2020/4/2 14:38
    　*/
    @Override
    public List<A02Role> getRoleListByAdminId(Long adminId) {

        return a02RoleMapper.getRoleListByAdminId(adminId);
    }
}
