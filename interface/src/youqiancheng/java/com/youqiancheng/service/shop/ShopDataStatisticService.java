package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.vo.result.ResultVo;

import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/1 14:55
 * @Version: V1.0
 */
public interface ShopDataStatisticService {
    List<D01OrderDO> getListBy(EntityWrapper<D01OrderDO> ew);

    ResultVo DataStatistics();
}
