package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.vo.app.A15MessageAppVO;
import com.youqiancheng.vo.client.A15MessageClientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Mapper
public interface A15MessageDao extends BaseMapper<A15MessageDO> {

     A15MessageDO get(Long id);


     List<A15MessageDO> listHDPage(Map<String, Object> map);
     List<A15MessageAppVO> listByUserIdAppHDPage(Map<String, Object> map);
     Integer countByUserIdApp(Map<String, Object> map);
     List<A15MessageClientVO> listByUserIdClientHDPage(Map<String, Object> map);
     Integer countByUserIdClient(Map<String, Object> map);

     List<A15MessageDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A15MessageDO> a15Messages);



     int updateList(List<A15MessageDO> a15Messages);

     int updateStatus(Map<String, Object> map);
}
