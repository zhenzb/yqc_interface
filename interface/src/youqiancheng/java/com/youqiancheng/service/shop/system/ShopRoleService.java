package com.youqiancheng.service.shop.system;


import com.youqiancheng.mybatis.model.F11RoleDO;

import java.util.List;
import java.util.Map;

public interface ShopRoleService {

    List<F11RoleDO> listRolePage(Map<String, Object> map);

    F11RoleDO findByName(String name);

    boolean insertUserRole(F11RoleDO f11RoleDO);

    boolean deleteRoleById(Long id);

    List<Long> findUserByRoleId(Long id);

    F11RoleDO findRoleByName(String name);
    F11RoleDO get(Long id);

    boolean updateUserRole(F11RoleDO f11RoleDO);

    List<F11RoleDO> listShopUserRole();

    List<F11RoleDO> getRoleListByUserId(Long userId);
}
