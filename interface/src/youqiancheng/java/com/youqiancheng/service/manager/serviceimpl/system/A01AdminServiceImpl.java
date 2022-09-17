package com.youqiancheng.service.manager.serviceimpl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.dto.RouterDTO;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.form.manager.AuthAdmin.A01AdminSaveForm;
import com.youqiancheng.form.manager.AuthAdmin.A01ChangeForm;
import com.youqiancheng.mybatis.dao.system.A01AdminMapper;
import com.youqiancheng.mybatis.dao.system.A03PermissionMapper;
import com.youqiancheng.mybatis.dao.system.A04RoleAdminMapper;
import com.youqiancheng.mybatis.dao.system.A05RolePermissionMapper;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.mybatis.model.A03Permission;
import com.youqiancheng.mybatis.model.A04RoleAdmin;
import com.youqiancheng.mybatis.model.A05RolePermission;
import com.youqiancheng.service.manager.service.system.A01AdminService;
import com.youqiancheng.service.manager.service.system.A04RoleAdminService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class A01AdminServiceImpl implements A01AdminService {

    @Resource
    private A01AdminMapper a01AdminMapper;

    @Resource
    private A03PermissionMapper a03PermissionMapper;

    @Resource
    private A04RoleAdminMapper a04RoleAdminMapper;

    @Resource
    private A04RoleAdminService a04RoleAdminService;

    @Resource
    private A05RolePermissionMapper a05RolePermissionMapper;
    /**
    　* @Description: 根据名称 获取管理员信息
    　* @author shalongteng
    　* @date 2020/3/28 16:20
    　*/
    @Override
    public A01Admin findByUserName(String username) {

        return a01AdminMapper.findByUserName(username);
    }

    /**
    　* @Description: 更新管理员信息
    　* @author shalongteng
    　* @date 2020/3/28 16:20
    　*/
    @Override
    public void updateAuthAdmin(A01Admin au01AdminUp) {
        a01AdminMapper.updateAuthAdmin(au01AdminUp);
    }

    /**
    　* @Description: 根据用户id，获取用户权限集合
    　* @author shalongteng
    　* @date 2020/3/28 18:01
    　*/
    @Override
    public List<RouterDTO> RouterByAdminId(Long id) {
        //根据用户id 获取用户所有的权限
        List<A03Permission> a03PermissionList = a03PermissionMapper.findByAdminId(id);
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

            routerDTOList.add(routerDTO);
        }
        //将权限 封装成树形结构
        return RouterDTO.buildList(routerDTOList,0l);


    }
    /**
    　* @Description: 插入一条管理员记录
    　* @author shalongteng
    　* @date 2020/3/30 11:23
    　*/
    @Override
    public int insertAuthAdmin(A01Admin a01Admin, A01AdminSaveForm authAdminSaveForm) {
        a01Admin.setStatus(StatusConstant.enable.getCode());
        a01Admin.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a01AdminMapper.insertSelective(a01Admin);
        // 插入角色
        if (authAdminSaveForm.getRoleList() != null) {
            a04RoleAdminService.insertRolesAdminIdAll(authAdminSaveForm.getRoleList(), a01Admin.getId());
        }
        return a01Admin.getId().intValue();
    }
    /**
    　* @Description:分页查询管理员列表
    　* @author shalongteng
    　* @date 2020/3/30 13:56
    　*/
    @Override
    public List<A01Admin> listAdminHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return a01AdminMapper.listAdminHDPage(map);
    }
    /**
    　* @Description: 批量修改用户状态
    　* @author shalongteng
    　* @date 2020/3/30 15:10
     * @param
     * */
    @Override
    public List<String> changeStatus(List<A01ChangeForm> changeFormList) {
//        A01Admin admin = (A01Admin) SpringContextUtil.getHttpServletRequest().getSession().getAttribute("admin");
//        if(admin == null)
//            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        for (A01ChangeForm a01ChangeForm : changeFormList) {

            A01Admin a01Admin = new A01Admin();
            a01Admin.setId(a01ChangeForm.getId());
            a01Admin  = a01AdminMapper.selectById(a01Admin.getId());
            //参数错误
            if(a01Admin == null)
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL);
            //admin是超级管理员，不允许修改
            if(a01Admin.getUserName().equals("admin"))
                throw new JsonException(ResultEnum.NOT_ALLOW_CHANGE);
            a01Admin.setStatus(a01ChangeForm.getStatus());
            a01Admin.setUpdateTime(LocalDateTime.now());
            a01AdminMapper.updateById(a01Admin);
        }
        return null;
    }

    /**
    　* @Description: 修改用户，修改用户角色
    　* @author shalongteng
    　* @date 2020/4/1 15:46
    　*/
    @Override
    public boolean updateAdmin(A01Admin a01Admin) {
        a01Admin.setUpdateTime(LocalDateTime.now());
        a01AdminMapper.updateById(a01Admin);
        //修改用户角色 先删除角色 然后插入
        a04RoleAdminMapper.deleteByAdminId(a01Admin.getId());
        for (Long id : a01Admin.getRoleList()) {
            A04RoleAdmin a04RoleAdmin = new A04RoleAdmin();
            a04RoleAdmin.setAdminId(a01Admin.getId() );
            a04RoleAdmin.setRoleId(id);
            a04RoleAdminMapper.insertSelective(a04RoleAdmin);
        }
        return true;
    }

    /**
    　* @Description: 删除用户 并级联删除 角色用户中间表
    　* @author shalongteng
    　* @date 2020/4/1 16:30
    　*/
    @Override
    public boolean deleteAdminById(Long id) {
        A01Admin a01Admin = a01AdminMapper.selectById(id);
        if(a01Admin == null){
            return false;
        }
        if(a01Admin.getUserName().equals("admin")){
            throw new  JsonException(ResultEnum.NOT_ALLOW_CHANGE);
        }

        a01AdminMapper.deleteById(a01Admin.getId());
        //级联删除 角色用户中间表
        a04RoleAdminMapper.deleteByAdminId(a01Admin.getId());
        return true;
    }

    /**
    　* @Description: 根据角色id，获取他所有菜单id
    　* @author shalongteng
    　* @date 2020/4/2 11:30
    　*/
    @Override
    public List<Long> getIdListByRolId(Long roleId) {
        EntityWrapper<A05RolePermission> ew = new EntityWrapper<>();
        ew.eq("role_id",roleId);
        List<A05RolePermission> a05RolePermissionList = a05RolePermissionMapper.selectList(ew);
        List<Long> menuIds = a05RolePermissionList.stream().map(A05RolePermission::getPermissionId).collect(Collectors.toList());
        // 根据roleId查询权限,只保留子节点，父节点的选中或半选中状态，前台自动调整
        List<A03Permission> menus = a03PermissionMapper.selectList(new EntityWrapper<>());
        List<Long> temp= menuIds;
        for (A03Permission menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        return menuIds;
    }

    @Override
    public boolean verifyPhoneNumbers(String phoneNumbers) {
        EntityWrapper<A01Admin> ew = new EntityWrapper<>();
        ew.eq("tel",phoneNumbers);
        List<A01Admin> a01AdminList = a01AdminMapper.selectList(ew);
        if(a01AdminList != null && a01AdminList.size() > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean setNewPassword(String phoneNumbers, String password) {
        EntityWrapper<A01Admin> ew = new EntityWrapper<>();
        ew.eq("tel",phoneNumbers);
        List<A01Admin> a01AdminList = a01AdminMapper.selectList(ew);
        if(a01AdminList != null && a01AdminList.size() > 0){
            A01Admin a01Admin = a01AdminList.get(0);
            a01Admin.setPassword(PasswordUtils.authAdminPwd(password));
            a01Admin.setUpdateTime(LocalDateTime.now());
            return a01AdminMapper.updateById(a01Admin) == 1? true : false;
        }
        return false;
    }


}
