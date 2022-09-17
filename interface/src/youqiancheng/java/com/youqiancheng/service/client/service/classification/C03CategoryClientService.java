package com.youqiancheng.service.client.service.classification;

import com.youqiancheng.mybatis.model.C03CategoryDO;

import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 14:57
 */
public interface C03CategoryClientService {
    List<C03CategoryDO> getCategoryList(String parentId);
}
