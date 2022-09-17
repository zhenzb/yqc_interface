package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F16ShopProfitFlowsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-05-28
 */
@Mapper
public interface F16ShopProfitFlowsDao  extends BaseMapper<F16ShopProfitFlowsDO> {

     F16ShopProfitFlowsDO get(Long id);


     List<F16ShopProfitFlowsDO> listHDPage(Map<String, Object> map);

     List<F16ShopProfitFlowsDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F16ShopProfitFlowsDO> f16ShopProfitFlowss);



     int updateList(List<F16ShopProfitFlowsDO> f16ShopProfitFlowss);

     int updateStatus(Map<String, Object> map);

     List<F16ShopProfitFlowsDO> getProfitByDay(Map<String, Object> map);
     List<F16ShopProfitFlowsDO> getProfitBytoday(Map<String, Object> map);
     List<F16ShopProfitFlowsDO> getTotalProfitBytoday(Map<String, Object> map);

     int unionTest(Map<String, Object> map);
}
