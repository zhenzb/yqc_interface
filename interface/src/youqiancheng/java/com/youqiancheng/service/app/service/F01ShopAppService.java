package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface F01ShopAppService {

     F01ShopDO get(Long id);
     F01ShopDO getShop(Long id);
     List<F01ShopDO> listHDPage(Map<String, Object> map);
     int isShopApplyEnters(Long userId) ;
     List<F03MainProjectDO> getShopProjectByType(String type);

     List<F01ShopDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     F01ShopDO applyEnters(F01ShopAppSaveForm f01Shop) ;

     int insertBatch(List<F01ShopDO> f01Shops);

     int update(F01ShopDO f01Shop);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     List<F01ShopDO> getvicinity(Map<String, Object> map);
     List<F01ShopDO> getvicinityByDistanceHDPage(Map<String, Object> map);
     List<F01ShopDO> getShopInfoById(Long id);
     //获取二维码图片
     F01ShopDO getQRcodeimage(Long id);

     int updateApplyEnters(com.youqiancheng.form.client.F01ShopAppSaveForm f01Shop) ;

}
