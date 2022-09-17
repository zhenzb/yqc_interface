package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.C02GoodsPicDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/21 13:24
 * @Version: V1.0
 */
public interface GoodsPicService {
    List<C02GoodsPicDO> listGoodsPic(EntityWrapper<C02GoodsPicDO> ew);
    List<C02GoodsPicDO> listGoodsPicPage(Map<String, Object> map);
    C02GoodsPicDO getGoodsPicById(long id);
    Integer batchUpdateGoodsPicById(List<C02GoodsPicDO> goodsPics);
    Integer saveOrUpdateGoodsPic(C02GoodsPicDO goodsPic);
}
