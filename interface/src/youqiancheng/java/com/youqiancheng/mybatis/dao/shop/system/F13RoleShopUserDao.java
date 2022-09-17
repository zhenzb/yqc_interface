package com.youqiancheng.mybatis.dao.shop.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F13RoleShopUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface F13RoleShopUserDao  extends BaseMapper<F13RoleShopUserDO> {
     List<Long> findUserByRoleId(Long id);
     Integer insertBatchRoleShopUser(List<F13RoleShopUserDO> list);

     F13RoleShopUserDO get(Long id);


     List<F13RoleShopUserDO> listHDPage(Map<String, Object> map);

     List<F13RoleShopUserDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F13RoleShopUserDO> f13RoleShopUsers);



     int updateList(List<F13RoleShopUserDO> f13RoleShopUsers);

     int updateStatus(Map<String, Object> map);

     void deleteByUserId(Long userId);
}
