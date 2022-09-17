package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.util.QueryMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface C01GoodsDao  extends BaseMapper<C01GoodsDO> {
    List<C01GoodsDO> listGoodsHDPage(Map<String, Object> map);
    List<C01GoodsDO> listGoodsHDPage1(Map<String, Object> map);

     C01GoodsDO get(Long id);
    //根据商家id查询商品的信息
    List<C01GoodsDO> getGoodsByIdHDPage(Map<String, Object> map);

     List<C01GoodsDO> listHDPage(Map<String, Object> map);

     List<C01GoodsDO> listGoods(Map<String, Object> map);

     List<C01GoodsDO> listWithOrderHDPage(Map<String, Object> map);
     List<C01GoodsDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insertBatch(List<C01GoodsDO> c01Goodss);
     int updateList(List<C01GoodsDO> c01Goodss);

     int updateStatus(Map<String, Object> map);

     Long selectForDay();

     Long selectForMonth();

     Long selectForAll();

     List<C01GoodsDO> getGoodsListHDPage(QueryMap map);

     List<C01GoodsDO> getShopAppGoodsByShopId(Long shopId);
}
