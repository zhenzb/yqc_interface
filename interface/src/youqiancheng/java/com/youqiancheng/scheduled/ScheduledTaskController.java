package com.youqiancheng.scheduled;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DateUtil;
import com.handongkeji.util.TimeUtils;
import com.youqiancheng.ability.UserAccountFlowAbility;
import com.youqiancheng.controller.app.LogisticsController;
import com.youqiancheng.controller.websocket.GreetingController;
import com.youqiancheng.mybatis.dao.B02UserAccountDao;
import com.youqiancheng.mybatis.dao.B03UserAccountFlowDao;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.model.B02UserAccountDO;
import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.F05ShopAccountDO;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.service.shop.ShopAccountService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.client.D01OrderVO;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Zhenzhuobin
 * @Date: 2020/8/1 0001 09:45
 */
@Slf4j
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduledTaskController {

    @Autowired
    private ShopAccountService shopAccountService;
    @Resource
    private F05ShopAccountDao shopAccountDao;
    @Resource
    private D01OrderDao d01OrderDao;
    @Resource
    private B02UserAccountDao b02UserAccountDao;

    @Resource
    private B03UserAccountFlowDao b03UserAccountFlowDao;

    @Autowired
    private GreetingController greetingController;

    @Autowired
    private UserAccountFlowAbility userAccountFlowAbility;

    @Resource
    private LogisticsController logisticsController;

//    每隔5秒执行一次："*/5 * * * * ?"
//
//    每隔1分钟执行一次："0 */1 * * * ?"
//
//    每天23点执行一次："0 0 23 * * ?"
//
//    每天凌晨1点执行一次："0 0 1 * * ?"
//
//    每月1号凌晨1点执行一次："0 0 1 1 * ?"
//
//    每月最后一天23点执行一次："0 0 23 L * ?"
//
//    每周星期天凌晨1点实行一次："0 0 1 ? * L"
//
//    在26分、29分、33分执行一次："0 26,29,33 * * * ?"
//
//    每天的0点、13点、18点、21点都执行一次："0 0 0,13,18,21 * * ?"
//
//    表示在每月的1日的凌晨2点调度任务："0 0 2 1 * ? *"
//
//    表示周一到周五每天上午10：15执行作业："0 15 10 ? * MON-FRI"
//
//    表示2002-2006年的每个月的最后一个星期五上午10:15执行："0 15 10 ? 6L 2002-2006"


    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    //@Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void settlementWithdrawalAmount(){
        log.info("每天凌晨1点 商家账户昨日收益统计定时任务及定时结算可提现金额开始执行>>>>>>>>>>>>>>>>>>>>>>>>");
        //查询所有商户账户更新商户账户收益
        List<F05ShopAccountDO> list = shopAccountDao.list(new HashMap<>());
        for(F05ShopAccountDO f05ShopAccountDao:list){
            shopAccountService.updateShopAccount(f05ShopAccountDao.getShopId());
        }
        log.info("每天凌晨1点 商家账户昨日收益统计定时任务及定时结算可提现金额执行结束>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //系统自动更新15天未收货的订单改为已收货状态
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateD01Order(){
        log.info("每天凌晨2点 15天自动确认收货定时任务开始执行>>>>>>>>>>>>>>>>>>>>>>>>");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());//设置起时间
        cal.add(Calendar.DATE, -15);//计算15天前的时间
        String s = TimeUtils.dateMonth(cal.getTime());
        //查询15天前已发货待收货的订单
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(new EntityWrapper<D01OrderDO>()
                .eq("order_status", 3).le("send_time", s));
        for(D01OrderDO d01Order:d01OrderDOS){
            d01Order.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
            d01OrderDao.updateById(d01Order);
        }
        log.info("每天凌晨2点 15天自动确认收货定时任务结束执行>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Scheduled(cron = "0 0 3 * * ?") //每天凌晨3点执行一次
    //@Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    @Transactional
    public void updateUserAccount(){
        log.info("每天凌晨2点 用户红包经7天冻结期转可提现金额开始执行>>>>>>>>>>>>>>>>>>>>>>>>");
        //查询所有商户账户更新商户账户收益
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());//设置起时间
        cal.add(Calendar.DATE, -0);//计算15天前的时间
        String s = TimeUtils.dateMonth(cal.getTime());

        List<B03UserAccountFlowDO> b03UserAccountFlowDOS = b03UserAccountFlowDao.selectList(new EntityWrapper<B03UserAccountFlowDO>()
                .eq("status", 0).le("create_time", s).eq("type",1));

        //更新用户账户 减去余额 增加可提现金额
        for(B03UserAccountFlowDO userAccountFlow:b03UserAccountFlowDOS){
            BigDecimal money = userAccountFlow.getMoney();
            B02UserAccountDO userAccount = b02UserAccountDao.get(userAccountFlow.getAccountId());
            if(userAccount !=null){
                BigDecimal withdrawalBalance = userAccount.getWithdrawalBalance();
                BigDecimal accountBalance = userAccount.getAccountBalance();
                //可提现金额相加
                BigDecimal finalWithdrawalBalance = withdrawalBalance.add(money);
                //余额减少
                BigDecimal finalAccountBalance = accountBalance.subtract(money);
                if(finalAccountBalance.compareTo(new BigDecimal("0"))== -1 ){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"账号余额不能小于0");
                }
                //更新用户账户
                userAccount.setAccountBalance(finalAccountBalance);
                userAccount.setWithdrawalBalance(finalWithdrawalBalance);
                b02UserAccountDao.updateById(userAccount);
                //更新流水状态
                userAccountFlow.setStatus((short) 1);
                b03UserAccountFlowDao.updateById(userAccountFlow);
                //增加用户账号流水
                //userAccountFlowAbility.addUserAccountFlow(userAccount,money,TypeConstant.UserAccountType.buy_pay.getCode());
            }

        }
        log.info("每天凌晨2点 用户红包经7天冻结期转可提现金额执行结束>>>>>>>>>>>>>>>>>>>>>>>>");
    }


    // @Scheduled(cron = "0 0 2 * * ?") //每天凌晨1点执行一次
    @Scheduled(cron = "0 */20 * * * ?") //每分钟执行一次
    public void publicUserAccountStatus(){
        log.info("用户账户解封任务开始》》》》》》》》》》》》》》》》");
        //查询冻结三个月需要解封的资金
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());//设置起时间
        cal.add(Calendar.DATE, -0);//计算15天前的时间
        String s = TimeUtils.dateMonth(cal.getTime());
        //查询用户账户信息
        List<B02UserAccountDO> userAccountList = b02UserAccountDao.selectList(new EntityWrapper<B02UserAccountDO>()
                .eq("status",2)
                .le("update_time",s));
        for(B02UserAccountDO userAccountDO:userAccountList){
            userAccountDO.setStatus(1);
            b02UserAccountDao.updateById(userAccountDO);
        }
        log.info("用户账户解封任务结束》》》》》》》》》》》》》》》》");
    }

    /**
     * 定时每分钟查询客服未读消息
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void findUnreadCustomerServiceMessages(){
        greetingController.findUnRead();
    }

    /**
     * 清除物流Map
     */
    @Scheduled(cron = "0 0 4 * * ?") //每天凌晨3点执行一次
    public void clearOrderTranceMap(){
        logisticsController.clearMap();
    }

}
