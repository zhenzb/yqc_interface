package com.youqiancheng.service.client.serviceimpl;

import com.youqiancheng.mybatis.dao.C03CategoryDao;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.client.service.C03CategoryClientService;
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
public class C03CategoryClientServiceImpl implements C03CategoryClientService {
    @Autowired
    private C03CategoryDao c03CategoryDao;

    @Override
    public List<C03CategoryDO> getByParentId(Map<String, Object> map) {
        List<C03CategoryDO> list = c03CategoryDao.list(map);
        return list;
    }

    @Override
    public List<C03CategoryDO> listFirst() {
        return c03CategoryDao.listFirst();
    }
}
