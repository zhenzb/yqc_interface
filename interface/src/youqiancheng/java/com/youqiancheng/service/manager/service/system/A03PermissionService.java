package com.youqiancheng.service.manager.service.system;


import com.handongkeji.dto.RouterDTO;
import com.youqiancheng.mybatis.model.A03Permission;
import com.youqiancheng.util.Tree;

import java.util.List;

public interface A03PermissionService {
    List<RouterDTO> RouterList();

    boolean insertAuthPermission(A03Permission a03Permission);

    boolean deleteById(Long menuId);

    boolean updateAuthPermission(A03Permission a03Permission);

    Tree<A03Permission> getTree();

    List<Long> findRoleByPermissionId(Long menuId);
}
