package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C11RoomDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Mapper
public interface C11RoomDao  extends BaseMapper<C11RoomDO> {
     List<C11RoomDO> listRoomHDPage(Map<String, Object> map);

     C11RoomDO get(Long id);


     List<C11RoomDO> listHDPage(Map<String, Object> map);

     List<C11RoomDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C11RoomDO> c11Rooms);



     int updateList(List<C11RoomDO> c11Rooms);

     int updateStatus(Map<String, Object> map);
}
