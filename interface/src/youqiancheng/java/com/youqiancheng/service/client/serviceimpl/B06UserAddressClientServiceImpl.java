package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.mybatis.dao.B06UserAddressDao;
import com.youqiancheng.mybatis.model.B06UserAddressDO;
import com.youqiancheng.service.client.service.B06UserAddressClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Service
@Transactional
public class B06UserAddressClientServiceImpl implements B06UserAddressClientService {
    @Autowired
    private B06UserAddressDao b06UserAddressDao;


    @Override
    public B06UserAddressDO get(Long id){
        return b06UserAddressDao.get(id);
    }


    @Override
    public List<B06UserAddressDO> listHDPage(Map<String, Object> map) {
        return b06UserAddressDao.listHDPage(map);
    }


    @Override
    public List<B06UserAddressDO> list(Map<String, Object> map) {
        return b06UserAddressDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b06UserAddressDao.count(map);
    }


    @Override
    public int insert(B06UserAddressDO b06UserAddress) {
        if(0==b06UserAddress.getUserId()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"用户ID不能为空");
        }
        //如果插入地址为默认地址。则修改其他地址（若为默认）为非默认
        if(StatusConstant.IsDefault.yes.getCode()==b06UserAddress.getIsDefault()) {
            List<B06UserAddressDO> list = b06UserAddressDao.selectList(
                    new EntityWrapper<B06UserAddressDO>()
                            .eq("user_id", b06UserAddress.getUserId())
                            .eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode()));
            if (list != null && !list.isEmpty()) {
                for (B06UserAddressDO b06UserAddressDO : list) {
                    if (StatusConstant.IsDefault.yes.getCode() == b06UserAddressDO.getIsDefault()) {
                        b06UserAddressDO.setIsDefault(StatusConstant.IsDefault.no.getCode());
                        b06UserAddressDao.updateById(b06UserAddressDO);
                    }
                }
            }
        }
        return b06UserAddressDao.insert(b06UserAddress);
    }


    @Override
    public int insertBatch(List<B06UserAddressDO> b06UserAddresss){
        return b06UserAddressDao.insertBatch(b06UserAddresss);
    }


    @Override
    public int update(B06UserAddressDO b06UserAddress) {
        if (0 == b06UserAddress.getUserId()) {
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "用户ID不能为空");
        }
        //如果修改为默认地址，则修改其他地址（若为默认）为非默认
        if(StatusConstant.IsDefault.yes.getCode()==b06UserAddress.getIsDefault()) {
            List<B06UserAddressDO> list = b06UserAddressDao.selectList(
                    new EntityWrapper<B06UserAddressDO>()
                            .eq("user_id", b06UserAddress.getUserId())
                            .eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode()));
            if (list != null && !list.isEmpty()) {
                for (B06UserAddressDO b06UserAddressDO : list) {
                    if (StatusConstant.IsDefault.yes.getCode() == b06UserAddressDO.getIsDefault()) {
                        b06UserAddressDO.setIsDefault(StatusConstant.IsDefault.no.getCode());
                        b06UserAddressDao.updateById(b06UserAddressDO);
                    }
                }
            }
        }
        return b06UserAddressDao.updateById(b06UserAddress);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
        return b06UserAddressDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b06UserAddressDao.updateStatus(param);
        }
    }
