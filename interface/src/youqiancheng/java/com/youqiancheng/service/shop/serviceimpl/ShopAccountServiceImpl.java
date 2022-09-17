package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.ShopAccountService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.shop.WithdrawalParamVO;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.xml.ws.RequestWrapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:52
 * @Version: V1.0
 */
@Slf4j
@Service
@Transactional
public class ShopAccountServiceImpl implements ShopAccountService {
    @Autowired
    private F05ShopAccountDao shopAccountDao;
    @Resource
    private F09WithdrawalSetDao f09WithdrawalSetDao;
    @Resource
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Resource
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Resource
    private F10WithdrawalRuleDao  f10WithdrawalRuleDao;

    @Override
    public F05ShopAccountDO getShopAccountById(long id) {
        return shopAccountDao.selectById(id);
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/17 9:31
     * @Param:
     * @return:
     * @Description: 根据当前登录用户获取商家账户对象
     */
    @Override
    public F05ShopAccountDO getShopAccountByUserId() {
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Failure, "登录超时");
        }
        EntityWrapper<F05ShopAccountDO> ew = new EntityWrapper<>();
        ew.eq("shop_id",shopUser.getShopId());
        ew.eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode());
        List<F05ShopAccountDO> list = shopAccountDao.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            throw new JsonException(Constants.$Failure, "用户不匹配");
        }
        return list.get(0);
    }

    /**
     * 功能描述: <br>
     * 〈结算可提现金额〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    @Override
    public void getWithdrawableMoney(Long accountId) {
//        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(accountId);
//        if(f05ShopAccountDO==null){
//            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"账户信息不存在");
//        }
//        // DATE_ADD(NOW(),INTERVAL -7 day);
//        Map<String, Object> map=new HashMap<>();
//        map.put("accountId",accountId);
//        map.put("type", TypeConstant.ShopAccountType.sale_income.getCode());
//        BigDecimal withdrawableMoney = f07ShopAccountFlowDao.getWithdrawableMoney(map);
//        BigDecimal money=f05ShopAccountDO.getAvailableBalance().subtract(withdrawableMoney);
//        f05ShopAccountDO.setAvailableWithdrawMoney(money);
//        shopAccountDao.updateById(f05ShopAccountDO);
    }


    /**
     * 功能描述: <br>
     * 〈定时结算可提现金额〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:getReadGrantRecord
     */
    @Override
    public void updateShopAccount(Long accountId) {
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.getShopAccountbyShopId(accountId);
        if(f05ShopAccountDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"账户信息不存在");
        }
        // DATE_ADD(NOW(),INTERVAL -7 day);
        Map<String, Object> map=new HashMap<>();
        map.put("accountId",f05ShopAccountDO.getId());
        map.put("type", TypeConstant.ShopAccountType.sale_income.getCode());
        List<F07ShopAccountFlowDO> shopAccountFlowList = f07ShopAccountFlowDao.getWithdrawableMoney(map);
        BigDecimal withdrawableMoney = new BigDecimal("0.00");
        for(F07ShopAccountFlowDO f07ShopAccountFlowDO:shopAccountFlowList) {
            withdrawableMoney = withdrawableMoney.add(f07ShopAccountFlowDO.getMoney());
            f07ShopAccountFlowDO.setFlowStatus(1);
            f07ShopAccountFlowDao.updateById(f07ShopAccountFlowDO);

            //余额减去对应值
            BigDecimal subtract = f05ShopAccountDO.getAvailableBalance().subtract(withdrawableMoney);
            if(subtract.compareTo(new BigDecimal(0))==-1){
                throw new JsonException(Constants.$Failure, "账号金额不能小于零");
            }
            f05ShopAccountDO.setAvailableBalance(subtract);
            f05ShopAccountDO.setAccountBalance(subtract);
            //BigDecimal money=f05ShopAccountDO.getAvailableBalance().subtract(withdrawableMoney);
            //可提现金额加上相应值
            BigDecimal money = DecimalUtil.addBigMal(String.valueOf(f05ShopAccountDO.getAvailableWithdrawMoney()), String.valueOf(withdrawableMoney), 0, 0);
            f05ShopAccountDO.setAvailableWithdrawMoney(money);
            shopAccountDao.updateById(f05ShopAccountDO);
            //增加余额转可提现金额记录
            try{
                F07ShopAccountFlowDO f07ShopAccountFlowDo = new F07ShopAccountFlowDO();
                f07ShopAccountFlowDo.setAccountId(f05ShopAccountDO.getId());
                f07ShopAccountFlowDo.setOriginalMoney(f05ShopAccountDO.getAvailableWithdrawMoney());
                f07ShopAccountFlowDo.setMoney(withdrawableMoney);
                f07ShopAccountFlowDo.setFinalMoney(money);
                f07ShopAccountFlowDo.setSourceId(0);
                f07ShopAccountFlowDo.setType(1);
                f07ShopAccountFlowDo.setStatus(9);
                f07ShopAccountFlowDo.setCreatePerson("余额转为可提现金额");
                f07ShopAccountFlowDo.setCreateTime(LocalDateTime.now());
                f07ShopAccountFlowDo.setDeleteFlag(1);
                f07ShopAccountFlowDo.setFlowStatus(1);
                f07ShopAccountFlowDao.insert(f07ShopAccountFlowDo);
                f07ShopAccountFlowDo.setType(2);
                f07ShopAccountFlowDo.setStatus(1);
                f07ShopAccountFlowDo.setFinalMoney(subtract);
                f07ShopAccountFlowDao.insert(f07ShopAccountFlowDo);
            }catch (Exception e){
                log.error("增加余额转可提现金额流水失败 "+e.toString());
            }
        }
        if(shopAccountFlowList.size()==0){
            log.info("未查到该商户账户流水.....");
        }

    }
    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/18 14:24
     * @Param:
     * @return:
     * @Description: 商家账户总余额  入账管理：订单完成，则 添加 商家账户总余额
     */
    @Override
    public Integer inAccountMaintain(D01OrderDO order) {
        if (order == null){
            throw new JsonException(Constants.$Failure, "订单不存在");
        }
        EntityWrapper<F05ShopAccountDO> ew = new EntityWrapper<>();
        ew.eq("shopId",order.getShopId());
        List<F05ShopAccountDO> list = shopAccountDao.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            throw new JsonException(Constants.$Failure, "商家账户不存在");
        }
        F05ShopAccountDO shopAccount = list.get(0);
        //账户总余额=原总余额+订单金额
        shopAccount.setAccountBalance(shopAccount.getAccountBalance().add(order.getOrderPrice()));
        //账户可用余额=总余额-提现中金额
        shopAccount.setAvailableBalance(shopAccount.getAccountBalance().subtract(shopAccount.getInWithdrawMoney()));
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser != null){
            shopAccount.setUpdatePerson(shopUser.getUserName());
        }
        shopAccount.setUpdateTime(LocalDateTime.now());
        shopAccountDao.updateById(shopAccount);
        //账户可提现金额——计算保存
        getWithdrawableMoney(shopAccount.getId());
        return 1;
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/18 14:40
     * @Param:
     * @return:
     * @Description:  出账管理（商家账户总余额/可提现余额/累计提现金额）：提现申请通过，则 减 商家账户总余额
     */
    @Override
    public Integer outAccountMaintain(F06WithdrawalApplicationDO withdrawalApplication) {
        if (withdrawalApplication == null){
            throw new JsonException(Constants.$Failure, "提现申请单不存在");
        }
        EntityWrapper<F05ShopAccountDO> ew = new EntityWrapper<>();
        ew.eq("id",withdrawalApplication.getAccountId());
        List<F05ShopAccountDO> list = shopAccountDao.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            throw new JsonException(Constants.$Failure, "商家账户不存在");
        }
        F05ShopAccountDO shopAccount = list.get(0);
        if(withdrawalApplication.getWithdrawalMoney().compareTo(shopAccount.getAvailableWithdrawMoney())==1){
            throw new JsonException(Constants.$Failure, "提现金额大于可提现余额");
        }
        // 提现金额中金额
        shopAccount.setInWithdrawMoney(withdrawalApplication.getWithdrawalMoney());
        //可用余额 = 账户总余额 - 提现金额中金额
       shopAccount.setAvailableBalance(shopAccount.getAccountBalance().subtract(shopAccount.getInWithdrawMoney()));
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser != null){
            shopAccount.setUpdatePerson(shopUser.getUserName());
        }
        shopAccount.setUpdateTime(LocalDateTime.now());
        shopAccountDao.updateById(shopAccount);
        //计算可提现金额
        getWithdrawableMoney(shopAccount.getId());
        return 1;
    }


    @Override
    public WithdrawalParamVO getWithdrawalParam(Long id) {
        WithdrawalParamVO vo =new WithdrawalParamVO();
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(id);
        List<F09WithdrawalSetDO> list = f09WithdrawalSetDao.list(new HashMap<>());
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到提现设置内容");
        }
        vo.setMoney(list.get(0).getLowerLimit());
        BigDecimal serviceRatio1 = list.get(0).getServiceRatio();
        BigDecimal divide1 = serviceRatio1.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_UP);
        vo.setOriginalServiceRratio(divide1);

        Map<String,Object> map =new HashMap<>();
        map.put("shopId",f05ShopAccountDO.getShopId());
        BigDecimal grantMoney = e04RedenvelopesGrantRecordDao.getGrantMoney(map);
        vo.setRedMoney(grantMoney);
        BigDecimal serviceRatio = f10WithdrawalRuleDao.getServiceRatio(grantMoney);
        if(serviceRatio==null){
            vo.setActualServiceRratio(divide1);
        }else{
            BigDecimal divide = serviceRatio.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_UP);
            vo.setActualServiceRratio(divide);
        }
        return vo;
    }
}


