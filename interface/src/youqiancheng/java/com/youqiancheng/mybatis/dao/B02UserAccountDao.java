package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B02UserAccountDao  extends BaseMapper<B02UserAccountDO>{

     B02UserAccountDO get(Long id);

     List<B02UserAccountDO> list(Map<String, Object> map);
     List<B02UserAccountDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B02UserAccountDO> b02UserAccounts);



     int updateList(List<B02UserAccountDO> b02UserAccounts);

     int updateStatus(Map<String, Object> map);

    List<B02UserAccountDO> listB02UserAccountHDPage(Map<String, Object> map);

     List<B02UserAccountDO> getAccountBalanceByUserId(Long id);
}
