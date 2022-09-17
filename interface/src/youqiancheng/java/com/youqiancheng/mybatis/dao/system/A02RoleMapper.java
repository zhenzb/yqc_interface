package com.youqiancheng.mybatis.dao.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.mybatis.model.A02Role;

import java.util.List;
import java.util.Map;

public interface A02RoleMapper extends BaseMapper<A02Role> {

    int insertSelective(A02Role record);

    List<A02Role> listAdminHDPage(Map<String, Object> authRoleQueryForm);

    List<A02Role> getRoleListByAdminId(Long adminId);
}