package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.A08ContactUsDao;
import com.youqiancheng.mybatis.model.A08ContactUsDO;
import com.youqiancheng.service.app.service.A08ContactUsAppService;
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
public class A08ContactUsAppServiceImpl implements A08ContactUsAppService {
    @Autowired
    private A08ContactUsDao a08ContactUsDao;


    @Override
    public List<A08ContactUsDO> list(Map<String, Object> map) {
        return a08ContactUsDao.list(map);
    }


}
