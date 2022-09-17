package com.youqiancheng.service.shop.system;


import java.util.List;

public interface ShopRoleUserService {

    int insertRolesUserIdAll(List<Long> roles, Long id);

    void deleteByUserId(Long userId);
}
