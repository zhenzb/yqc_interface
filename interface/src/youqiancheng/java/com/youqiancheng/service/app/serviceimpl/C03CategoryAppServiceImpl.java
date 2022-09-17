package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.C03CategoryDao;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.app.service.C03CategoryAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class C03CategoryAppServiceImpl implements C03CategoryAppService {
    @Autowired
    private C03CategoryDao c03CategoryDao;

    @Override
    public List<C03CategoryDO> getByParentId(Map<String, Object> map) {
        List<C03CategoryDO> list = c03CategoryDao.list(map);
        return list;
    }
   @Override
    public List<C03CategoryDO> listFirst() {
        List<C03CategoryDO> list = c03CategoryDao.listFirst();
        return list;
    }



}
