package com.youqiancheng.service.client.service;


import com.youqiancheng.mybatis.model.A09NoticeDO;

import java.util.List;
import java.util.Map;

public interface A09NoticeClientService {
    List<A09NoticeDO> listNoticePage(Map<String, Object> map);

}
