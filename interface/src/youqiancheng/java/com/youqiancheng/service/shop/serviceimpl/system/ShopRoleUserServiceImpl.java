package com.youqiancheng.service.shop.serviceimpl.system;

import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.model.F13RoleShopUserDO;
import com.youqiancheng.service.shop.system.ShopRoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ShopRoleUserServiceImpl implements ShopRoleUserService {
    @Resource
    private F13RoleShopUserDao f13RoleShopUserDao;

    /**
    　* @Description:根据 角色idList 和 管理员 userId 批量插入
    　*/
    @Override
    public int insertRolesUserIdAll(List<Long> roleList, Long userId) {
        if (CollectionUtils.isEmpty(roleList) || userId == null){
            return 0;
        }
        List<F13RoleShopUserDO> addList = new ArrayList<>();
        for (Long roleId : roleList) {
            F13RoleShopUserDO f13RoleShopUserDO = new F13RoleShopUserDO();
            f13RoleShopUserDO.setRoleId(roleId);
            f13RoleShopUserDO.setUserId(userId);
            addList.add(f13RoleShopUserDO);
            f13RoleShopUserDO = null;
        }
        return f13RoleShopUserDao.insertBatch(addList);
    }

    @Override
    public void deleteByUserId(Long userId) {
        f13RoleShopUserDao.deleteByUserId(userId);
    }

}
