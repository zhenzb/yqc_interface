package com.youqiancheng.service.client.serviceimpl.classification;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.C03CategoryDao;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.client.service.classification.C03CategoryClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/3 14:58
 */
@Service
@Transactional
public class C03CategoryClientServiceImpl implements C03CategoryClientService {
    @Resource
    private C03CategoryDao c03CategoryDao;
    @Override
    public List<C03CategoryDO> getCategoryList(String parentId){
        EntityWrapper<C03CategoryDO> ew=new EntityWrapper<C03CategoryDO>();
        ew.eq("parent_id",parentId);
        ew.eq("status",StatusConstant.enable.getCode());
        ew.eq("delete_flag", StatusConstant.enable.getCode());
        return c03CategoryDao.selectList(ew);
    }
}
