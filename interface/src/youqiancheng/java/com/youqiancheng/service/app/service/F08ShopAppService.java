package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface F08ShopAppService {

     F08ShopUserDO getf08ShopUserDO(Map<String, Object> map);

}
