package com.youqiancheng.mybatis.dao.shop.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface F08ShopUserDao  extends BaseMapper<F08ShopUserDO>{

     F08ShopUserDO get(Long id);


     List<F08ShopUserDO> listHDPage(Map<String, Object> map);

     List<F08ShopUserDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     List<F08ShopUserDO> listUserHDPage(Map<String, Object> map);

     int insertBatch(List<F08ShopUserDO> f08ShopUsers);



     int updateList(List<F08ShopUserDO> f08ShopUsers);

     int updateStatus(Map<String, Object> map);

    void  deleteUserShop(Map<String,Object> map);
}
