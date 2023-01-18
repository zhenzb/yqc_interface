package com.youqiancheng.ability;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.B02UserAccountClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserAccountFlowAbility {

    @Resource
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Resource
    private B02UserAccountDao b02UserAccountDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Resource
    private B02UserAccountClientService b02UserAccountClientService;
    /**
     * 增加用户账号流水记录
     * @param b02UserAccountDO
     * @param money
     * @param type
     */
    public void addUserAccountFlow( B02UserAccountDO b02UserAccountDO,BigDecimal money,int type ){
        //新增用户账户流水
        B03UserAccountFlowDO entity=new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        entity.setOriginalMoney(b02UserAccountDO.getAccountBalance());
        entity.setMoney(money);
        entity.setType(type);
        BigDecimal finalMoney;
        if(type == TypeConstant.UserAccountType.redPacket_income.getCode()){
            finalMoney=b02UserAccountDO.getAccountBalance().add(b02UserAccountDO.getWithdrawalBalance());
        }else{
            finalMoney=b02UserAccountDO.getWithdrawalBalance();
        }
        entity.setFinalMoney(finalMoney);
        b03UserAccountFlowDao.insert(entity);
    }

    /**
     * 增加用户账号流水记录2
     * @param b02UserAccountDO
     * @param money
     * @param type
     */
    public void addUserAccountFlow2( B02UserAccountDO b02UserAccountDO,BigDecimal money,int type ){
        //新增用户账户流水
        B03UserAccountFlowDO entity=new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        entity.setOriginalMoney(b02UserAccountDO.getAccountBalance());
        entity.setMoney(money);
        entity.setType(type);
        BigDecimal finalMoney;
        if(type == 1){
            finalMoney=b02UserAccountDO.getAccountBalance().add(money);
        }else{
            finalMoney=b02UserAccountDO.getAccountBalance().subtract(money);
        }
        entity.setFinalMoney(finalMoney);
        b03UserAccountFlowDao.insert(entity);
    }

    //增加用户，线下消费，线上支付流水
    public Boolean addUserFacePayFlow(Long userId, Long shopId,BigDecimal money){
        List<B02UserAccountDO> b02UserAccountDO = b02UserAccountDao.getAccountBalanceByUserId(userId);
        if(b02UserAccountDO.size()==0){
            return false;
        }
        B03UserAccountFlowDO entity = new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.get(0).getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        entity.setOriginalMoney(new BigDecimal("0"));
        entity.setMoney(money);
        entity.setType(9);
        entity.setSourceId(shopId);
        entity.setFinalMoney(new BigDecimal("0"));
        b03UserAccountFlowDao.insert(entity);
        return true;
    }

    //增加商家线下消费，线上支付流水
    public Boolean addBussinessFacePayFlow(Long userId, Long shopId,BigDecimal money){
        //增加商户账户流水
        F05ShopAccountDO f05ShopAccountDO = f05ShopAccountDao.getShopAccountbyShopId(shopId);
        B01UserDO b01UserDO = b01UserDao.get(userId);
        if(f05ShopAccountDO ==null || b01UserDO == null){
            return false;
        }
        F07ShopAccountFlowDO f07ShopAccountFlowDo = new F07ShopAccountFlowDO();
        f07ShopAccountFlowDo.setAccountId(f05ShopAccountDO.getId());
        f07ShopAccountFlowDo.setOriginalMoney(f05ShopAccountDO.getAvailableWithdrawMoney());
        f07ShopAccountFlowDo.setMoney(money);
        f07ShopAccountFlowDo.setFinalMoney(f05ShopAccountDO.getAvailableWithdrawMoney());
        f07ShopAccountFlowDo.setSourceId(userId);
        f07ShopAccountFlowDo.setType(1);
        f07ShopAccountFlowDo.setStatus(9);
        f07ShopAccountFlowDo.setCreatePerson(String.valueOf(userId));
        f07ShopAccountFlowDo.setUpdatePerson(b01UserDO.getMobile());
        f07ShopAccountFlowDo.setCreateTime(LocalDateTime.now());
        f07ShopAccountFlowDo.setDeleteFlag(1);
        f07ShopAccountFlowDo.setIsFace(3);
        f07ShopAccountFlowDao.insert(f07ShopAccountFlowDo);
        return true;
    }

    /**
     * 获取用户账户信息
     * @param userId
     * @return
     */
    public B02UserAccountDO getUserAccount(Long userId){
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",userId);
        //map.put("countryId",1);
        List<B02UserAccountDO> list = b02UserAccountClientService.list(map);
        if(CollectionUtils.isEmpty(list)){
            return new B02UserAccountDO();
        }
        B02UserAccountDO b02UserAccountDO = list.get(0);
        return b02UserAccountDO;
    }

    /**
     * 修改用户账户资金
     * @param b02UserAccountDO
     * @return
     */
    public boolean updateUserAccount(B02UserAccountDO b02UserAccountDO){
        int update = b02UserAccountClientService.update(b02UserAccountDO);
        if(1 == update){
            return true;
        }else{
            return false;
        }
    }
}
