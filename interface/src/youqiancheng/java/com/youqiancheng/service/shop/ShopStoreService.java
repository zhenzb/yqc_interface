package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.F01ShopDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:51
 * @Version: V1.0
 */
public interface ShopStoreService {


     F01ShopDO get(Long id);


     List<F01ShopDO> listHDPage(Map<String, Object> map);




     List<F01ShopDO> list(Map<String, Object> map);




     int count(Map<String, Object> map);





     int update(F01ShopDO f01Shop);

}
