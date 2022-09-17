package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.service.client.service.F05ShopAccountService;
import com.youqiancheng.service.shop.ShopWithdrawalSetService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.shop.WithdrawalParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class F05ShopAccountServiceImpl implements F05ShopAccountService {

    @Resource
    private F05ShopAccountDao shopAccountDao;
    @Resource
    private F09WithdrawalSetDao f09WithdrawalSetDao;
    @Autowired
    private E04RedenvelopesGrantRecordDao e04RedenvelopesGrantRecordDao;
    @Autowired
    private F10WithdrawalRuleDao f10WithdrawalRuleDao;

    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    @Resource
    private ShopWithdrawalSetService shopWithdrawalSetService;
    @Resource
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;

    @Override
    public List<F05ShopAccountDO> getShopAccount(Long id) {
        return shopAccountDao.getShopAccount(id);
    }   @Override
    public List<F05ShopAccountDO> getAccountByShopId(Long id) {

        List<F05ShopAccountDO> f05ShopAccountDOS = shopAccountDao.selectList(
                new EntityWrapper<F05ShopAccountDO>()
                .eq("shop_id",id)
        );
        return f05ShopAccountDOS;
    }
    @Override
    public  F05ShopAccountDO get(Long id) {
        return shopAccountDao.get(id);
    }


    @Override
    public WithdrawalParamVO getWithdrawalParam(Long id) {
        WithdrawalParamVO vo =new WithdrawalParamVO();
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(id);
        if(f05ShopAccountDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到账户信息");
        }
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

    @Override
    public Integer save(F06WithdrawalApplicationDO withdrawalApplication) {
        if (withdrawalApplication == null){
            return 0;
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
        //服务费比例
        //假如达到最低提现金额,把提现内容添加到提现申请表里
         withdrawalApplicationDao.insert(withdrawalApplication);

        //修改账号可提现金额
        F05ShopAccountDO f05ShopAccountDO = shopAccountDao.get(withdrawalApplication.getAccountId());
        if (f05ShopAccountDO == null){
            throw new JsonException(Constants.Error, "账户不存在");
        }
        //提现中金额添加提现金额
        BigDecimal add = f05ShopAccountDO.getInWithdrawMoney().add(withdrawalApplication.getWithdrawalMoney());
        f05ShopAccountDO.setInWithdrawMoney(add);
        //可提现金额减去提现金额
//        BigDecimal subtract = f05ShopAccountDO.getAvailableBalance().subtract(withdrawalApplication.getWithdrawalMoney());
//        f05ShopAccountDO.setAvailableBalance(subtract);
//        f05ShopAccountDO.setAccountBalance(subtract);
        BigDecimal subtract1= f05ShopAccountDO.getAvailableWithdrawMoney().subtract(withdrawalApplication.getWithdrawalMoney());
        f05ShopAccountDO.setAvailableWithdrawMoney(subtract1);
        shopAccountDao.updateById(f05ShopAccountDO);
        return 1;
    }

    @Override
    public List<F07ShopAccountFlowDO> getFlow(Map<String,Object> map) {
        List<F07ShopAccountFlowDO> f07ShopAccountFlowDOS = f07ShopAccountFlowDao.listHDPage(map);
        return f07ShopAccountFlowDOS;
    }
}
