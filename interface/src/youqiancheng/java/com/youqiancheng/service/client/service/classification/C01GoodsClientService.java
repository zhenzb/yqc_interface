package com.youqiancheng.service.client.service.classification;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.util.QueryMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 17:42
 */
@Service
@Transactional
public interface C01GoodsClientService {
    List<C01GoodsDO> getGoodsListPage(QueryMap map);
    C01GoodsDO getGoodsInfo(Long id);
    List<C02GoodsPicDO> getGoodsPic(Long id);
    List<C08SelectAttributeDO> getGoodSelecttAttribute(Long id);
    List<C09GoodsSkuDO> getGoodsku(Long id,String specifications);
    List<D04GoodsEvaluateDO> getGoodsEvaluateList(Long id);
    int  saveCollection(Long id,Long userId);

}
