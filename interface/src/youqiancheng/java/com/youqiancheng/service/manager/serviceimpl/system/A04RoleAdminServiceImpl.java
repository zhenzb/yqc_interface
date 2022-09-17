package com.youqiancheng.service.manager.serviceimpl.system;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.system.A04RoleAdminMapper;
import com.youqiancheng.mybatis.model.A04RoleAdmin;
import com.youqiancheng.service.manager.service.system.A04RoleAdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class A04RoleAdminServiceImpl implements A04RoleAdminService {
    @Resource
    private A04RoleAdminMapper a04RoleAdminMapper;

    /**
    　* @Description:根据 角色idList 和 管理员 adminId 批量插入
    　* @author shalongteng
    　* @date 2020/3/30 11:30
    　*/
    @Override
    public int insertRolesAdminIdAll(List<Long> roleList, Long adminId) {
        for (Long roleId : roleList) {
            A04RoleAdmin a04RoleAdmin = new A04RoleAdmin();
            a04RoleAdmin.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            a04RoleAdmin.setRoleId(roleId);
            a04RoleAdmin.setAdminId(adminId);
            a04RoleAdminMapper.insertSelective(a04RoleAdmin);
        }
        return 0;
    }

}
