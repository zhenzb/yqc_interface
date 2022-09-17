package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.D05EvaluatePicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface D05EvaluatePicDao extends BaseMapper<D05EvaluatePicDO> {

     D05EvaluatePicDO get(Long id);


     List<D05EvaluatePicDO> listHDPage(Map<String, Object> map);

     List<D05EvaluatePicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<D05EvaluatePicDO> d05EvaluatePics);



     int updateList(List<D05EvaluatePicDO> d05EvaluatePics);

     int updateStatus(Map<String, Object> map);
}
