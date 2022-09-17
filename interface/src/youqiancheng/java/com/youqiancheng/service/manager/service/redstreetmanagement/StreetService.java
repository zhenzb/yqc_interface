package com.youqiancheng.service.manager.service.redstreetmanagement;


import com.youqiancheng.form.manager.redstreet.RuleSaveEditForm;
import com.youqiancheng.form.manager.redstreet.StreetSaveForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.E03RedenvelopesRuleDO;

import java.util.List;
import java.util.Map;

public interface StreetService {


    Map<String,List<E01RedenvelopesStreetDO>> getTree();

    void addStreet(StreetSaveForm streetSaveForm);

    Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList);

    void addOrEditAudit(RuleSaveEditForm ruleSaveEditForm);

    E03RedenvelopesRuleDO getAudit();

    List<C03CategoryDO> getCountryList();

    List<E01RedenvelopesStreetDO> getStreetList(Long id);
    List<E01RedenvelopesStreetDO> getStreetList1(Map<String,Object> map);
}
