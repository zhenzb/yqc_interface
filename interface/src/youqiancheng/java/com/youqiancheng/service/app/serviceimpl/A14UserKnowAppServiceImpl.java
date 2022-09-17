package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.A14UserKnowDao;
import com.youqiancheng.mybatis.model.A14UserKnowDO;
import com.youqiancheng.service.app.service.A14UserKnowAppService;
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
public class A14UserKnowAppServiceImpl implements A14UserKnowAppService {
    @Autowired
    private A14UserKnowDao a14UserKnowDao;

    @Override
    public List<A14UserKnowDO> list(Map<String, Object> map) {
        return a14UserKnowDao.list(map);
    }

}
