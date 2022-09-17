package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.B05CollectionDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.mybatis.model.F01ShopDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface B05CollectionClientService {

     B05CollectionDO get(Long id);

     List<B05CollectionDO> listHDPage(Map<String, Object> map);

     List<F01ShopDO> getCollectionShop(Map<String, Object> map);
     List<C10PublicityDO> getCollectionPublicity(Map<String, Object> map);

     List<C01GoodsDO> getCollectionGoods(Map<String, Object> map);

     List<B05CollectionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B05CollectionDO b05Collection);

     int insertBatch(List<B05CollectionDO> b05Collections);

     int update(B05CollectionDO b05Collection);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     int delete(B05CollectionDO b05Collection);

}
