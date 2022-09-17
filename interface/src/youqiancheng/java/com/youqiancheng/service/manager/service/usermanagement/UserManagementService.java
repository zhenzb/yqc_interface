package com.youqiancheng.service.manager.service.usermanagement;


import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.user.UserFreezeForm;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.vo.manager.*;

import java.util.List;
import java.util.Map;

public interface UserManagementService {

    List<B01UserDO> listUserHDPage(Map<String, Object> map);

    boolean freeze(UserFreezeForm userFreezeForm);

    List<B02UserAccountDO> checkBalance(Map<String, Object> map);

    List<B03UserAccountFlowDO> checkBalanceDetail(Map<String, Object> map);

    List<B07AuthenticationDO> listUserAuthHDPage(Map<String, Object> map);

    Integer batchPassRefuse(List<ShopPassRefuseForm> shopPassRefuseFormList);
}
