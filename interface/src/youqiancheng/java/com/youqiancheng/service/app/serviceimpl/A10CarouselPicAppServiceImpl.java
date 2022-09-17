package com.youqiancheng.service.app.serviceimpl;

import com.youqiancheng.mybatis.dao.A10CarouselPicDao;
import com.youqiancheng.mybatis.model.A10CarouselPicDO;
import com.youqiancheng.service.app.service.A10CarouselPicAppService;
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
public class A10CarouselPicAppServiceImpl implements A10CarouselPicAppService {
    @Autowired
    private A10CarouselPicDao a10CarouselPicDao;

    @Override
    public List<A10CarouselPicDO> list(Map<String, Object> map) {
        return a10CarouselPicDao.list(map);
    }


}
