package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.form.app.F01ShopAppSaveForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.dao.shop.system.F11RoleDao;
import com.youqiancheng.mybatis.dao.shop.system.F13RoleShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.F01ShopAppService;
import com.youqiancheng.service.app.service.F08ShopAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Service
@Transactional
public class F08ShopAppServiceImpl implements F08ShopAppService {

    @Autowired
    private  F08ShopUserDao f08ShopUserDao;

    @Override
    public F08ShopUserDO getf08ShopUserDO(Map<String, Object> map) {
        List<F08ShopUserDO> list = f08ShopUserDao.list(map);
        if(list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
