package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface F07ShopAccountFlowDao  extends BaseMapper<F07ShopAccountFlowDO>{

     F07ShopAccountFlowDO get(Long id);
     List<F07ShopAccountFlowDO> getWithdrawableMoney(Map<String, Object> map);


     List<F07ShopAccountFlowDO> listHDPage(Map<String, Object> map);

     List<F07ShopAccountFlowDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F07ShopAccountFlowDO> f07ShopAccountFlows);



     int updateList(List<F07ShopAccountFlowDO> f07ShopAccountFlows);

     int updateStatus(Map<String, Object> map);
}
