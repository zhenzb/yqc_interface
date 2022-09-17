package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.util.QueryMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B03UserAccountFlowDao  extends BaseMapper<B03UserAccountFlowDO>{

     B03UserAccountFlowDO get(Long id);

     List<B03UserAccountFlowDO> list(Map<String, Object> map);
     List<B03UserAccountFlowDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B03UserAccountFlowDO> b03UserAccountFlows);



     int updateList(List<B03UserAccountFlowDO> b03UserAccountFlows);

     int updateStatus(Map<String, Object> map);

    List<B03UserAccountFlowDO> listB03UserAccountFlowHDPage(Map<String, Object> map);

     List<F01ShopDO> listShopHDPage(Map<String, Object> map);
    List<B03UserAccountFlowDO> getUserAccountFlowHDPage( QueryMap map);
}
