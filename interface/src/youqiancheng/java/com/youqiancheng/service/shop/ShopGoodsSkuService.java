package com.youqiancheng.service.shop;

import com.youqiancheng.form.shop.C09GoodsSkuSaveForm;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/18 15:55
 * @Version: V1.0
 */
public interface ShopGoodsSkuService {
    List<C09GoodsSkuDO> listGoodsSkuHDPage(Map<String, Object> map);
    C09GoodsSkuDO getGoodsSkuById(Long id);
    Integer saveOrUpdateGoodsSku(C09GoodsSkuDO goodsSku);
    Integer batckUpdateGoodsSku(List<C09GoodsSkuDO> goodsSkus);
    Integer save(List<C09GoodsSkuSaveForm> list);
    Integer delete(List<Long> ids);
    Integer edit(C09GoodsSkuDO dto);
}
