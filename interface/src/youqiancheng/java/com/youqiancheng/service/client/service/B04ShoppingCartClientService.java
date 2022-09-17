package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.vo.client.ShoppingCartVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
public interface B04ShoppingCartClientService {

     B04ShoppingCartDO get(Long id);

     List<ShoppingCartVO>  listHDPage(Map<String, Object> map);

     List<B04ShoppingCartDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B04ShoppingCartDO b04ShoppingCart);

     int insertBatch(List<B04ShoppingCartDO> b04ShoppingCarts);

     int update(B04ShoppingCartDO b04ShoppingCart);

     int delete(List<Long> ids);

     int start(List<Long> ids);


}
