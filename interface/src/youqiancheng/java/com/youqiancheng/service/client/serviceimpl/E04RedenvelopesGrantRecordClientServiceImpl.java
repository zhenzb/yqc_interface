package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.client.E04RedenvelopesGrantRecordSaveForm;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.dao.E04RedenvelopesGrantRecordDao;
import com.youqiancheng.mybatis.dao.E05RedenvelopesReceiveRecordDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
import com.youqiancheng.util.RandomSplit;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Service
@Transactional
public class E04RedenvelopesGrantRecordClientServiceImpl implements E04RedenvelopesGrantRecordClientService {
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Autowired
    private E05RedenvelopesReceiveRecordDao e5RedenvelopesReceiveRecordDao;
    @Autowired
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;

    @Override
    public E04RedenvelopesGrantRecordDO get(Long id){
        return e04RedenvelopesGrantRecordDao.get(id);
    }

    @Override
    public BigDecimal TotalAmtHDPage(Map<String, Object> map) {
       return e04RedenvelopesGrantRecordDao.TotalAmtHDPage(map);
    }


    @Override
    public List<E04RedenvelopesGrantRecordDO> listHDPage(Map<String, Object> map) {
        return e04RedenvelopesGrantRecordDao.listHDPage(map);
    }

    @Override
    public List<F01ShopDO> getShopListByRedEnvelopes(Map<String, Object> map) {
        return e04RedenvelopesGrantRecordDao.getShopListByRedEnvelopesHDPage(map);
    }


    @Override
    public List<E04RedenvelopesGrantRecordDO> list(Map<String, Object> map) {
        return e04RedenvelopesGrantRecordDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return e04RedenvelopesGrantRecordDao.count(map);
    }


    @Override
    public long insert(E04RedenvelopesGrantRecordSaveForm form ) {
        //查询红包发放记录是否结束，结束插入新的，否则提示无法重复上街

//        List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDOS =
//                e04RedenvelopesGrantRecordDao.selectList(
//                new EntityWrapper<E04RedenvelopesGrantRecordDO>()
//                        .eq("street_id",form.getStreetId())
//                        .eq("shop_id",form.getShopId())
//                        .eq("end_flag",StatusConstant.EndFlag.un_end.getCode())
//                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
//                );
//
//        if(!CollectionUtils.isEmpty(e04RedenvelopesGrantRecordDOS)){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"已经上街的商家无法重新上街");
//        }
//        //保存红包发放记录
//        E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord=new E04RedenvelopesGrantRecordDO();
//        e04RedenvelopesGrantRecord.setShopId(form.getShopId());
//        e04RedenvelopesGrantRecord.setCreatePerson(form.getCreatePerson());
//        e04RedenvelopesGrantRecord.setStreetId(form.getStreetId());
//        e04RedenvelopesGrantRecord.setEndFlag(StatusConstant.EndFlag.un_start.getCode());
//        e04RedenvelopesGrantRecord.setUpdateTime(LocalDateTime.now());
//        e04RedenvelopesGrantRecord.setUpdatePerson(form.getCreatePerson());
//        e04RedenvelopesGrantRecord.setCreateTime(LocalDateTime.now());
//        e04RedenvelopesGrantRecord.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
//        Integer insert = e04RedenvelopesGrantRecordDao.insert(e04RedenvelopesGrantRecord);
//        //获取红包金额
//        E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetDao.get(e04RedenvelopesGrantRecord.getStreetId());
//        //查分红包——20个
//        ArrayList<BigDecimal> redPackage = RandomSplit.getRedPackage(e01RedenvelopesStreetDO.getMoney().doubleValue(), 20);
//        List<E05RedenvelopesReceiveRecordDO> list=new ArrayList<>();
//        for (BigDecimal money : redPackage) {
//            E05RedenvelopesReceiveRecordDO dto=new E05RedenvelopesReceiveRecordDO();
//            dto.setIsReceive(StatusConstant.IsReceive.un_receive.getCode());
//            dto.setCreatePerson(form.getCreatePerson());
//            dto.setCreateTime(LocalDateTime.now());
//            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
//            dto.setGrantId(e04RedenvelopesGrantRecord.getId());
//            dto.setUpdatePerson(form.getCreatePerson());
//            dto.setUpdateTime(LocalDateTime.now());
//            dto.setMoney(money);
//            list.add(dto);
//        }
//
//         e5RedenvelopesReceiveRecordDao.insertBatch(list);
//        return e04RedenvelopesGrantRecord.getId();
        return 0;
    }


    @Override
    public int insertBatch(List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecords){
        return e04RedenvelopesGrantRecordDao.insertBatch(e04RedenvelopesGrantRecords);
    }


    @Override
    public int update(E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord) {
        return e04RedenvelopesGrantRecordDao.updateById(e04RedenvelopesGrantRecord);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return e04RedenvelopesGrantRecordDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return e04RedenvelopesGrantRecordDao.updateStatus(param);
        }

    @Override
    public List<E05RedenvelopesReceiveRecordDO> getShopRedPacket(Map<String, Object> map) {
        return e04RedenvelopesGrantRecordDao.getShopRedPacket(map);
    }

    @Override
    public int updateStatus(Long id,String no,int type) {
        E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordDao.get(id);
        if(e04RedenvelopesGrantRecordDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放记录不存在");
        }
        e04RedenvelopesGrantRecordDO.setEndFlag(StatusConstant.EndFlag.un_end.getCode());
        e04RedenvelopesGrantRecordDO.setPayType(type);
        e04RedenvelopesGrantRecordDO.setPayNo(no);
        Integer integer = e04RedenvelopesGrantRecordDao.updateById(e04RedenvelopesGrantRecordDO);
        if(integer<=0){
            throw new JsonException(ResultEnum.UPDATE_FAIL,"红包发放记录修改状态失败");
        }
        return 1;
    }
}
