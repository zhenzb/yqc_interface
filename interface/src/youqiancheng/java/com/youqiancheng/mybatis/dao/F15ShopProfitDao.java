package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F15ShopProfitDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-05-28
 */
@Mapper
public interface F15ShopProfitDao  extends BaseMapper<F15ShopProfitDO> {

     F15ShopProfitDO get(Long id);


     List<F15ShopProfitDO> listHDPage(Map<String, Object> map);

     List<F15ShopProfitDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F15ShopProfitDO> f15ShopProfits);



     int updateList(List<F15ShopProfitDO> f15ShopProfits);

     int updateStatus(Map<String, Object> map);
     //获取商家的总收益
     BigDecimal getTotalIncome(Long shopId);
}
