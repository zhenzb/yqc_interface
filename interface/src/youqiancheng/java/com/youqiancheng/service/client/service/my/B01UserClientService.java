package com.youqiancheng.service.client.service.classification;

import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.util.QueryMap;

import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 17:42
 */

public interface B01UserClientService {
 B01UserDO getUserInfo(Long userId);
 B02UserAccountDO getUserAccountInfo(Long userId);
 List<B03UserAccountFlowDO> getUserAccountFlowList( QueryMap map);
 List<D01OrderDO>  getOrderList(QueryMap map);
}
