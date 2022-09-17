package com.youqiancheng.service.app.serviceimpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.form.app.B02AppPayBalanceForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.B02UserAccountAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
public class B02UserAccountAppServiceImpl implements B02UserAccountAppService {
    @Autowired
    private B02UserAccountDao b02UserAccountDao;
    @Autowired
    private  B01UserDao b01UserDao;
    @Autowired
    private B08PaySetDao b08PaySetDao;
    @Autowired
    private D06PayOrderDao d06PayOrderDao;
    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private  D02OrderItemDao d02OrderItemDao;
    @Autowired
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    @Autowired
    private B12PromotionAccountDao b12PromotionAccountDao;

    @Autowired
    private C01GoodsDao c01GoodsDao;

    @Override
    public B02UserAccountDO get(Long id){
        return b02UserAccountDao.get(id);
    }


    @Override
    public List<B02UserAccountDO> list(Map<String, Object> map) {
        return b02UserAccountDao.list(map);
    }

  @Override
    public List<B02UserAccountDO> listHDPage(Map<String, Object> map) {
        return b02UserAccountDao.listHDPage(map);
    }



    @Override
    public int count(Map<String, Object> map){
        return b02UserAccountDao.count(map);
    }


    @Override
    public int insert(B02UserAccountDO b02UserAccount) {
        return b02UserAccountDao.insert(b02UserAccount);
    }


    @Override
    public int insertBatch(List<B02UserAccountDO> b02UserAccounts){
        return b02UserAccountDao.insertBatch(b02UserAccounts);
    }


    @Override
    public int update(B02UserAccountDO b02UserAccount) {
        b02UserAccount.setUpdateTime(LocalDateTime.now());
        return b02UserAccountDao.updateById(b02UserAccount);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return b02UserAccountDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return b02UserAccountDao.updateStatus(param);
        }

    //获取账户余额
    @Override
    public BigDecimal getAccountBalanceByUserId(Long id) {
        QueryMap map =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("countryId",1);
        map.put("userId",id);
         List<B02UserAccountDO>  user = b02UserAccountDao.list(map);
         if(CollectionUtils.isEmpty(user)){
             throw  new JsonException(ResultEnum.DATA_NOT_EXIST," 没有相应的数据");
        }
        return  user.get(0).getAccountBalance();
    }
    //余额支付
    @Override
    public int payByUserBalance(B02AppPayBalanceForm from) {
        //用户来支付,先判断用户存在不存在
        //其实他都能来支付.此用户肯定存在,在这判断就没意义
       B01UserDO b01UserDO= b01UserDao.get(from.getUserId());
       if(b01UserDO==null){
          throw    new JsonException(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");

       }
       //因为要支付,肯定有支付设置,先校验此用户在支付设置表面吗,还有是否已删除?
        List<B08PaySetDO> b08PaySetDOS = b08PaySetDao.selectList(
                new EntityWrapper<B08PaySetDO>()
                        .eq("user_id",from.getUserId())
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
        );
       //在用户存在并且没删除的情况下,在去验证用户的支付密码
        if(CollectionUtils.isEmpty(b08PaySetDOS)){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"支付密码信息不存在");
        }
        B08PaySetDO b08PaySetDO = b08PaySetDOS.get(0);
        //判断支付密码的
        if(!b08PaySetDO.getPayPwd().equals(PasswordUtils.authAdminPwd(from.getPayPwd()))){
            throw  new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"支付密码错误");
        }
        /*
        * 继续校验订单信息,由于没支付,所以应该是校验支付订单的信息
        * 先判断下存在不,存在,在去验证订单的金额与余的是否一致
        * 再判断支付余额是否大于订单金额,假如小于的话,提示你的余额不足.请冲钱
        * 用户已付钱后,订单的状态也该从未支付 改为已支付了
        * 所以修改支付订单状态.
        * */
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(from.getPayOrderId());
        if(d06PayOrderDO==null){
            throw  new  JsonException(ResultEnum.DATA_NOT_EXIST,"订单信息不存在");
        }
//        if(d06PayOrderDO.getOrderPrice().compareTo(from.getMoney())!=0){
//            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"支付金额与订单金额不符");
//        }
        //在判断之前,先校验用户账户信息,然后获取余额
        List<B02UserAccountDO> b02UserAccountDOS = b02UserAccountDao.selectList(
                new EntityWrapper<B02UserAccountDO>()
                        //判断用户id,用户删除状态,用户是哪个国家的:1 代表中国
                .eq("user_id",from.getUserId())
                .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
                .eq("country_id", 1)
        );
       if(CollectionUtils.isEmpty(b02UserAccountDOS)){
           throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"用户账户余额信息不存在");
       }
       if(b02UserAccountDOS.get(0).getAccountBalance().compareTo(from.getMoney())<0){
           throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"亲,余额不足,请充值哦!");
       }
       //修改支付订单状态,就的修改商家订单状态,也的修改订单明细的订单状态
        d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
        d06PayOrderDO.setPayTime(LocalDateTime.now());
        d06PayOrderDO.setPayType(StatusConstant.payType.Balance.getCode());
        int i=d06PayOrderDao.updateById(d06PayOrderDO);
        if(i<0){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"修改订单状态失败");
        }

        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("payOrderId",d06PayOrderDO.getId());
        List<D01OrderDO> d01OrderDOList =d01OrderDao.list(map);
        if(d01OrderDOList!=null&&!d01OrderDOList.isEmpty()){
            for (D01OrderDO d01:d01OrderDOList
                 ) {
                d01.setOrderStatus(d06PayOrderDO.getOrderStatus());
                d01.setPayTime(LocalDateTime.now());
                d01.setPayType(StatusConstant.payType.Balance.getCode());
                //在这里面修改订单明细的状态
                QueryMap map2 = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map2.put("orderId",d01.getId());
                List<D02OrderItemDO> d02OrderItemlist = d02OrderItemDao.list(map2);

                for (D02OrderItemDO d02:d02OrderItemlist
                     ) {
                    //改订单明细的状态
                    d02.setOrderStatus(d01.getOrderStatus());
                    //添加销售量
                    C01GoodsDO c01GoodsDO = c01GoodsDao.get(d02.getGoodsId());
                    c01GoodsDO.setSaleNum(c01GoodsDO.getSaleNum()+d02.getNum());
                    c01GoodsDao.updateById(c01GoodsDO);
                }
                //修改全部订单明细状态
               int n= d02OrderItemDao.updateList(d02OrderItemlist);
                if(n<0){
                    throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"修改订单明细状态失败");
                }
            }
            //修改全部商家订单状态
            int j=  d01OrderDao.updateList(d01OrderDOList);
            if(j<0){
                throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"修改商家订单状态失败");
            }
        }

        //全部修改完后,要把用户的余额扣掉他消费的
        B02UserAccountDO b02UserAccountDO = b02UserAccountDOS.get(0);
        //新增用户账户流水
        B03UserAccountFlowDO entity=new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        entity.setOriginalMoney(b02UserAccountDO.getAccountBalance());
        entity.setMoney(d06PayOrderDO.getOrderPrice());
        entity.setType(TypeConstant.UserAccountType.buy_pay.getCode());
        BigDecimal finalMoney=b02UserAccountDO.getAccountBalance().subtract(d06PayOrderDO.getOrderPrice());
        entity.setFinalMoney(finalMoney);
        b03UserAccountFlowDao.insert(entity);
        b02UserAccountDO.setAccountBalance(b02UserAccountDO.getAccountBalance().subtract(from.getMoney()));
        b02UserAccountDao.updateById(b02UserAccountDO);
        return 1;
    }

    @Override
    @Transactional
    public Boolean saveWithdrawalApplication(F06WithdrawalApplicationDO f06WithdrawalApplicationDO,BigDecimal big,B02UserAccountDO userAccount) {
        //更改用户钱包的钱
        //userAccount.setAccountBalance(big);
        userAccount.setWithdrawalBalance(big);
        b02UserAccountDao.updateById(userAccount);
        //增加用户提现申请
        withdrawalApplicationDao.insert(f06WithdrawalApplicationDO);
        return true;
    }

    @Override
    public List<F06WithdrawalApplicationDO> selectUserWithdrawalNumber(Long accountId) {
        return withdrawalApplicationDao.selectUserWithdrawalNumber(accountId);
    }

    @Override
    @Transactional
    public Boolean saveInvitationWithdrawalApplication(F06WithdrawalApplicationDO f06WithdrawalApplicationDO,BigDecimal big,BigDecimal withdrawalMoney,B12PromotionAccountDO promotionAccount) {
        //更改用户钱包的钱
        promotionAccount.setAccountBalance(big);
        //计算累计提现金额
        BigDecimal bigDecimal = DecimalUtil.addBigMall(promotionAccount.getAccumulatedRevenue(), withdrawalMoney, 2, null);
        promotionAccount.setAccumulatedRevenue(bigDecimal);
        promotionAccount.setEditTime(LocalDateTime.now());
        b12PromotionAccountDao.updateById(promotionAccount);
        //增加用户提现申请
        withdrawalApplicationDao.insert(f06WithdrawalApplicationDO);
        return true;
    }

}
