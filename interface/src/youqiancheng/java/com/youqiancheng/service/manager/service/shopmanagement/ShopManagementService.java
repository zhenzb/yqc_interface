package com.youqiancheng.service.manager.service.shopmanagement;


import com.youqiancheng.form.manager.shop.*;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;

import java.util.List;
import java.util.Map;

public interface ShopManagementService {


    List<F01ShopDO> listShopHDPage(Map<String, Object> map);

    List<F03MainProjectDO> listMainProjectHDPage(Map<String, Object> map);

    void addMainProject(MainProjectSaveForm mainProjectSaveForm);

    void editMainProject(MainProjectEditForm mainProjectEditForm);

    Integer deleteShop(List<ShopDeleteForm> shopDeleteForm);

    Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList);

    Integer deleteMainProject(List<MainProjectDeleteForm> mainProjectDeleteForm);

    int stop(List<Long> idList);

    int start(List<Long> idList);

    void addOrEditAudit(ExamineSaveEditForm examineSaveEditForm);

    F04ShopExamineSetDO getAudit();

    List<F08ShopUserDO> listShopAccountHDPage(Map<String, Object> map);

    void saveOrupdateShopUser(ShopUsertSaveOrUpdateForm shopUsertSaveOrUpdateForm);

    Integer batchPassRefuseShopUser(List<ShopPassRefuseForm> shopPassRefuseFormList);

    Integer saveFlow();
    F01ShopDO getInfo(Long id);
    int update(F01ShopUpdateManageDO dto);
}
