package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/17 18:45
 * @Version: V1.0
 */
public interface ShopWithdrawalApplicationService {
    F06WithdrawalApplicationDO getWithdrawalApplicationById(long id);
    List<F06WithdrawalApplicationDO> listWithdrawalApplicationHDPage(Map<String, Object> map);
    List<F06WithdrawalApplicationDO> listHDPage(Map<String, Object> map);
    Integer batchUpdateWithdrawalApplicationById(List<F06WithdrawalApplicationDO> withdrawalApplications);
    Integer batchDelWithdrawalApplicationById(Collection<? extends Serializable> ids);
    Integer saveOrUpdateWithdrawalApplication(F06WithdrawalApplicationDO withdrawalApplication);
    Integer save(F06WithdrawalApplicationDO withdrawalApplication);
}
