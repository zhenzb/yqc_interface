package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.form.shop.otther.ShopGoodsStatisticsForm;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C03CategoryDO;

import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/13 13:29
 * @Version: V1.0
 */
public interface ShopGoodsService {
    //商家的商品总数
    Integer goodsCount();
    Integer goodsStatistics(ShopGoodsStatisticsForm shopGoodsStatisticsForm);
    List<C01GoodsDO> listGoodsHDPage(Map<String, Object> map);
    C01GoodsDO getGoodsById(long id);
    Integer saveOrUpdateGoods(C01GoodsDO goods);
    Integer batchUpdateGoods(List<C01GoodsDO> goods);
    List<C03CategoryDO> getCategoryById(Long id);
    C03CategoryDO getById(Long id);
    List<C03CategoryDO> getCategoryList();
    List<C03CategoryDO> listCategoryListBy(EntityWrapper<C03CategoryDO> ew);
}
