package com.youqiancheng.service.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.youqiancheng.form.shop.C07SelectProjectSaveForm;
import com.youqiancheng.mybatis.model.C07SelectProjectDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/20 10:36
 * @Version: V1.0
 */
public interface ShopSelectProjectService {
    C07SelectProjectDO getSelectProjectById(Long id);
    Integer saveOrUpdateSelectProject(C07SelectProjectDO selectProjec);
    Integer batchUpdateSelectProject(List<C07SelectProjectDO> selectProjects);
    List<C07SelectProjectDO> listSelectProjectHDPage(Map<String, Object> map);
    List<C07SelectProjectDO> listSelectProject(EntityWrapper<C07SelectProjectDO> ew);
    int  save(C07SelectProjectSaveForm form);
    List<C07SelectProjectDO>  listWithContent(Map<String, Object> map);
}
