package com.youqiancheng.mybatis.dao.shop.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F14RolePermissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface F14RolePermissionDao  extends BaseMapper<F14RolePermissionDO> {
     Integer insertBatchRolePermission(List<F14RolePermissionDO> list);

     F14RolePermissionDO get(Long id);


     List<F14RolePermissionDO> listHDPage(Map<String, Object> map);

     List<F14RolePermissionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F14RolePermissionDO> f14RolePermissions);



     int updateList(List<F14RolePermissionDO> f14RolePermissions);

     int updateStatus(Map<String, Object> map);
}
