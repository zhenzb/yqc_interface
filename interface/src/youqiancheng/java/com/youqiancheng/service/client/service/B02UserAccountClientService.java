package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.my.PayBalanceForm;
import com.youqiancheng.mybatis.model.B02UserAccountDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface B02UserAccountClientService {

     B02UserAccountDO get(Long id);

     List<B02UserAccountDO> list(Map<String, Object> map);
     List<B02UserAccountDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B02UserAccountDO b02UserAccount);

     int insertBatch(List<B02UserAccountDO> b02UserAccounts);

     int update(B02UserAccountDO b02UserAccount);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     int payByBalance(PayBalanceForm form);

}
