package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.B07AuthenticationDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import com.youqiancheng.service.client.service.B07AuthenticationClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Service
@Transactional
public class B07AuthenticationClientServiceImpl implements B07AuthenticationClientService {
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private B01UserDao b01UserDao;

    @Override
    public B07AuthenticationDO get(Long id){
        return b07AuthenticationDao.get(id);
    }


    @Override
    public List<B07AuthenticationDO> listHDPage(Map<String, Object> map) {
        return b07AuthenticationDao.listHDPage(map);
    }


    @Override
    public List<B07AuthenticationDO> list(Map<String, Object> map) {
        return b07AuthenticationDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b07AuthenticationDao.count(map);
    }


    @Override
    public int insert(B07AuthenticationDO b07Authentication) {
        String cardNo = b07Authentication.getCardNo();
        if(StringUtils.isBlank(cardNo)){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"身份证号不能为空");
        }
        if(cardNo.length()!=18){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"身份证号位数为18位");
        }
        try {
            String year = cardNo.substring(6, 10);
            String mouth = cardNo.substring(10, 12);
            String day = cardNo.substring(12, 14);
            B01UserDO b01UserDO = b01UserDao.get(b07Authentication.getUserId());
            if(b01UserDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");
            }
            LocalDate date=LocalDate.parse(year+"-"+mouth+"-"+day);
            b01UserDO.setBirthday(date);
            b01UserDao.updateById(b01UserDO);
        }catch (Exception e){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"请输入正确的身份证号");
        }
        return b07AuthenticationDao.insert(b07Authentication);
    }


    @Override
    public int insertBatch(List<B07AuthenticationDO> b07Authentications){
        return b07AuthenticationDao.insertBatch(b07Authentications);
    }


    @Override
    public int update(B07AuthenticationDO b07Authentication) {
        b07Authentication.setUpdateTime(LocalDateTime.now());
        return b07AuthenticationDao.updateById(b07Authentication);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b07AuthenticationDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b07AuthenticationDao.updateStatus(param);
        }
    }
