package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B09BankCardDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B09BankCardDao  extends BaseMapper<B09BankCardDO>{

     B09BankCardDO get(Long id);

     List<B09BankCardDO> list(Map<String, Object> map);
     List<B09BankCardDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B09BankCardDO> b09BankCards);



     int updateList(List<B09BankCardDO> b09BankCards);

     int updateStatus(Map<String, Object> map);
}
