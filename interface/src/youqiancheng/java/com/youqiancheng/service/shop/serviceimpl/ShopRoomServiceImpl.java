package com.youqiancheng.service.shop.serviceimpl;

import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C11RoomDao;
import com.youqiancheng.mybatis.model.C11RoomDO;
import com.youqiancheng.service.shop.ShopRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 17:19
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopRoomServiceImpl implements ShopRoomService {
    @Resource
    private C11RoomDao roomDao;


    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/15 17:39
     * @Param:
     * @return:
     * @Description: 房间列表 分页
     */
    @Override
    public List<C11RoomDO> listRoomHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return roomDao.listRoomHDPage(map);
    }

    //查看详情
    @Override
    public C11RoomDO getRoomById(long id) {
        return roomDao.selectById(id);
    }

    //添加/编辑
    @Override
    public Integer saveOrUpdateRoom(C11RoomDO room) {
        if (room == null){
            return 0;
        }
        room.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (room.getId() == null){//添加
            room.setCreateTime(LocalDateTime.now());
            return roomDao.insert(room);
        }
        //编辑
        room.setUpdateTime(LocalDateTime.now());
        return roomDao.updateById(room);
    }

    @Override
    public Integer batchUpdateRoom(List<C11RoomDO> rooms) {
        if (CollectionUtils.isEmpty(rooms)){
            return 0;
        }
        return roomDao.updateList(rooms);
    }
}


