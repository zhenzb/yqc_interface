package com.youqiancheng.mybatis.dao.shop.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F11RoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface F11RoleDao  extends BaseMapper<F11RoleDO> {
     List<F11RoleDO> getRoleListByUserId(Long userId);
     List<F11RoleDO> listUserHDPage(Map<String, Object> userRoleQueryForm);

     F11RoleDO get(Long id);


     List<F11RoleDO> listHDPage(Map<String, Object> map);

     List<F11RoleDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F11RoleDO> f11Roles);



     int updateList(List<F11RoleDO> f11Roles);

     int updateStatus(Map<String, Object> map);
}
