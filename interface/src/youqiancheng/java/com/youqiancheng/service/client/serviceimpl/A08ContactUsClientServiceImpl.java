package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.youqiancheng.mybatis.dao.A06BaseInfoDao;
import com.youqiancheng.mybatis.dao.A08ContactUsDao;
import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import com.youqiancheng.mybatis.model.A08ContactUsDO;
import com.youqiancheng.service.client.service.A08ContactUsClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class A08ContactUsClientServiceImpl implements A08ContactUsClientService {
    @Autowired
    private A08ContactUsDao a08ContactUsDao;
    @Autowired
    private A06BaseInfoDao a06BaseInfoDao;

    @Override
    public List<A08ContactUsDO> list(Map<String, Object> map) {
        return a08ContactUsDao.list(map);
    }

    @Override
    public int addBrowseVolume() {
        List<A06BaseInfoDO> list = a06BaseInfoDao.list(new HashMap<>());
        if(list==null||list.size()==0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到平台基本信息");
        }
        A06BaseInfoDO a06BaseInfoDO = list.get(0);
        a06BaseInfoDO.setBrowseVolume(a06BaseInfoDO.getBrowseVolume()+1);
        a06BaseInfoDO.setUpdateTime(LocalDateTime.now());
        a06BaseInfoDao.updateById(a06BaseInfoDO);
        return 1;
    }

    @Override
    public int getBrowseVolume() {
        List<A06BaseInfoDO> list = a06BaseInfoDao.list(new HashMap<>());
        if(list==null||list.size()==0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到平台基本信息");
        }
        A06BaseInfoDO a06BaseInfoDO = list.get(0);
        return a06BaseInfoDO.getBrowseVolume();
    }


}
