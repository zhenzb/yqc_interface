package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.A11AdvertisementPicDao;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import com.youqiancheng.service.client.service.A11AdvertisementPicClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Service
@Transactional
public class A11AdvertisementPicClientServiceImpl implements A11AdvertisementPicClientService {
    @Autowired
    private A11AdvertisementPicDao a11AdvertisementPicDao;

    /**
     * 方法实现说明
     *
     * @return 启动页列表
     * @author nl
     * @date 2020/4/3 10:12
     */
    @Override
    public List<A11AdvertisementPicDO> getAdPicList(int type) {
        EntityWrapper<A11AdvertisementPicDO> ew = new EntityWrapper<>();
        ew.eq("type", type);
        ew.eq("status", StatusConstant.enable.getCode());
        ew.eq("delete_flag", StatusConstant.enable.getCode());
        ew.orderBy("order_num", true);
        return a11AdvertisementPicDao.selectList(ew);

    }

    @Override
    public List<A11AdvertisementPicDO> listHDPage(Map<String, Object> map) {
        return a11AdvertisementPicDao.listHDPage(map);
    }


}
