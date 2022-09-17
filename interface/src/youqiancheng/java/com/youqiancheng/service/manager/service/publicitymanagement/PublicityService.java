package com.youqiancheng.service.manager.service.publicitymanagement;


import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.model.C10PublicityDO;

import java.util.List;
import java.util.Map;

public interface PublicityService {

    List<C10PublicityDO> listPublicityHDPage(Map<String, Object> map);

    Integer delete(Integer id);

    Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList);
    C10PublicityDO get(Long id);
    int update(C10PublicityDO dto);

}
