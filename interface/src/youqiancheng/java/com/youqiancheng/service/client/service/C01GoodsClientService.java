package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.C09GoodsSkuSearchForm;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.vo.client.C01GoodsClientVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface C01GoodsClientService {

     C01GoodsClientVO get(Long id);

     List<C01GoodsDO> listHDPageWithOrder(Map<String, Object> map);

     List<C01GoodsDO> list(Map<String, Object> map);
     List<C01GoodsDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C01GoodsDO c01Goods);

     int insertBatch(List<C01GoodsDO> c01Goodss);

     int update(C01GoodsDO c01Goods);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     List<C08SelectAttributeDO> getSelectAttribute(Long id);

      C09GoodsSkuDO  getSku(C09GoodsSkuSearchForm form);
     //根据商家id获取商品信息
     List<C01GoodsDO> getGoodsById(Map<String,Object> map);
}
