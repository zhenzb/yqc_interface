package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.B04ShoppingCartAddAndSubSaveForm;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.vo.app.ShoppingCartVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
public interface B04ShoppingCartAppService {

     B04ShoppingCartDO get(Long id);

     List<ShoppingCartVO>  listHDPage(Map<String, Object> map);

     List<B04ShoppingCartDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B04ShoppingCartDO b04ShoppingCart);

     int insertBatch(List<B04ShoppingCartDO> b04ShoppingCarts);

     int update(B04ShoppingCartDO b04ShoppingCart);

     int delete(List<Long> ids);

     int start(List<Long> ids);

     //添加购物车商品
     int addCartGoods(B04ShoppingCartAddAndSubSaveForm form);


}
