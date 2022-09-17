package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.C11RoomDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 17:18
 * @Version: V1.0
 */
public interface ShopRoomService {
    List<C11RoomDO> listRoomHDPage(Map<String, Object> map);
    C11RoomDO getRoomById(long id);
    Integer saveOrUpdateRoom(C11RoomDO room);
    Integer batchUpdateRoom(List<C11RoomDO> rooms);
}
