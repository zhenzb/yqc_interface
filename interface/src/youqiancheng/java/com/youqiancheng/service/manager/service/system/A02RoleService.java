package com.youqiancheng.service.manager.service.system;


import com.youqiancheng.mybatis.model.A02Role;

import java.util.List;
import java.util.Map;

public interface A02RoleService {

    List<A02Role> listRolePage(Map<String, Object> map);

    A02Role findByName(String name);

    boolean insertAuthRole(A02Role a02Role);

    boolean deleteRoleById(Integer id);

    List<Long> findAdminByRoleId(Integer id);

    A02Role findRoleByName(String name);
    A02Role get(Long id);

    boolean updateAuthRole(A02Role a02Role);

    List<A02Role> listAuthAdminRole();

    List<A02Role> getRoleListByAdminId(Long adminId);
}
