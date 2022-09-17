package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.C10PublicityDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 16:21
 * @Version: V1.0
 */
public interface ShopPublicityService {
    List<C10PublicityDO> listPublicityPage(Map<String, Object> map);

    int  count(Map<String, Object> map);
    C10PublicityDO getPublicityById(long id);
    //String getCategoryNameById(long id);
    Integer batchUpdatePublicityById(List<C10PublicityDO> publicitys);
    Integer saveOrUpdatePublicity(C10PublicityDO publicity);
    Integer saveOrUpdatePublicity2(C10PublicityDO publicity);
}
