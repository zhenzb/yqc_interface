package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.mybatis.model.C06AttributeProjectDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/18 17:27
 * @Version: V1.0
 */
public interface ShopAttributeProjectService {
    C06AttributeProjectDO getAttributeProjectById(Long id);
    List<C06AttributeProjectDO> listAttributeProject(EntityWrapper<C06AttributeProjectDO> ew);
    List<C06AttributeProjectDO> listAttributeProjectHDPage(Map<String, Object> map);
    Integer saveOrUpdateAttributeProject(C06AttributeProjectDO attributeProject);
    Integer batchUpdateAttributeProject(List<C06AttributeProjectDO> attributeProjects);

}
