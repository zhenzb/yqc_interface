package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
@Repository
public interface F05ShopAccountDao  extends BaseMapper<F05ShopAccountDO>{

     F05ShopAccountDO get(Long id);
     F05ShopAccountDO getShopAccountbyShopId(Long id);

     List<F05ShopAccountDO> listHDPage(Map<String, Object> map);
     List<F05ShopAccountDO> list(Map<String, Object> map);
     int count(Map<String, Object> map);
     int insertBatch(List<F05ShopAccountDO> f05ShopAccounts);
     int updateList(List<F05ShopAccountDO> f05ShopAccounts);

     int updateStatus(Map<String, Object> map);

     //根据商家的id查商家账号
     List<F05ShopAccountDO> getShopAccount(Long id);
     //App端查询商家的总余额
     BigDecimal getShopTotalBalanceByShopId(Long shopId);
     F05ShopAccountDO getShopAccountById(Long shopId);
}
