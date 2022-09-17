package com.youqiancheng.mybatis.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A04RoleAdmin;

import java.util.List;

public interface A04RoleAdminMapper extends BaseMapper<A04RoleAdmin> {
    int insertSelective(A04RoleAdmin record);

    List<Long> findAdminByRoleId(Integer id);

    void deleteByAdminId(Long adminId);
}