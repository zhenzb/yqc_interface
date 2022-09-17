package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.form.shop.C08SelectAttributeSaveForm;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/20 10:57
 * @Version: V1.0
 */
public interface ShopSelectAttributeService {
    C08SelectAttributeDO getSelectAttributeById(Long id);
    Integer saveOrUpdateSelectAttribute(C08SelectAttributeDO selectAttribute);
    Integer batchUpdateSelectAttribute(List<C08SelectAttributeDO> selectAttributes);
    List<C08SelectAttributeDO> listSelectAttributeHDPage(Map<String, Object> map);
    List<C08SelectAttributeDO> listSelectAttribute(EntityWrapper<C08SelectAttributeDO> ew);
    int save(List<C08SelectAttributeSaveForm> list);
}
