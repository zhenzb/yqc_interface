package com.youqiancheng.ability;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.app.E04GrantRecordSearchForm;
import com.youqiancheng.mybatis.dao.E01RedenvelopesStreetDao;
import com.youqiancheng.mybatis.dao.E04RedenvelopesGrantRecordDao;
import com.youqiancheng.mybatis.dao.E05RedenvelopesReceiveRecordDao;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.util.RandomSplit;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShopSendRedEnvelopesAbility {

    @Resource
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Resource
    private E05RedenvelopesReceiveRecordDao e5RedenvelopesReceiveRecordDao;
    @Resource
    private E01RedenvelopesStreetDao e01RedenvelopesStreetDao;

    public long saveShopSendRedEnvelopesRecord(E04GrantRecordSearchForm form){

        //查询红包发放记录是否结束，结束插入新的，否则提示无法重复上街
        List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDOS =
                e04RedenvelopesGrantRecordDao.selectList(
                        new EntityWrapper<E04RedenvelopesGrantRecordDO>()
                                .eq("street_id",form.getStreetId())
                                .eq("shop_id",form.getShopId())
                                .eq("end_flag",StatusConstant.EndFlag.un_end.getCode())
                                .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
                );

        if(!CollectionUtils.isEmpty(e04RedenvelopesGrantRecordDOS)){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"已经上街的商家无法重新上街");
        }
        //判断快销街的上街条件：是否已在VIP街区 上街  按街道名称区分
        E01RedenvelopesStreetDO e01RedenvelopesStreetDO1 = e01RedenvelopesStreetDao.get(form.getStreetId());

        List<E01RedenvelopesStreetDO> e01RedenvelopesStreetDOS = e01RedenvelopesStreetDao.selectList(
                new EntityWrapper<E01RedenvelopesStreetDO>().like("name", "vip")
        .eq("delete_flag",1).eq("status",1).eq("category_id",1));

        if(e01RedenvelopesStreetDO1 == null){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"未查询到该街道");
        }
        if(e01RedenvelopesStreetDOS !=null){
            if(e01RedenvelopesStreetDO1.getName().contains("快销")){
                List<E04RedenvelopesGrantRecordDO> vipE04RedenvelopesGrantRecordDOS =
                        e04RedenvelopesGrantRecordDao.selectList(
                                new EntityWrapper<E04RedenvelopesGrantRecordDO>()
                                        .eq("street_id",e01RedenvelopesStreetDOS.get(0).getId())
                                        .eq("shop_id",form.getShopId())
                                        .eq("end_flag",StatusConstant.EndFlag.un_end.getCode())
                                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
                        );
                if(CollectionUtils.isEmpty(vipE04RedenvelopesGrantRecordDOS)){
                    throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"暂不能上快销街区，请先上VIP街区后才能上快销街区");
                }
            }
        }


        //保存红包发放记录
        E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord=new E04RedenvelopesGrantRecordDO();
        e04RedenvelopesGrantRecord.setShopId(form.getShopId());
        e04RedenvelopesGrantRecord.setCreatePerson(form.getCreatePerson());
        e04RedenvelopesGrantRecord.setStreetId(form.getStreetId());
        e04RedenvelopesGrantRecord.setEndFlag(StatusConstant.EndFlag.un_start.getCode());
        e04RedenvelopesGrantRecord.setUpdateTime(LocalDateTime.now());
        e04RedenvelopesGrantRecord.setUpdatePerson(form.getCreatePerson());
        e04RedenvelopesGrantRecord.setCreateTime(LocalDateTime.now());
        e04RedenvelopesGrantRecord.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        e04RedenvelopesGrantRecord.setMoney(e01RedenvelopesStreetDO1.getMoney());
        Integer insert = e04RedenvelopesGrantRecordDao.insert(e04RedenvelopesGrantRecord);
        //获取红包金额
        E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetDao.get(e04RedenvelopesGrantRecord.getStreetId());
        //扣除20%的手续费
        BigDecimal money1 = e01RedenvelopesStreetDO.getMoney();
        BigDecimal multiply = money1.multiply(new BigDecimal("0.8"));
        //查询数据获取对应街道的红包个数——默认20个
        ArrayList<BigDecimal> redPackage = RandomSplit.getRedPackage(multiply.doubleValue(), e01RedenvelopesStreetDO.getPackageNum());
        List<E05RedenvelopesReceiveRecordDO> list=new ArrayList<>();
        for (BigDecimal money : redPackage) {
            E05RedenvelopesReceiveRecordDO dto=new E05RedenvelopesReceiveRecordDO();
            dto.setIsReceive(StatusConstant.IsReceive.un_receive.getCode());
            dto.setCreatePerson(form.getCreatePerson());
            dto.setCreateTime(LocalDateTime.now());
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setGrantId(e04RedenvelopesGrantRecord.getId());
            dto.setUpdatePerson(form.getCreatePerson());
            dto.setUpdateTime(LocalDateTime.now());
            dto.setMoney(money);
            list.add(dto);
        }

        e5RedenvelopesReceiveRecordDao.insertBatch(list);
        return e04RedenvelopesGrantRecord.getId();
    }

}
