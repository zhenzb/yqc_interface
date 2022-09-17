package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.C09GoodsSkuSearchForm;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.vo.app.C01GoodsAppVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface C01GoodsAppService {

     C01GoodsAppVO get(Long id);

     List<C01GoodsDO> listHDPageWithOrder(Map<String, Object> map);

     List<C01GoodsDO> list(Map<String, Object> map);
     List<C01GoodsDO> listHDPage(Map<String, Object> map);
     List<C01GoodsDO> listGoods(Map<String, Object> map);
     int count(Map<String, Object> map);

     int insert(C01GoodsDO c01Goods);

     int insertBatch(List<C01GoodsDO> c01Goodss);

     int update(C01GoodsDO c01Goods);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     List<C08SelectAttributeDO> getSelectAttribute(Long id);

      C09GoodsSkuDO  getSku(C09GoodsSkuSearchForm form);
      //查商家的商品缩略图和商品名称
     List<C01GoodsDO> getShopAppGoodsByShopId(Long id);
}
