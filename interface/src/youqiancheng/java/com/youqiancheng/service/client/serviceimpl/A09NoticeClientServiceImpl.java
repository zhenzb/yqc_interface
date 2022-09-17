package com.youqiancheng.service.client.serviceimpl;

import com.youqiancheng.mybatis.dao.A09NoticeDao;
import com.youqiancheng.mybatis.model.A09NoticeDO;
import com.youqiancheng.service.client.service.A09NoticeClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class A09NoticeClientServiceImpl implements A09NoticeClientService {

    @Resource
    private A09NoticeDao a09NoticeDao;

    /**
    　* @Description: 获取公告列表
    　* @author shalongteng
    　* @date 2020/4/2 14:08
     * */
    @Override
    public List<A09NoticeDO> listNoticePage(Map<String, Object> map) {
        return a09NoticeDao.listNoticeHDPage(map);
    }


}
