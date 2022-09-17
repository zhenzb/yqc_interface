package com.youqiancheng.service.shop.system;


import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.mybatis.model.F12PermissionDO;
import com.youqiancheng.util.Tree;

import java.util.List;

public interface ShopPermissionService {
    List<RouterDTO> RouterList();

    boolean insertShopPermission(F12PermissionDO f12PermissionDO);

    boolean deleteById(Long menuId);

    boolean updateShopPermission(F12PermissionDO f12PermissionDO);

    Tree<F12PermissionDO> getTree();

    List<Long> findShopRoleByPermissionId(Long menuId);
}
