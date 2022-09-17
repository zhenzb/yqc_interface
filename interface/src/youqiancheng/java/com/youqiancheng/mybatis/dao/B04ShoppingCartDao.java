package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Mapper
public interface B04ShoppingCartDao extends BaseMapper<B04ShoppingCartDO> {

     B04ShoppingCartDO get(Long id);


     List<B04ShoppingCartDO> listHDPage(Map<String, Object> map);

     List<B04ShoppingCartDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B04ShoppingCartDO> b04ShoppingCarts);



     int updateList(List<B04ShoppingCartDO> b04ShoppingCarts);

     int updateStatus(Map<String, Object> map);
     int deleteShoppingCart(Map<String, Object> map);
}
