package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.NoUtil;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.dao.F06WithdrawalApplicationDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/17 18:46
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopWithdrawalApplicationServiceImpl implements ShopWithdrawalApplicationService {
    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    @Resource
    private ShopRedenvelopesGrantRecordService shopRedenvelopesGrantRecordService;
    @Resource
    private ShopWithdrawalSetService shopWithdrawalSetService;
    @Resource
    private ShopRedenvelopesStreetService shopRedenvelopesStreetService;
    @Resource
    private ShopWithdrawalRuleService shopWithdrawalRuleService;
    @Resource
    private F05ShopAccountDao shopAccountDao;

    @Override
    public F06WithdrawalApplicationDO getWithdrawalApplicationById(long id) {
        return withdrawalApplicationDao.selectById(id);
    }

    @Override
    public List<F06WithdrawalApplicationDO> listWithdrawalApplicationHDPage(Map<String, Object> map) {
        return withdrawalApplicationDao.listWithdrawalApplicationHDPage(map);
    }
 @Override
    public List<F06WithdrawalApplicationDO> listHDPage(Map<String, Object> map) {
        return withdrawalApplicationDao.listHDPage(map);
    }

    @Override
    public Integer batchUpdateWithdrawalApplicationById(List<F06WithdrawalApplicationDO> withdrawalApplications) {
        if (CollectionUtils.isEmpty(withdrawalApplications)){
            return 0;
        }
        return withdrawalApplicationDao.updateList(withdrawalApplications);
    }

    @Override
    public Integer batchDelWithdrawalApplicationById(Collection<? extends Serializable> ids) {
        return withdrawalApplicationDao.deleteBatchIds(ids);
    }

    @Override
    public Integer saveOrUpdateWithdrawalApplication(F06WithdrawalApplicationDO withdrawalApplication) {
        if (withdrawalApplication == null){
            return 0;
        }
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        if (withdrawalApplication.getId() == null){//添加
            /**
             * 提现规则:
             *      1、判断是否达到提现标准：取提现设置（f09_withdrawal_set）  判断必须大于等于 最低提现金额
             *      2、获取商家发放的总红包金额（e04_redenvelopes_grant_record） 且必须为 非免费的
             *      3、根据发放总红包金额筛选出服务费率（f10_withdrawal_rule）
             *      4、拿筛选出服务费率计算服务费，筛选结果如果没有，则取初始费率
             *
             */
            List<F09WithdrawalSetDO> listWithdrawalSet = shopWithdrawalSetService.listWithdrawalSet(null);
            if (CollectionUtils.isEmpty(listWithdrawalSet)){
                throw new JsonException(Constants.$Null, "提现规则为空");
            }
            //1、判断是否达到提现标准
            F09WithdrawalSetDO withdrawalSet = listWithdrawalSet.get(0);
            if (withdrawalApplication.getWithdrawalMoney().compareTo(withdrawalSet.getLowerLimit()) == -1){
                throw new JsonException(Constants.Error, "未达到最低提现金额");
            }
            //服务费比例
            BigDecimal serviceRatio = withdrawalSet.getServiceRatio();
            withdrawalApplication.setOriginalServiceRatio(serviceRatio);
            EntityWrapper<E04RedenvelopesGrantRecordDO> ew = new EntityWrapper<>();
            ew.eq("shop_id",shopUser.getShopId()).eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
            List<E04RedenvelopesGrantRecordDO> listRedenvelopesGrantRecord = shopRedenvelopesGrantRecordService.listRedenvelopesGrantRecordBy(ew);
            if (!CollectionUtils.isEmpty(listRedenvelopesGrantRecord)){
                EntityWrapper<E01RedenvelopesStreetDO> redew = new EntityWrapper<>();
                redew.in("id",listRedenvelopesGrantRecord.stream().map(d -> d.getStreetId()).collect(Collectors.toList()));
                redew.eq("delete_flag",StatusConstant.ExamineStatus.un_examine.getCode());
                redew.eq("is_free",StatusConstant.FreeStatus.no.getCode());
                List<E01RedenvelopesStreetDO> listRedenvelopesStreet = shopRedenvelopesStreetService.listRedenvelopesStreetBy(redew);
                if (!CollectionUtils.isEmpty(listRedenvelopesStreet)){
                    //2、获取商家发放的总红包金额
                    BigDecimal totalOrderPrice = listRedenvelopesStreet.stream().map(d -> d.getMoney() == null ? BigDecimal.ZERO : d.getMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    //3、根据发放总红包金额筛选出服务费率
                    EntityWrapper<F10WithdrawalRuleDO> wrew = new EntityWrapper<>();
                    //下限（大于等于)
                    wrew.ge("lower_limit",totalOrderPrice);
                    //上限（小于）
                    wrew.lt("upper_limit",totalOrderPrice);
                    List<F10WithdrawalRuleDO> listWithdrawalRule = shopWithdrawalRuleService.listWithdrawalRuleBy(wrew);
                    if (!CollectionUtils.isEmpty(listWithdrawalRule)){
                        serviceRatio = listWithdrawalRule.get(0).getServiceRatio();
                    }
                }
            }
            //4、拿筛选出服务费率计算服务费
            BigDecimal serviceMoney = withdrawalApplication.getWithdrawalMoney().multiply(serviceRatio).divide(new BigDecimal(100),2,BigDecimal.ROUND_DOWN);
            withdrawalApplication.setServiceMoney(serviceMoney);
            withdrawalApplication.setActualServiceRatio(serviceRatio);
            withdrawalApplication.setActualWithdrawalMoney(withdrawalApplication.getWithdrawalMoney().subtract(serviceMoney));
            withdrawalApplication.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            withdrawalApplication.setApplyTime(LocalDateTime.now());
            return withdrawalApplicationDao.insert(withdrawalApplication);
        }
        //编辑
        withdrawalApplication.setExamineTime(LocalDateTime.now());
        return withdrawalApplicationDao.updateById(withdrawalApplication);
    }
    public Integer save(F06WithdrawalApplicationDO withdrawalApplication) {
        if (withdrawalApplication == null){
            return 0;
        }
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }

        List<F09WithdrawalSetDO> listWithdrawalSet = shopWithdrawalSetService.listWithdrawalSet(null);
        if (CollectionUtils.isEmpty(listWithdrawalSet)){
            throw new JsonException(Constants.$Null, "提现规则为空");
        }
        //1、判断是否达到提现标准
        F09WithdrawalSetDO withdrawalSet = listWithdrawalSet.get(0);
        if (withdrawalApplication.getWithdrawalMoney().compareTo(withdrawalSet.getLowerLimit()) == -1){
            throw new JsonException(Constants.Error, "未达到最低提现金额");
        }
        withdrawalApplication.setOrderNo(NoUtil.createNo(withdrawalApplication.getAccountId(), TypeConstant.withdrawal_application.getCode()));
         withdrawalApplicationDao.insert(withdrawalApplication);
        //修改账号可提现金额
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(withdrawalApplication.getAccountId());
        if (f05ShopAccountDO == null){
            throw new JsonException(Constants.Error, "账户不存在");
        }
        //提现中金额添加提现金额
        BigDecimal add = f05ShopAccountDO.getInWithdrawMoney().add(withdrawalApplication.getWithdrawalMoney());
        f05ShopAccountDO.setInWithdrawMoney(add);
        //可提现金额和可用余额减去提现金额
//        BigDecimal subtract = f05ShopAccountDO.getAvailableBalance().subtract(withdrawalApplication.getWithdrawalMoney());
//        f05ShopAccountDO.setAvailableBalance(subtract);
        BigDecimal subtract1= f05ShopAccountDO.getAvailableWithdrawMoney().subtract(withdrawalApplication.getWithdrawalMoney());
        f05ShopAccountDO.setAvailableWithdrawMoney(subtract1);
        shopAccountDao.updateById(f05ShopAccountDO);
        return 1;

    }
}


