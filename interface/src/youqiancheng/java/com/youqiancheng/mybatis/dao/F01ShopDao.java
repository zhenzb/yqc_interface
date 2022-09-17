package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F01ShopDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface F01ShopDao  extends BaseMapper<F01ShopDO> {

     F01ShopDO get(Long id);


     List<F01ShopDO> listHDPage(Map<String, Object> map);

     List<F01ShopDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F01ShopDO> f01Shops);



     int updateList(List<F01ShopDO> f01Shops);

     int updateStatus(Map<String, Object> map);

    Long selectForDay();

     Long selectForMonth();

     Long selectForAll();

    List<F01ShopDO> listShopHDPage(Map<String, Object> map);
    List<F01ShopDO> getvicinity(Map<String, Object> map);
    List<F01ShopDO> getvicinityByDistanceHDPage(Map<String, Object> map);

    List<F01ShopDO> getShopInfoById(Long id);

    F01ShopDO getQRcodeimage(@Param(value = "shopId")Long shopId, @Param(value = "deleteFlag")int deleteFlag);
}
