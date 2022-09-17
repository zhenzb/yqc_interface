package com.youqiancheng.mybatis.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A05RolePermission;

public interface A05RolePermissionMapper extends BaseMapper<A05RolePermission> {

    int insertSelective(A05RolePermission record);
}