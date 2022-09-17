package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface C09GoodsSkuDao extends BaseMapper<C09GoodsSkuDO> {
     List<C09GoodsSkuDO> listGoodsSkuHDPage(Map<String, Object> map);

     C09GoodsSkuDO get(Long id);


     List<C09GoodsSkuDO> listHDPage(Map<String, Object> map);

     List<C09GoodsSkuDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C09GoodsSkuDO> c09GoodsSkus);



     int updateList(List<C09GoodsSkuDO> c09GoodsSkus);

     int updateStatus(Map<String, Object> map);
     int deductInventory(Map<String, Object> map);
     int addInventory(Map<String, Object> map);
}
