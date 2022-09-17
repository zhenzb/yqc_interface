package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/22 18:12
 * @Version: V1.0
 */
public interface ShopRedenvelopesStreetService {
    E01RedenvelopesStreetDO getRedenvelopesStreetById(Long id);
    List<E01RedenvelopesStreetDO> listRedenvelopesStreetBy(EntityWrapper<E01RedenvelopesStreetDO> ew);
}
