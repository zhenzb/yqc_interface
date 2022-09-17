package com.youqiancheng.mybatis.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A03Permission;

import java.util.List;

public interface A03PermissionMapper extends BaseMapper<A03Permission> {

    int insertSelective(A03Permission record);
    //根据用户id 获取用户所有的权限
    List<A03Permission> findByAdminId(Long id);

    A03Permission findPermissionByName(String name);
}