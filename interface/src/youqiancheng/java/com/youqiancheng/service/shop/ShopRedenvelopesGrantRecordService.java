package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 14:46
 * @Version: V1.0
 */
public interface ShopRedenvelopesGrantRecordService {
    E04RedenvelopesGrantRecordDO getRedenvelopesGrantRecordById(Long id);
    List<E04RedenvelopesGrantRecordDO> listRedenvelopesGrantRecordBy(EntityWrapper<E04RedenvelopesGrantRecordDO> ew);
}
