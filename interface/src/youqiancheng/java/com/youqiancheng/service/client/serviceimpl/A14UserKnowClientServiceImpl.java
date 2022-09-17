package com.youqiancheng.service.client.serviceimpl;

import com.youqiancheng.mybatis.dao.A14UserKnowDao;
import com.youqiancheng.mybatis.model.A14UserKnowDO;
import com.youqiancheng.service.client.service.A14UserKnowClientService;
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
public class A14UserKnowClientServiceImpl implements A14UserKnowClientService {
    @Autowired
    private A14UserKnowDao a14UserKnowDao;

    @Override
    public List<A14UserKnowDO> list(Map<String, Object> map) {
        return a14UserKnowDao.list(map);
    }

}
