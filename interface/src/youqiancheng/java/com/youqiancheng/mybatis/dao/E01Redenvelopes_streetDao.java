package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E01Redenvelopes_streetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface E01Redenvelopes_streetDao  extends BaseMapper<E01Redenvelopes_streetDO>{

     E01Redenvelopes_streetDO get(Long id);

     List<E01Redenvelopes_streetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E01Redenvelopes_streetDO> e01Redenvelopes_streets);



     int updateList(List<E01Redenvelopes_streetDO> e01Redenvelopes_streets);

     int updateStatus(Map<String, Object> map);
}
