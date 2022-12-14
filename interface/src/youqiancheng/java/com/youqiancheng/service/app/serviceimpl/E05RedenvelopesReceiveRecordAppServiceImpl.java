package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.youqiancheng.form.app.E05ReceiveRecordUpdateForm;
import com.youqiancheng.form.app.ReceiveRecordSearchForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.E05RedenvelopesReceiveRecordAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.ReceiveRecordNumVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-26
 */
@Service
@Transactional
public class E05RedenvelopesReceiveRecordAppServiceImpl implements E05RedenvelopesReceiveRecordAppService {
    @Autowired
    private E05RedenvelopesReceiveRecordDao e05RedenvelopesReceiveRecordDao;
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Autowired
    private D01OrderDao  d01OrderDao;
    @Autowired
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;
    @Autowired
    private B02UserAccountDao b02UserAccountDao;
    @Autowired
    private B03UserAccountFlowDao b03UserAccountFlowDao;

    @Autowired
    private E03RedenvelopesRuleDao e03RedenvelopesRuleDao;
    @Autowired
    private B11InvitationRecordDao b11InvitationRecordDao;
    @Override
    public E05RedenvelopesReceiveRecordDO get(Long id){
        return e05RedenvelopesReceiveRecordDao.get(id);
    }


    @Override
    public List<E05RedenvelopesReceiveRecordDO> listHDPage(Map<String, Object> map) {
        return e05RedenvelopesReceiveRecordDao.listHDPage(map);
    }


    @Override
    public List<E05RedenvelopesReceiveRecordDO> list(Map<String, Object> map) {
        return e05RedenvelopesReceiveRecordDao.list(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return e05RedenvelopesReceiveRecordDao.count(map);
    }


    @Override
    public int insert(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord) {
        return e05RedenvelopesReceiveRecordDao.insert(e05RedenvelopesReceiveRecord);
    }


    @Override
    public int insertBatch(List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecords){
        return e05RedenvelopesReceiveRecordDao.insertBatch(e05RedenvelopesReceiveRecords);
    }


    @Override
    public int update(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord) {
        return e05RedenvelopesReceiveRecordDao.updateById(e05RedenvelopesReceiveRecord);
    }

    @Override
    @Transactional
    public int receiveRedPacket(E05ReceiveRecordUpdateForm form) {
        //??????????????????????????????
        E05RedenvelopesReceiveRecordDO dto = e05RedenvelopesReceiveRecordDao.get(form.getId());
        if(dto==null){
            throw new  JsonException(ResultEnum.DATA_NOT_EXIST,"?????????????????????");
        }
        //???????????????????????????????????????????????????
        dto.setIsReceive(StatusConstant.IsReceive.receive.getCode());
        dto.setUserId(form.getUserId());
        dto.setUpdateTime(LocalDateTime.now());
        Integer update = e05RedenvelopesReceiveRecordDao.updateById(dto);
        if(update<=0){
            throw new  JsonException(ResultEnum.UPDATE_FAIL,"????????????");
        }
        //????????????????????????????????????????????????????????????????????????????????????
        verifyGrantRecord(dto);
        //??????????????????
        Map<String,Object> map=new HashMap<>();
        map.put("userId",form.getUserId());
        //map.put("countryId",form.getCategoryId());
        List<B02UserAccountDO> list = b02UserAccountDao.list(map);
        if(list==null||list.isEmpty()){
            throw new  JsonException(ResultEnum.UPDATE_FAIL,"?????????????????????");
        }
        B02UserAccountDO b02UserAccountDO = list.get(0);

        //????????????????????????
        B03UserAccountFlowDO entity=new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        entity.setOriginalMoney(b02UserAccountDO.getAccountBalance());
        entity.setMoney(dto.getMoney());
        entity.setType(TypeConstant.UserAccountType.redPacket_income.getCode());
        BigDecimal finalMoney=b02UserAccountDO.getAccountBalance().add(dto.getMoney());
        entity.setFinalMoney(finalMoney);
        b03UserAccountFlowDao.insert(entity);

        //??????????????????
        b02UserAccountDO.setAccountBalance(finalMoney);
        b02UserAccountDao.updateById(b02UserAccountDO);
        return 1;
    }
    /**
     * ????????????: <br>
     * ????????????????????????????????????

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    void verifyGrantRecord(E05RedenvelopesReceiveRecordDO dto){
        //?????????????????????????????????????????????????????????
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("grantId",dto.getGrantId());
        map.put("isReceive",StatusConstant.IsReceive.un_receive.getCode());
        List<E05RedenvelopesReceiveRecordDO> list = e05RedenvelopesReceiveRecordDao.list(map);
        //????????????????????????????????????????????????
        if(list==null||list.isEmpty()){
            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordDao.get(dto.getGrantId());
            if(e04RedenvelopesGrantRecordDO==null){
                throw new  JsonException(ResultEnum.UPDATE_FAIL,"???????????????????????????");
            }
            //???????????????????????????
            e04RedenvelopesGrantRecordDO.setEndFlag(StatusConstant.EndFlag.end.getCode());
            e04RedenvelopesGrantRecordDao.updateById(e04RedenvelopesGrantRecordDO);
        }

    }

    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return e05RedenvelopesReceiveRecordDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return e05RedenvelopesReceiveRecordDao.updateStatus(param);
        }

    @Override
    public E03RedenvelopesRuleDO getRedPacketUrl() {
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<E03RedenvelopesRuleDO> list = e03RedenvelopesRuleDao.list(map);
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"??????????????????????????????");
        }
        return list.get(0);
    }

}
