package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
public interface A11AdvertisementPicAppService {
     List<A11AdvertisementPicDO> getAdPicList(int type);


     List<A11AdvertisementPicDO> listHDPage(Map<String, Object> map);

}
