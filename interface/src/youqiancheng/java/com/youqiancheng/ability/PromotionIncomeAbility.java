package com.youqiancheng.ability;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.B11InvitationRecordAppService;
import com.youqiancheng.util.CollectionTools;
import com.youqiancheng.util.QueryMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class PromotionIncomeAbility {

    @Autowired
    private B11InvitationRecordDao b11InvitationRecordDao;
    @Autowired
    private B01UserDao b01UserDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Autowired
    private F17PromotionIncomeDao f17PromotionIncomeDao;

    @Autowired
    private B13PromotionFlowDao b13PromotionFlowDao;

    @Autowired
    private B12PromotionAccountDao b12PromotionAccountDao;

    //支付宝税点
    private BigDecimal alipay_shuilv = new BigDecimal("0.006");
    //有钱城平台提现点
    private BigDecimal yqc_koudian = new BigDecimal("0.02");
    //有钱城平台服务费点
    private BigDecimal yqc_fuwufei = new BigDecimal("0.06");
    //推广用户10%提出
    private BigDecimal tuiguang_tidian = new BigDecimal("0.1");
    //劳务费率
    private BigDecimal laowu_feilv = new BigDecimal("0.08");



    BigDecimal hasWithdrawMoney = new BigDecimal("0.00");
    BigDecimal aliPayTaxes = new BigDecimal("0.00");
    BigDecimal yqcMoney = new BigDecimal("0.00");
    BigDecimal userPromotionExpenses = new BigDecimal("0.00");
    BigDecimal userAfterTax = new BigDecimal("0.00");
    BigDecimal userActualAmount = new BigDecimal("0.00");
    //插入推广收益记录
    public void addPromotionIncome(Long userId){
        //1、查询邀请记录
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId", userId);
        List<B11InvitationRecordDO> list = b11InvitationRecordDao.list(map);
        if(list.size()>0){
            //2、查询被邀请者信息
            List<Long> beUserIds = CollectionTools.collectList(list, B11InvitationRecordDO::getBeUserId);
            List<B01UserDO> b01UserDOS = b01UserDao.listUserByUserIds(beUserIds);
            //3、计算推广邀请收益
            calculatePromotionIncome(b01UserDOS,userId);
        }
    }

    private void calculatePromotionIncome(List<B01UserDO> b01UserDOS,Long userId){
        //1、判断每位用户是否已开通店铺
        for (B01UserDO b01User:b01UserDOS){
            if(b01User.getIsShop()==2){
                //2、已开通店铺的开始计算推广收入
                F05ShopAccountDO shopAccount = f05ShopAccountDao.getShopAccountById(b01User.getShopId());
                //店铺累计提现收益金额
                BigDecimal hasWithdrawMoney = shopAccount.getAvailableWithdrawMoney().add(shopAccount.getInWithdrawMoney());//可提现金额+已提现金额
                BigDecimal calculatedAmount = new BigDecimal(0);
                if(hasWithdrawMoney.intValue()!=0){
                    //以计算过的收益
                    F17PromotionIncomeDO f17PromotionIncomeDO = new F17PromotionIncomeDO();
                    f17PromotionIncomeDO.setShopId(shopAccount.getShopId());
                    F17PromotionIncomeDO f17PromotionIncomeDO1 = f17PromotionIncomeDao.selectOne(f17PromotionIncomeDO);

                    if(f17PromotionIncomeDO1 !=null){
                         calculatedAmount = DecimalUtil.subTionBigMal(hasWithdrawMoney, f17PromotionIncomeDO1.getAvailableWithdrawMoney(), 2, null);
                        if(calculatedAmount.intValue() == 0){
                            return;
                        }
                    }

                    //1、支付宝扣0.6%的金额
                    BigDecimal aliPayTaxes = DecimalUtil.BigMalMultBigMal(calculatedAmount, alipay_shuilv, 2, null);
                    //2、有钱城平台利润
                    BigDecimal bigDecimal = DecimalUtil.BigMalMultBigMal(calculatedAmount, yqc_koudian, 2, null);
                    BigDecimal yqcMoney = DecimalUtil.subTionBigMal(bigDecimal, aliPayTaxes, 2, null);//平台扣点金额-支付宝扣点金额=平台提现金额
                    //3、有钱城平台净利润 = 有钱城平台利润-平台服务费
                    BigDecimal bigDecimal1 = DecimalUtil.BigMalMultBigMal(yqcMoney, yqc_fuwufei, 2, null);
                    BigDecimal userPromotionExpenses = DecimalUtil.subTionBigMal(yqcMoney, bigDecimal1, 2, null);
                    //4、用户推广收入
                    BigDecimal userAfterTax = DecimalUtil.BigMalMultBigMal(userPromotionExpenses, tuiguang_tidian, 2, null);
                    //5、用户最终收入 = 用户推广收入-劳务手续费
                    BigDecimal bigDecimal3 = DecimalUtil.BigMalMultBigMal(userAfterTax, laowu_feilv, 2, null);
                    BigDecimal userActualAmount1 = DecimalUtil.subTionBigMal(userAfterTax, bigDecimal3, 2, null);
                    userActualAmount = DecimalUtil.addBigMall(userActualAmount,userActualAmount1,2,0);
                    addF17promotionIncome(hasWithdrawMoney,aliPayTaxes,yqcMoney,userPromotionExpenses,userAfterTax,userActualAmount,b01User.getShopId(),userId);
                }else{
                    addF17promotionIncome(hasWithdrawMoney,aliPayTaxes,yqcMoney,userPromotionExpenses,userAfterTax,userActualAmount,b01User.getShopId(),userId);
                }
            }else{
                addF17promotionIncome(hasWithdrawMoney,aliPayTaxes,yqcMoney,userPromotionExpenses,userAfterTax,userActualAmount,b01User.getShopId(),userId);
            }
        }
    }

    public void addF17promotionIncome(BigDecimal hasWithdrawMoney,BigDecimal aliPayTaxes,BigDecimal yqcMoney,
                                      BigDecimal userPromotionExpenses,BigDecimal userAfterTax,BigDecimal userActualAmount,Long shopId,Long userId){
        EntityWrapper<F17PromotionIncomeDO> ew = new EntityWrapper<>();
        ew.eq("shop_id",shopId);
        List<F17PromotionIncomeDO> f17PromotionIncomeDOS = f17PromotionIncomeDao.selectList(ew);
        if(f17PromotionIncomeDOS.size() == 0) {
            F17PromotionIncomeDO f17PromotionIncomeDO = new F17PromotionIncomeDO();
            f17PromotionIncomeDO.setAvailableWithdrawMoney(hasWithdrawMoney);
            f17PromotionIncomeDO.setAlipayTaxes(aliPayTaxes);
            f17PromotionIncomeDO.setYqcMoney(yqcMoney);
            f17PromotionIncomeDO.setUserPromotionExpenses(userPromotionExpenses);
            f17PromotionIncomeDO.setUserAfterTax(userAfterTax);
            f17PromotionIncomeDO.setUserActualAmount(userActualAmount);
            f17PromotionIncomeDO.setShopId(shopId);
            f17PromotionIncomeDO.setCreateTime(LocalDateTime.now());
            f17PromotionIncomeDO.setUpdateTime(LocalDateTime.now());
            f17PromotionIncomeDO.setUserId(userId);
            f17PromotionIncomeDao.insert(f17PromotionIncomeDO);
        }else{
            F17PromotionIncomeDO f17PromotionIncomeDO = f17PromotionIncomeDOS.get(0);
            f17PromotionIncomeDO.setAvailableWithdrawMoney(hasWithdrawMoney);
            f17PromotionIncomeDO.setAlipayTaxes(aliPayTaxes);
            f17PromotionIncomeDO.setYqcMoney(yqcMoney);
            f17PromotionIncomeDO.setUserPromotionExpenses(userPromotionExpenses);
            f17PromotionIncomeDO.setUserAfterTax(userAfterTax);
            f17PromotionIncomeDO.setUserActualAmount(userActualAmount);
            f17PromotionIncomeDO.setShopId(shopId);
            f17PromotionIncomeDO.setUpdateTime(LocalDateTime.now());
            f17PromotionIncomeDao.updateById(f17PromotionIncomeDO);
        }


        //查询已提现金额  增加用户推广收入账户金额
        List<B12PromotionAccountDO> promotionAccountDO = b12PromotionAccountDao.selectList(new EntityWrapper<B12PromotionAccountDO>().eq("user_id", userId));
        if(promotionAccountDO.size()>0){
            B12PromotionAccountDO b12PromotionAccountDO = promotionAccountDO.get(0);
            BigDecimal accumulatedRevenue = b12PromotionAccountDO.getAccumulatedRevenue();
            if(accumulatedRevenue == null){
                accumulatedRevenue = new BigDecimal("0.00");
            }
            //计算用户可提现余额
            BigDecimal bigDecimal = DecimalUtil.subTionBigMal(userActualAmount, accumulatedRevenue, 2, null);
            b12PromotionAccountDO.setAccountBalance(bigDecimal);
            b12PromotionAccountDO.setEditTime(LocalDateTime.now());
            b12PromotionAccountDao.updateById(b12PromotionAccountDO);
        }else{
            B12PromotionAccountDO b12PromotionAccountDO = new B12PromotionAccountDO();
            b12PromotionAccountDO.setUserId(userId);
            b12PromotionAccountDO.setAccountBalance(userActualAmount);
            b12PromotionAccountDO.setCreateTime(LocalDateTime.now());
            b12PromotionAccountDao.insert(b12PromotionAccountDO);
        }

        //增加用户推广收入金额流水
//        List<B13PromotionFlowDO> b13PromotionFlowDOS = b13PromotionFlowDao.selectList(new EntityWrapper<B13PromotionFlowDO>().eq("user_id", userId).orderBy("id", false));
//        B13PromotionFlowDO b13PromotionFlowDO = new B13PromotionFlowDO();
//        b13PromotionFlowDO.setUserId(userId);
//        if(b13PromotionFlowDOS.size()>0){
//            B13PromotionFlowDO b13PromotionFlowDO1 = b13PromotionFlowDOS.get(0);
//            b13PromotionFlowDO1.getRemainingAmount();
//            //BigDecimal userPromotionExpenses = DecimalUtil.subTionBigMal(userActualAmount, bigDecimal1, 2, null);
//        }
//        //b13PromotionFlowDO.setFlowAmount();
//       // b13PromotionFlowDao.
    }

}
