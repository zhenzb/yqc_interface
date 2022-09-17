package com.youqiancheng.mybatis.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B12PromotionAccountDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-05-09
 */
@Mapper
public interface B12PromotionAccountDao extends BaseMapper<B12PromotionAccountDO> {

     B12PromotionAccountDO get(Long id);


     List<B12PromotionAccountDO> listHDPage(Map<String, Object> map);

     List<B12PromotionAccountDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B12PromotionAccountDO> b12PromotionAccounts);



     int updateList(List<B12PromotionAccountDO> b12PromotionAccounts);

     int updateStatus(Map<String, Object> map);
}
