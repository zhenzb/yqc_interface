package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.B02UserAccountDao;
import com.youqiancheng.mybatis.dao.C03CategoryDao;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.client.service.B01UserClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Service
@Transactional
public class B01UserClientServiceImpl implements B01UserClientService {
    @Autowired
    private B01UserDao b01UserDao;
    @Autowired
    private B02UserAccountDao b02UserAccountDao;
    @Autowired
    private C03CategoryDao c03CategoryDao;

    @Override
    public B01UserDO get(Long id){
        return b01UserDao.get(id);
    }


    @Override
    public List<B01UserDO> listHDPage(Map<String, Object> map) {
        return b01UserDao.listHDPage(map);
    }


    @Override
    public List<B01UserDO> list(Map<String, Object> map) {
        return b01UserDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b01UserDao.count(map);
    }


    @Override
    public int insert(B01UserDO b01User) {
        Integer insert = b01UserDao.insert(b01User);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"插入用户信息失败");
        }
        //初始化用户账号

        //List<C03CategoryDO> list = c03CategoryDao.listFirst();
        //if(!CollectionUtils.isEmpty(list)){
           // for (C03CategoryDO c03CategoryDO : list) {
                B02UserAccountDO dto=new B02UserAccountDO();
                dto.setUpdateTime(LocalDateTime.now());
                dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                dto.setUserId(b01User.getId());
                dto.setCountryId(1);
                dto.setCreatePerson(b01User.getMobile());
                dto.setUpdatePerson(b01User.getMobile());
                dto.setCreateTime(LocalDateTime.now());
                dto.setStatus(StatusConstant.enable.getCode());
                b02UserAccountDao.insert(dto);
          //  }
        //}
        return 1;
    }


    @Override
    public int insertBatch(List<B01UserDO> b01Users){
        return b01UserDao.insertBatch(b01Users);
    }


    @Override
    public int update(B01UserDO b01User) {
        b01User.setUpdateTime(LocalDateTime.now());
        return b01UserDao.updateById(b01User);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b01UserDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b01UserDao.updateStatus(param);
        }
}
