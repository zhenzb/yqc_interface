package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface F01ShopClientService {

     F01ShopDO get(Long id);
     F01ShopDO getShop(Long id);

     List<F01ShopDO> listHDPage(Map<String, Object> map);

     List<F03MainProjectDO> getShopProjectByType(String type);

     List<F01ShopDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int applyEnters(F01ShopAppSaveForm f01Shop) ;



     int isShopApplyEnters(Long userId) ;

     int insertBatch(List<F01ShopDO> f01Shops);

     int update(F01ShopDO f01Shop);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     F05ShopAccountDO getProfit(Long id);
    //获取微信支付宝二维码图片
     F01ShopDO getQRcodeimage(Long id);


}
