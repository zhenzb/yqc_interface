package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.GoodsPicService;
import com.youqiancheng.service.shop.ShopC13PublicityAutio;
import com.youqiancheng.service.shop.ShopPublicityService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 16:22
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopPublicityServiceAutioImpl implements ShopC13PublicityAutio {

    @Autowired
    private C13PublicityAutioDao c13PublicityAutioDao;
/*
    @Override
    public List<C13PublicityAutioDO> listC13Autio(EntityWrapper<C13PublicityAutioDO> ew) {
        return c13PublicityAutioDao.selectList(ew);
    }


    //关联删除宣传的音频
    @Override
    public Integer delete(Long id) {
        QueryMap map = new QueryMap();
        map.put("deleteFlag", StatusConstant.DeleteFlag.un_delete.getCode());
        map.put("publicityId",id);
        List<C13PublicityAutioDO> list = c13PublicityAutioDao.list(map);
        if (list != null && list.size() > 0) {
            for (C13PublicityAutioDO c13 : list
            ) {
                c13.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                c13.setShopId(c13.getShopId());
                c13.setShopId(c13.getPublicityId());
                c13.setAutioName(c13.getAutioName());
                c13.setAutioUrl(c13.getAutioUrl());
                c13.setCreatePerson(c13.getCreatePerson());
                c13.setCreateTime(LocalDateTime.now());
                c13.setUpdatePerson(c13.getUpdatePerson());
                c13.setUpdateTime(LocalDateTime.now());
                Integer i = c13PublicityAutioDao.updateById(c13);
                if (i < 0) {
                    throw new JsonException(ResultEnum.DELETE_FAIL);
                }
            }

        }
        return 1;

    }*/
}

