package com.youqiancheng.service.client.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.D06PayOrderClientService;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Service
@Transactional
public class D06PayOrderClientServiceImpl implements D06PayOrderClientService {

    @Resource
    private D06PayOrderDao d06PayOrderDao;
    @Resource
    private D01OrderDao d01OrderDao;
    @Resource
    private D02OrderItemDao d02OrderItemDao;
    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Resource
    private A18SysMessageDao a18SysMessageDao;
    @Resource
    private D03CustomerServiceDao d03CustomerServiceDao;
    @Resource
    private F06WithdrawalApplicationDao f06WithdrawalApplicationDao;
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;

    @Override
    public D06PayOrderDO deductInventory(Long orderid) {
        //校验订单
        if(orderid==null||orderid==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空");
        }
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(orderid);
        if(d06PayOrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单不存在");
        }

        //查询订单明细
        List<D02OrderItemDO> itemList = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("pay_order_id", orderid)
        );
        if(CollectionUtils.isEmpty(itemList)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单明细不能为空");
        }
        //减库存
        for (D02OrderItemDO d02OrderItemDO : itemList) {

            C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(d02OrderItemDO.getSkuId());
            if (c09GoodsSkuDO == null) {
                //新增 查询面对面商品库存
                C01GoodsDO c01GoodsDO = c01GoodsDao.get(d02OrderItemDO.getGoodsId());
                if (c01GoodsDO == null) {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "未查询到库存数据，商品：" + d02OrderItemDO.getName());
                }
                if (c01GoodsDO.getStock() == 0) {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "库存不足,商品：" + d02OrderItemDO.getName());
                }
//                c01GoodsDO.setStock(c01GoodsDO.getStock()-1);
//                c01GoodsDO.setSaleNum(c01GoodsDO.getSaleNum()+1);
//                c01GoodsDO.setUpdateTime(LocalDateTime.now());
//                int i = c01GoodsDao.updateById(c01GoodsDO);
//                if (i <= 0) {
//                    throw new JsonException(ResultEnum.UPDATE_FAIL, "库存扣减失败,商品：" + d02OrderItemDO.getName());
//                }
                // throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到库存数据，商品："+d02OrderItemDO.getName());
            } else {
                if (c09GoodsSkuDO.getNum() < d02OrderItemDO.getNum()) {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "库存不足,商品：" + d02OrderItemDO.getName());
                }
                Map<String, Object> map = new HashMap<>();
                map.put("num", d02OrderItemDO.getNum());
                map.put("id", c09GoodsSkuDO.getId());
                int i = c09GoodsSkuDao.deductInventory(map);
                if (i <= 0) {
                    throw new JsonException(ResultEnum.UPDATE_FAIL, "库存扣减失败,商品：" + d02OrderItemDO.getName());
                }
            }
        }
        return d06PayOrderDO;
    }

    @Override
    public int addInventory(String orderNo) {
        List<D06PayOrderDO> d06PayOrderDOS = d06PayOrderDao.selectList(
                new EntityWrapper<D06PayOrderDO>()
                .eq("order_no",orderNo)
        );
        if(CollectionUtils.isEmpty(d06PayOrderDOS)){
            A18SysMessageDO dto=new A18SysMessageDO();
            dto.setContent("支付订单不存在");
            dto.setType(TypeConstant.NotifyType.ali.getCode());
            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
            dto.setOrderNo( orderNo);
            dto.setCreateTime( LocalDateTime.now());
            dto.setUpdateTime( LocalDateTime.now());
            a18SysMessageDao.insert(dto);
        }
        Long orderid = d06PayOrderDOS.get(0).getId();
        //查询订单明细
        List<D02OrderItemDO> itemList = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("pay_order_id", orderid)
        );
        if(CollectionUtils.isEmpty(itemList)){
            A18SysMessageDO dto=new A18SysMessageDO();
            dto.setContent("支付订单明细不存在");
            dto.setType(TypeConstant.NotifyType.ali.getCode());
            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
            dto.setOrderNo( orderNo);
            dto.setCreateTime( LocalDateTime.now());
            dto.setUpdateTime( LocalDateTime.now());
            a18SysMessageDao.insert(dto);
        }
        //减库存
        for (D02OrderItemDO d02OrderItemDO : itemList) {

            C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(d02OrderItemDO.getSkuId());
            if(c09GoodsSkuDO==null){
                A18SysMessageDO dto=new A18SysMessageDO();
                dto.setContent("未查询到库存数据;skuID:"+d02OrderItemDO.getSkuId());
                dto.setType(TypeConstant.NotifyType.ali.getCode());
                dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
                dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
                dto.setOrderNo(orderNo);
                dto.setCreateTime( LocalDateTime.now());
                dto.setUpdateTime( LocalDateTime.now());
                a18SysMessageDao.insert(dto);
            }
            Map<String,Object> map=new HashMap<>();
            map.put("num",d02OrderItemDO.getNum());
            map.put("id",c09GoodsSkuDO.getId());
            map.put("version",c09GoodsSkuDO.getVersion());
            int i = c09GoodsSkuDao.addInventory(map);
            if(i<=0){
                A18SysMessageDO dto=new A18SysMessageDO();
                dto.setContent("库存返还失败;skuID:"+d02OrderItemDO.getSkuId());
                dto.setType(TypeConstant.NotifyType.ali.getCode());
                dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
                dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
                dto.setOrderNo(orderNo);
                dto.setCreateTime( LocalDateTime.now());
                dto.setUpdateTime( LocalDateTime.now());
                a18SysMessageDao.insert(dto);
            }
        }
        return 1;
    }

    @Override
    public long updatePayStatus(String orderNo,String tradeNo,int type) {
        List<D06PayOrderDO> d06PayOrderDOS = d06PayOrderDao.selectList(
                new EntityWrapper<D06PayOrderDO>()
                        .eq("order_no",orderNo)
        );
        if(CollectionUtils.isEmpty(d06PayOrderDOS)){
            A18SysMessageDO dto=new A18SysMessageDO();
            dto.setContent("支付订单不存在");
            dto.setType(TypeConstant.NotifyType.ali.getCode());
            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
            dto.setOrderNo( orderNo);
            dto.setCreateTime( LocalDateTime.now());
            dto.setUpdateTime( LocalDateTime.now());
            a18SysMessageDao.insert(dto);
        }
        D06PayOrderDO d06PayOrderDO = d06PayOrderDOS.get(0);
        d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
        d06PayOrderDO.setPayType(type);
        d06PayOrderDO.setPayTime(LocalDateTime.now());
        d06PayOrderDO.setTradeNo(tradeNo);
        d06PayOrderDao.updateById(d06PayOrderDO);
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                .eq("pay_order_id",d06PayOrderDOS.get(0).getId())
        );
        for (D01OrderDO d01OrderDO : d01OrderDOS) {
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
            d01OrderDO.setPayType(type);
            d01OrderDO.setPayTime(LocalDateTime.now());
            d01OrderDO.setTradeNo(tradeNo);
        }
        d01OrderDao.updateList(d01OrderDOS);

        List<D02OrderItemDO> itemList = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("pay_order_id", d06PayOrderDOS.get(0).getId())
        );
        List<C01GoodsDO> glist=new ArrayList<>();
        for (D02OrderItemDO d02OrderItemDO : itemList) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
            C01GoodsDO c01GoodsDO = c01GoodsDao.get(d02OrderItemDO.getGoodsId());
            c01GoodsDO.setSaleNum(c01GoodsDO.getSaleNum()+d02OrderItemDO.getNum());
            glist.add(c01GoodsDO);
            //增加商户账户金额
//            F05ShopAccountDO shopAccountById = f05ShopAccountDao.getShopAccountById(d02OrderItemDO.getShopId());
//            BigDecimal accountBalance = shopAccountById.getAccountBalance();//商户账户总金额
//            BigDecimal bigDecimal =accountBalance.add(d02OrderItemDO.getTotalPrice());
           // BigDecimal bigDecimal = DecimalUtil.addBigMal(String.valueOf(accountBalance), String.valueOf(d02OrderItemDO.getTotalPrice()), 0, 0);//最新总金额
//            shopAccountById.setAccountBalance(bigDecimal);
//            shopAccountById.setAvailableBalance(bigDecimal);
//            f05ShopAccountDao.updateById(shopAccountById);
            //计入收益
            List<F15ShopProfitDO> f15ShopProfitDOS = f15ShopProfitDao.selectList(
                    new EntityWrapper<F15ShopProfitDO>()
                            .eq("shop_id",d02OrderItemDO.getShopId())

            );
            //判断是否存在商家台账，不存在增加，存在修改
//            if(CollectionUtils.isEmpty(f15ShopProfitDOS)){
//                F15ShopProfitDO dto=new F15ShopProfitDO();
//                dto.setDayIncome(d06PayOrderDO.getOrderPrice());
//                dto.setTotalIncome(d06PayOrderDO.getOrderPrice());
//                dto.setShopId(d02OrderItemDO.getShopId());
//                dto.setUpdateTime(LocalDateTime.now());
//                dto.setUpdatePerson(d06PayOrderDO.getUserPhone());
//                dto.setCreatePerson(d06PayOrderDO.getUserPhone());
//                dto.setCreateTime(LocalDateTime.now());
//                dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
//                f15ShopProfitDao.insert(dto);
//            }else{
//                F15ShopProfitDO f15ShopProfitDO = f15ShopProfitDOS.get(0);
//                f15ShopProfitDO.setDayIncome(f15ShopProfitDO.getDayIncome().add(d06PayOrderDO.getOrderPrice()));
//                f15ShopProfitDO.setTotalIncome(f15ShopProfitDO.getTotalIncome().add(d06PayOrderDO.getOrderPrice()));
//                f15ShopProfitDao.updateById(f15ShopProfitDO);
//            }
        }
        c01GoodsDao.updateList(glist);
        d02OrderItemDao.updateList(itemList);
        return d01OrderDOS.get(0).getShopId();

    }

    @Override
    public int updateCustomService(D03CustomerServiceDO customService) {
        return  d03CustomerServiceDao.updateById(customService);
    }

    @Override
    public int updateTransfer(F06WithdrawalApplicationDO transferAccountsInfo) {
        return f06WithdrawalApplicationDao.updateById(transferAccountsInfo);
    }

    @Override
    public D03CustomerServiceDO getCustomService(Long id) {
        D03CustomerServiceDO d03CustomerServiceDO = d03CustomerServiceDao.get(id);
        if(d03CustomerServiceDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后服务不存在");
        }
        //查售后表里的订单明细id,去查订单明细表,等到一个订单明细对象
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(d03CustomerServiceDO.getOrderItemId());
        if(d02OrderItemDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单明细不存在");
        }
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(d02OrderItemDO.getPayOrderId());
        if(d06PayOrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付订单不存在");
        }
        if(StringUtils.isBlank(d06PayOrderDO.getTradeNo())){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"支付流水号为空");
        }
        d03CustomerServiceDO.setTradeNo(d06PayOrderDO.getTradeNo());
        return d03CustomerServiceDO;
    }

    @Override
    public F06WithdrawalApplicationDO getTransferAccountsInfo(Long id) {
        F06WithdrawalApplicationDO f06WithdrawalApplicationDO = f06WithdrawalApplicationDao.get(id);
        return f06WithdrawalApplicationDO;
    }

    @Override
    public D06PayOrderDO getPayOrderByItemId(Long id) {
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(id);
        if(d02OrderItemDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"退款关联订单明细不存在");
        }
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(d02OrderItemDO.getPayOrderId());
        return d06PayOrderDO;
    }
    @Override
    public D06PayOrderDO get(Long id) {
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(id);
        return d06PayOrderDO;
    }
}
