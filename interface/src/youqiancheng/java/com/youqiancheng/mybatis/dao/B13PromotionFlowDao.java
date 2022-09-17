package com.youqiancheng.mybatis.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B13PromotionFlowDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-05-09
 */
@Mapper
public interface B13PromotionFlowDao extends BaseMapper<B13PromotionFlowDO> {

     B13PromotionFlowDO get(Long id);


     List<B13PromotionFlowDO> listHDPage(Map<String, Object> map);

     List<B13PromotionFlowDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B13PromotionFlowDO> b13PromotionFlows);



     int updateList(List<B13PromotionFlowDO> b13PromotionFlows);

     int updateStatus(Map<String, Object> map);
}
