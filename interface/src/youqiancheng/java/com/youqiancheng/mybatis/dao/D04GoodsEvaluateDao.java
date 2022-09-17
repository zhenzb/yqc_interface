package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface D04GoodsEvaluateDao extends BaseMapper<D04GoodsEvaluateDO> {
     List<D04GoodsEvaluateDO> listGoodsEvaluateHDPage(Map<String, Object> map);

     D04GoodsEvaluateDO get(Long id);


     List<D04GoodsEvaluateDO> listHDPage(Map<String, Object> map);

     List<D04GoodsEvaluateDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<D04GoodsEvaluateDO> d04GoodsEvaluates);



     int updateList(List<D04GoodsEvaluateDO> d04GoodsEvaluates);

     int updateStatus(Map<String, Object> map);

    List<D04GoodsEvaluateDO> listCommentHDPage(Map<String, Object> map);
}
