package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.PasswordUtils;
import com.youqiancheng.form.client.my.PayBalanceForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.B02UserAccountClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
@Transactional
public class B02UserAccountClientServiceImpl implements B02UserAccountClientService {
    @Autowired
    private B02UserAccountDao b02UserAccountDao;
    @Autowired
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Autowired
    private B01UserDao b01UserDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;
    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private D06PayOrderDao d06PayOrderDao;
    @Autowired
    private B08PaySetDao b08PaySetDao;
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

    @Override
    public int payByBalance(PayBalanceForm form) {
        //校验用户信息
        B01UserDO b01UserDO = b01UserDao.get(form.getUserId());
        if(b01UserDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户信息不存在");
        }
        //校验支付信息
        List<B08PaySetDO> b08PaySetDOS = b08PaySetDao.selectList(
                new EntityWrapper<B08PaySetDO>()
                .eq("user_id",form.getUserId())
                .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
        );
        if(CollectionUtils.isEmpty(b08PaySetDOS)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付密码信息不存在");
        }
        B08PaySetDO b08PaySetDO = b08PaySetDOS.get(0);
        if(!b08PaySetDO.getPayPwd().equals(PasswordUtils.authAdminPwd(form.getPayPwd()))){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付密码错误");
        }

        //校验订单信息
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(form.getOrderId());
        if(d06PayOrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单信息不存在");
        }
//        if(d06PayOrderDO.getOrderPrice().compareTo(form.getMoney())!=0){
//            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付金额不等于订单金额");
//        }
        //校验用户余额
        List<B02UserAccountDO> b02UserAccountDOS = b02UserAccountDao.selectList(
                new EntityWrapper<B02UserAccountDO>()
                .eq("user_id",form.getUserId())
                .eq("country_id",1)
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
        );
        if(CollectionUtils.isEmpty(b02UserAccountDOS)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户余额信息不存在");
        }
        B02UserAccountDO b02UserAccountDO = b02UserAccountDOS.get(0);
        if(b02UserAccountDO.getAccountBalance().compareTo(form.getMoney())<0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"余额不足");
        }
        //修改订单状态——商家订单，订单明细状态
        d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
        d06PayOrderDO.setPayType(StatusConstant.payType.Balance.getCode());
        d06PayOrderDO.setPayTime(LocalDateTime.now());
        d06PayOrderDao.updateById(d06PayOrderDO);
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                        .eq("pay_order_id",d06PayOrderDO.getId())
        );
        for (D01OrderDO d01OrderDO : d01OrderDOS) {
            d01OrderDO.setPayTime(LocalDateTime.now());
            d01OrderDO.setPayType(StatusConstant.payType.Balance.getCode());
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
        }
        d01OrderDao.updateList(d01OrderDOS);

        List<D02OrderItemDO> itemList = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("pay_order_id",d06PayOrderDO.getId())
        );
        for (D02OrderItemDO d02OrderItemDO : itemList) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
            C01GoodsDO c01GoodsDO = c01GoodsDao.get(d02OrderItemDO.getGoodsId());
            c01GoodsDO.setSaleNum(c01GoodsDO.getSaleNum()+d02OrderItemDO.getNum());
            c01GoodsDao.updateById(c01GoodsDO);
        }
        d02OrderItemDao.updateList(itemList);

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

        //修改用户余额
        b02UserAccountDO.setAccountBalance(b02UserAccountDO.getAccountBalance().subtract(form.getMoney()));
        b02UserAccountDao.updateById(b02UserAccountDO);
        return 1;
    }
}
