package com.youqiancheng.service.client.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.CommonConstants;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.NoUtil;
import com.youqiancheng.ability.UserAccountFlowAbility;
import com.youqiancheng.controller.websocket.GreetingController;
import com.youqiancheng.form.client.D02OrderItemSaveForm;
import com.youqiancheng.form.client.D06PayOrderSearchForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.client.*;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Slf4j
@Service
@Transactional
public class D01OrderClientServiceImpl implements D01OrderClientService {
    @Resource
    private D01OrderDao d01OrderDao;
    @Resource
    private D02OrderItemDao d02OrderItemDao;
    @Resource
    private B04ShoppingCartDao b04ShoppingCartDao;
    @Resource
    private D06PayOrderDao d06PayOrderDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private A15MessageDao a15MessageDao;
    @Resource
    private A17MessageUserDao a17MessageUserDao;
    @Resource
    private F08ShopUserDao f08ShopUserDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private C09GoodsSkuDao  c09GoodsSkuDao;
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Resource
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Autowired
    private UserAccountFlowAbility userAccountFlowAbility;
    @Override
    public D01OrderClientVO get(Long id){
        D01OrderClientVO dto=new D01OrderClientVO();
        D01OrderDO d01OrderDO = d01OrderDao.get(id);
        if(d01OrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到订单数据");
        }
        BeanUtils.copyProperties(d01OrderDO,dto);
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",d01OrderDO.getId());
        List<D02OrderItemDO> list = d02OrderItemDao.list(map);
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到订单明细数据");
        }
        dto.setOrderItem(list);
        return dto;
    }
    @Override
    public D06PayOrderDO getPayOrderById(Long id) {
        return d06PayOrderDao.get(id);
    }

    @Override
    public int updatePayOrderById(D06PayOrderDO dto) {
        Integer integer = d06PayOrderDao.updateById(dto);
        //取消商家订单
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                        .eq("pay_order_id",dto.getId())

        );
        for (D01OrderDO d01OrderDO : d01OrderDOS) {
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
        }
        d01OrderDao.updateList(d01OrderDOS);
        //取消订单明细
        List<D02OrderItemDO> itemlist = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("pay_order_id", dto.getId())

        );
        for (D02OrderItemDO d02OrderItemDO : itemlist) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
        }
        d02OrderItemDao.updateList(itemlist);
        return integer;
    }

    @Override
    public int deleteByOrderId(Long id) {
        //1:先去查这个id对应的支付订单
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(id);
        if(d06PayOrderDO==null){
            throw new JsonException(ResultEnum.DELETE_FAIL,"支付订单不存在");
        }
        //修改支付订单状态
//        if(StatusConstant.OrderStatus.cancel.getCode()!=d06PayOrderDO.getOrderStatus()){
//            throw new JsonException(ResultEnum.DELETE_FAIL,"支付订单状态错误:只有已取消状态才可以删除");
//        }
        d06PayOrderDO.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        d06PayOrderDao.updateById(d06PayOrderDO);

        //2:通过支付订单的id去查商家订单表.是一个集合
        List<D01OrderDO> d01OrderList= d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                .eq("pay_order_id",id)
        );
        //修改商家订单
        for (D01OrderDO aDo : d01OrderList) {
//            if(StatusConstant.OrderStatus.cancel.getCode()!=aDo.getOrderStatus()){
//                throw new JsonException(ResultEnum.DELETE_FAIL,"商家订单状态错误:只有已取消状态才可以删除");
//            }
            aDo.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        }
        d01OrderDao.updateList(d01OrderList);

        //3:查询订单明细
        List<D02OrderItemDO> orderItmeByOrderId = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                .eq("pay_order_id",id)
        );
        //修改订单明细
        for (D02OrderItemDO aDoi : orderItmeByOrderId) {
            aDoi.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
        }
        d02OrderItemDao.updateList(orderItmeByOrderId);
        return  1;
    }


    @Override
    public List<D01OrderDO> listHDPage(Map<String, Object> map) {
        return d01OrderDao.listHDPage(map);
    }
    @Override
    public List<NewOrderVO> orderList(Map<String, Object> map) {
        List<NewOrderVO> vos=new ArrayList<>();
        //根据用户查询支付订单
        List<D06PayOrderDO> list = d06PayOrderDao.listHDPage(map);
        if(list!=null&&!list.isEmpty()){
            for (D06PayOrderDO d06PayOrder : list) {
                if(StatusConstant.OrderStatus.un_pay.getCode()==d06PayOrder.getOrderStatus()
                ||StatusConstant.OrderStatus.cancel.getCode()==d06PayOrder.getOrderStatus()) {
                    NewOrderVO vo=new NewOrderVO();
                    vo.setShopName(CommonConstants.PLATEFORM);
                    vo.setOrderId(d06PayOrder.getId());
                    vo.setCreateTime(d06PayOrder.getCreateTime());
                    vo.setOrderNo(d06PayOrder.getOrderNo());
                    vo.setOrderStatus(d06PayOrder.getOrderStatus());
                    vo.setOrderPrice(d06PayOrder.getOrderPrice());
                    vo.setSetOffFund(d06PayOrder.getSetOffFund());
                    //获取订单明细
                    List<D02OrderItemDO> itemList=new ArrayList<>();
                    //根据支付订单查询商家订单
                    QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                    map1.put("payOrderId",d06PayOrder.getId());
                    List<D01OrderDO> list1 = d01OrderDao.list(map1);
                    if(list1!=null&&!list1.isEmpty()){
                        for (D01OrderDO d01OrderDO : list1) {
                            //根据商家订单ID查询订单明细
                            QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                            map2.put("orderId",d01OrderDO.getId());
                            List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                            itemList.addAll(list2);
                        }
                        vo.setOrderItem(itemList);
                    }
                    vos.add(vo);
                }else{
                    //根据支付订单查询商家订单
                    QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                    map1.put("payOrderId",d06PayOrder.getId());
                    List<D01OrderDO> list1 = d01OrderDao.list(map1);
                    if(list1!=null&&!list1.isEmpty()){
                        for (D01OrderDO d01OrderDO : list1) {
                            NewOrderVO vo=new NewOrderVO();
                            vo.setOrderPrice(d01OrderDO.getOrderPrice());
                            vo.setOrderId(d01OrderDO.getId());
                            vo.setShopName(d01OrderDO.getShopName());
                            vo.setCreateTime(d01OrderDO.getCreateTime());
                            vo.setOrderNo(d01OrderDO.getOrderNo());
                            vo.setExpressCode(d01OrderDO.getExpressCode());
                            vo.setExpressName(d01OrderDO.getExpressName());
                            vo.setExpressNumber(d01OrderDO.getExpressNumber());
                            vo.setOrderStatus(d01OrderDO.getOrderStatus());
                            vo.setSetOffFund(d06PayOrder.getSetOffFund());
                            //根据商家订单ID查询订单明细
                            QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                            map2.put("orderId",d01OrderDO.getId());
                            List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                            vo.setOrderItem(list2);
                            vos.add(vo);
                        }
                    }
                }
            }
        }
        return vos;
    }
//    @Override
//    public List<OrderVO> orderList(Map<String, Object> map) {
//        List<OrderVO> vos=new ArrayList<>();
//        //根据用户查询支付订单
//        List<D06PayOrderDO> list = d06PayOrderDao.listHDPage(map);
//        if(list!=null&&!list.isEmpty()){
//            for (D06PayOrderDO d06PayOrder : list) {
//                OrderVO vo=new OrderVO();
//                vo.setOrderStatus(d06PayOrder.getOrderStatus());
//                List<D02OrderItemDO> itemList=new ArrayList<>();
//                List<D01OrderVO> soList=new ArrayList<>();
//                vo.setPayOrder(d06PayOrder);
//                //根据支付订单查询商家订单
//                QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                map1.put("payOrderId",d06PayOrder.getId());
//                List<D01OrderDO> list1 = d01OrderDao.list(map1);
//                if(list1!=null&&!list1.isEmpty()){
//                    for (D01OrderDO d01OrderDO : list1) {
//                        D01OrderVO ovo=new  D01OrderVO();
//                        BeanUtils.copyProperties(d01OrderDO,ovo);
//                        //根据商家订单ID查询订单明细
//                        QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                        map2.put("orderId",d01OrderDO.getId());
//                        List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
//                        ovo.setOrderItem(list2);
//                        itemList.addAll(list2);
//                        soList.add(ovo);
//                    }
//                    vo.setShopOrders(soList);
//                }
//                vo.setOrderItem(itemList);
//                vos.add(vo);
//            }
//        }
//        return vos;
//    }


    @Override
    public List<D01OrderVO> list(Map<String, Object> map) {
        List<D01OrderVO> voList=new ArrayList<>();
        List<D01OrderDO> list = d01OrderDao.listHDPage(map);
        if(list!=null&&!list.isEmpty()){
            for (D01OrderDO d01OrderDO : list) {
                D01OrderVO vo=new D01OrderVO();
                BeanUtils.copyProperties(d01OrderDO,vo);
                //根据商家订单ID查询订单明细
                QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map2.put("orderId",d01OrderDO.getId());
                map2.put("orderStatus",2);
                map2.put("orderStatus1",3);
                List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                vo.setOrderItem(list2);
                voList.add(vo);
            }

        }

        return voList;
    }

    @Override
    public List<D06PayOrderDO> listPayOrder(Map<String, Object> map) {
        //根据用户查询支付订单
        List<D06PayOrderDO> list = d06PayOrderDao.listHDPage(map);
        if(list!=null&&!list.isEmpty()){
            for (D06PayOrderDO d06PayOrder : list) {
                //根据支付订单查询商家订单
                QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map1.put("payOrderId",d06PayOrder.getId());
                List<D01OrderDO> list1 = d01OrderDao.list(map1);
                if(list1!=null&&!list1.isEmpty()){
                    for (D01OrderDO d01OrderDO : list1) {
                        //根据商家订单ID查询订单明细
                        QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                        map2.put("orderId",d01OrderDO.getId());
                        List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                        d06PayOrder.setOrderItem(list2);
                    }

                }
            }
        }
        return list;
    }


    @Override
    public int count(Map<String, Object> map){
        return d01OrderDao.count(map);
    }

    @Override
    public D06PayOrderDO saveFacePay(D06PayOrderSearchForm dto){
        //保存支付订单信息
        D06PayOrderDO d06Order=new D06PayOrderDO();
        BeanUtils.copyProperties(dto,d06Order);
        d06Order.setCreateTime(LocalDateTime.now());
        d06Order.setInvoiceType("999999");
        d06Order.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
        String no = NoUtil.createNo(dto.getUserId(), TypeConstant.face_pay.getCode());
        d06Order.setOrderNo(no);
        d06Order.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int insert = d06PayOrderDao.insert(d06Order);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"支付订单插入失败");
        }
        //保存商家订单
        D01OrderDO d01OrderDO=new D01OrderDO();

       // BeanUtils.copyProperties(dto,d01OrderDO);
        d01OrderDO.setShopId(dto.getShopId());
        d01OrderDO.setUserId(dto.getUserId());
        d01OrderDO.setStreetId(999999L);
        d01OrderDO.setTakeTime(LocalDateTime.now());
        d01OrderDO.setPayOrderId(d06Order.getId());
        d01OrderDO.setCreateTime(LocalDateTime.now());
        d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
        d01OrderDO.setDeliveryStatus(StatusConstant.DeliveryStatus.shipped.getCode());
        d01OrderDO.setOrderNo(no);
        d01OrderDO.setShopName(dto.getShopName());
        d01OrderDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        d01OrderDO.setOrderPrice(dto.getOrderPrice());
        Integer insert1 = d01OrderDao.insert(d01OrderDO);
        if(insert1<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"商家订单插入失败");
        }
        //保存订单明细
        D02OrderItemDO d02OrderItemDO=new D02OrderItemDO();
        d02OrderItemDO.setOrderId(d01OrderDO.getId());
        d02OrderItemDO.setPrice(dto.getOrderPrice());
        d02OrderItemDO.setShopName(dto.getShopName());
        d02OrderItemDO.setTotalPrice(dto.getOrderPrice());
        d02OrderItemDO.setPayOrderId(d06Order.getId());
        d02OrderItemDO.setIsEvaluate(StatusConstant.IsEvaluate.un_evaluated.getCode());
        d02OrderItemDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
        d02OrderItemDO.setCreatePerson(dto.getUserPhone());
        d02OrderItemDO.setUpdatePerson(dto.getUserPhone());
        d02OrderItemDO.setCreateTime(LocalDateTime.now());
        d02OrderItemDO.setUpdateTime(LocalDateTime.now());
        d02OrderItemDO.setShopId(dto.getShopId());
        int i = d02OrderItemDao.insert(d02OrderItemDO);
        if(i<=0){
            throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"订单明细插入失败");
        }
        return d06Order;
    }

    @Override
    public synchronized D06PayOrderDO save(D06PayOrderSearchForm dto) {
        //保存支付订单信息
        D06PayOrderDO d06Order=new D06PayOrderDO();
        BeanUtils.copyProperties(dto,d06Order);
       // if(dto.getOrderItem() !=null && dto.getOrderItem().get(0) !=null && dto.getOrderItem().get(0).getPrice() !=null){
            //根据sku 重新设置价格，前端传过来的有可能被恶意修改
            //总价钱
            BigDecimal orderPrice = new BigDecimal("0");
            //邮费
            BigDecimal postage = new BigDecimal("0");
            List<Long> goodsIdList = new ArrayList();
            List<D02OrderItemSaveForm> orderItem = dto.getOrderItem();
            int n = 1;
            for(D02OrderItemSaveForm item:orderItem){
                long skuId = item.getSkuId();
                if(9999999 == skuId){
                    //小程序下单
                    BigDecimal bangnifu = item.getBangnifu();
                    if(bangnifu.compareTo(new BigDecimal("0"))==1){
                        //查询用户可用流量
                        B02UserAccountDO userAccount = userAccountFlowAbility.getUserAccount(dto.getUserId());
                        BigDecimal withdrawalBalance = userAccount.getWithdrawalBalance();
                        BigDecimal subtract1 = withdrawalBalance.subtract(bangnifu);
                        BigDecimal subtract = withdrawalBalance.subtract(bangnifu);
                        if(subtract.compareTo(new BigDecimal("0")) == -1){
                            subtract = new BigDecimal("0");
                        }
                        if(subtract.compareTo(new BigDecimal("0")) == 0 || subtract.compareTo(new BigDecimal("0")) == 1){
                            userAccount.setWithdrawalBalance(subtract);
                            userAccountFlowAbility.updateUserAccount(userAccount);
                            if(subtract.compareTo(new BigDecimal("0")) == 0 && n>=2){
                                BigDecimal price = item.getPrice();
                                BigDecimal add = price.add(bangnifu);
                                item.setPrice(add);
                                orderPrice = orderPrice.add(add);
                                d06Order.setSetOffFund(item.getBangnifu());
                            }else if(subtract.compareTo(new BigDecimal("0")) == 1){
                                item.setPrice(item.getPrice());
                                orderPrice = orderPrice.add(item.getPrice().multiply(new BigDecimal(item.getNum())));
                                d06Order.setSetOffFund(item.getBangnifu());
                            }else{
                                int num = item.getNum();
                                if(num>1){
                                    for(int t=0;t<num;t++){
                                        item.setPrice(item.getPrice());
                                        if(t>0){
                                            BigDecimal price = item.getPrice();
                                            BigDecimal adds = price.add(bangnifu);
                                            item.setPrice(adds);
                                            orderPrice = orderPrice.add(adds);
                                            d06Order.setSetOffFund(item.getBangnifu());
                                        }else{
                                            orderPrice = orderPrice.add(item.getPrice());
                                            d06Order.setSetOffFund(item.getBangnifu());
                                        }

                                    }
                                }else{
                                    item.setPrice(item.getPrice());
                                    orderPrice = orderPrice.add(item.getPrice());
                                    d06Order.setSetOffFund(item.getBangnifu());
                                }

                            }

                        }else{
                            BigDecimal price = item.getPrice();
                            BigDecimal add = price.add(bangnifu);
                            item.setPrice(add);
                            orderPrice = orderPrice.add(add.multiply(new BigDecimal(item.getNum())));
                            d06Order.setSetOffFund(new BigDecimal("0"));
                        }
                    }else{
                        item.setPrice(item.getPrice());
                        orderPrice = orderPrice.add(item.getPrice().multiply(new BigDecimal(item.getNum())));
                        d06Order.setSetOffFund(item.getBangnifu());
                    }

                }else{
                    C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(item.getSkuId());
                    if(c09GoodsSkuDO == null){
                        //新增 查询面对面商品
                        C01GoodsDO c01GoodsDO = c01GoodsDao.get(item.getGoodsId());
                        item.setPrice(c01GoodsDO.getPrice());
                        orderPrice = orderPrice.add(c01GoodsDO.getPrice().multiply(new BigDecimal(item.getNum())));
                    }else{
                        item.setPrice(c09GoodsSkuDO.getGoodsPrice());
                        item.setInventory(c09GoodsSkuDO.getGoodsDesc());//商品规格
                        orderPrice = orderPrice.add(c09GoodsSkuDO.getGoodsPrice().multiply(new BigDecimal(item.getNum())));
                    }
                    if(!goodsIdList.contains(item.getGoodsId())){
                        goodsIdList.add(item.getGoodsId());
                        BigDecimal goodsPostage = item.getGoodsPostage()==null?new BigDecimal("0"):item.getGoodsPostage();
                        postage = postage.add(goodsPostage);
                    }
                }
                    n++;
            }
            d06Order.setOrderPrice(orderPrice.add(postage));
            goodsIdList.clear();
        //}
        d06Order.setCreateTime(LocalDateTime.now());
        d06Order.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
        String no = NoUtil.createNo(dto.getUserId(), TypeConstant.Order.getCode());
        d06Order.setOrderNo(no);
        d06Order.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int insert = d06PayOrderDao.insert(d06Order);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"支付订单插入失败");
        }
        //商品明细按照商家分组
        if(dto.getOrderItem()==null||dto.getOrderItem().isEmpty()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"订单明细不能为空");
        }
        List<Long> ids=new ArrayList<>();
        C01GoodsDO c01GoodsDO=null;
        Map<Long,List<D02OrderItemSaveForm>> map=new HashMap<>();
        for (D02OrderItemSaveForm formItem : dto.getOrderItem()) {
            //查询库存是否存在
            C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(formItem.getSkuId());
            if(c09GoodsSkuDO==null){
                //新增 查询面对面商品库存
                 c01GoodsDO = c01GoodsDao.get(formItem.getGoodsId());
                if(c01GoodsDO !=null){
                    int num = c01GoodsDO.getStock()-formItem.getNum(); //库存数
                    if(num <0){
                        throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "库存不足");
                    }
                    c01GoodsDO.setStock(num);
                    c01GoodsDao.updateById(c01GoodsDO);
                }else {
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST, "未查询到规格库存信息");
                }
            }else {
                if (c09GoodsSkuDO.getNum() < formItem.getNum()) {
                    throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL, "库存不足");
                } else {
                    int num = c09GoodsSkuDO.getNum() - formItem.getNum(); //库存数
                    c09GoodsSkuDO.setNum(num);
                    c09GoodsSkuDao.updateById(c09GoodsSkuDO);
                }
            }
            ids.add(formItem.getCartId());
            if(map.isEmpty()){
                List<D02OrderItemSaveForm> list=new ArrayList<>();
                list.add(formItem);
                map.put(formItem.getShopId(),list);
            }else{
                if(map.keySet().contains(formItem.getShopId())){
                    map.get(formItem.getShopId()).add(formItem);
                }else{
                    List<D02OrderItemSaveForm> list=new ArrayList<>();
                    list.add(formItem);
                    map.put(formItem.getShopId(),list);
                }
            }
        }
        //分组保存商家订单和明细

        for (Long aLong : map.keySet()) {
            D01OrderDO d01OrderDO=new D01OrderDO();
            if(c01GoodsDO !=null){
                dto.setShopId(dto.getOrderItem().get(0).getShopId());
            }
            if(dto.getShopId() == null){
                dto.setShopId(dto.getOrderItem().get(0).getShopId());
            }
            BeanUtils.copyProperties(dto,d01OrderDO);
            d01OrderDO.setShopId(aLong);
            d01OrderDO.setStreetId(dto.getOrderItem().get(0).getStreetId());
            d01OrderDO.setPayOrderId(d06Order.getId());
            d01OrderDO.setCreateTime(LocalDateTime.now());
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
            d01OrderDO.setDeliveryStatus(StatusConstant.DeliveryStatus.un_shipped.getCode());
            d01OrderDO.setOrderNo(no);
            d01OrderDO.setShopName(map.get(aLong).get(0).getShopName());
            d01OrderDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());

            BigDecimal money=new BigDecimal("0");
            //for (D02OrderItemSaveForm item : map.get(aLong)) {
//                if(null !=item.getTotalPrice()){
//                    money=money.add(item.getTotalPrice());
//                }else{
                    money=money.add(d06Order.getOrderPrice());
               // }

          //  }
            //快销街区的需要多付1.6%

            if(dto.getOrderItem().get(0).getStreetId()!=null && 201==dto.getOrderItem().get(0).getStreetId()){
                BigDecimal multiply = money.multiply(new BigDecimal("0.016"));

               money =  money.add(multiply);
            }
            d01OrderDO.setOrderPrice(money);
            Integer insert1 = d01OrderDao.insert(d01OrderDO);
            if(insert1<=0){
                throw new JsonException(ResultEnum.INSERT_FAIL,"商家订单插入失败");
            }

            List<D02OrderItemDO> list1=new ArrayList<>();
            for (D02OrderItemSaveForm item : map.get(aLong)) {
                D02OrderItemDO d02OrderItemDO=new D02OrderItemDO();
                BeanUtils.copyProperties(item,d02OrderItemDO);
                //if(item.getTotalPrice() == null){
                    d02OrderItemDO.setTotalPrice(d06Order.getOrderPrice());
                //}

                d02OrderItemDO.setOrderId(d01OrderDO.getId());
                d02OrderItemDO.setPayOrderId(d06Order.getId());
                d02OrderItemDO.setIsEvaluate(StatusConstant.IsEvaluate.un_evaluated.getCode());
                d02OrderItemDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
                d02OrderItemDO.setCreatePerson(dto.getUserPhone());
                d02OrderItemDO.setUpdatePerson(dto.getUserPhone());
                d02OrderItemDO.setCreateTime(LocalDateTime.now());
                d02OrderItemDO.setIcon(item.getGoodsIcon());
                d02OrderItemDO.setName(item.getGoodsName());
                d02OrderItemDO.setUpdateTime(LocalDateTime.now());
                list1.add(d02OrderItemDO);
            }
            int i = d02OrderItemDao.insertBatch(list1);
            if(i<=0){
                throw new JsonException(ResultEnum.BATCH_INSERT_FAIL,"订单明细插入失败");
            }
        }
        //b04ShoppingCartDao.deleteBatchIds(ids);
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("deleteFlag", StatusConstant.DeleteFlag.delete.getCode());
         b04ShoppingCartDao.deleteShoppingCart(param);
        return d06Order;
    }


    @Override
    public int insertBatch(List<D01OrderDO> d01Orders){
        return d01OrderDao.insertBatch(d01Orders);
    }


    @Override
    public int update(D01OrderDO d01Order) {
        return d01OrderDao.updateById(d01Order);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return d01OrderDao.updateStatus(param);
    }
    @Override
    public int cancelOrderByTime(Long id) {
        QueryMap  map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",id);
        map.put("orderStatus",StatusConstant.OrderStatus.un_pay.getCode());
        List<D06PayOrderDO> list = d06PayOrderDao.list(map);
        if(!CollectionUtils.isEmpty(list)){
            for (D06PayOrderDO d06PayOrderDO : list) {
                //校验是否支付时间过期
                //如果状态为待支付——校验是否过期
                //最后期限=创建时间+1小时
                LocalDateTime lastTime=d06PayOrderDO.getCreateTime().plusHours(1);
                LocalDateTime now=LocalDateTime.now();
                //如果当前时间小于最后期限则返回剩余支付时间，
                if(now.isAfter(lastTime)){
                    //否则修改订单状态为已取消
                    d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                    d06PayOrderDao.updateById(d06PayOrderDO);
                    //级联修改商家订单的状态
                    List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                            new EntityWrapper<D01OrderDO>()
                                    .eq("pay_order_id",d06PayOrderDO.getId())
                    );
                    for (D01OrderDO d01OrderDO : d01OrderDOS) {
                        d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                        d01OrderDao.updateById(d01OrderDO);
                    }
                }
            }
        }
        return 1;
    }


    @Override
    public int remindShipping(Long id) {
        //查询商家订单
        D01OrderDO d01OrderDO = d01OrderDao.get(id);
        if(d01OrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家订单不存在");
        }
        //根据商家订单创建消息体
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson(d01OrderDO.getNick());
        messageDo.setSendId(d01OrderDO.getUserId());
        messageDo.setCreatePerson(d01OrderDO.getNick());
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("提醒发货");
        messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
        }
        //websocket实时通知pc端商家 (响铃+弹框)
            try{
                GreetingController greetingController = new GreetingController();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type",1);
                jsonObject.put("content","请尽快发货，订单："+d01OrderDO.getOrderNo());
                greetingController.sendOneMessage(String.valueOf(d01OrderDO.getShopId()),jsonObject.toJSONString());
                log.info("socket推送提醒发货消息： 店铺id"+d01OrderDO.getShopId() );
            }catch (Exception e){
                log.info("socket推送提醒发货消息失败" );
                e.printStackTrace();
            }


        //查找消息接收人
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",d01OrderDO.getShopId());
        List<F08ShopUserDO> list = f08ShopUserDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家用户不存");
        }
        //发送消息
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.business.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(list.get(0).getId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送失败");
        }
        //消息推送——推送至商家对应申请入驻的用户
        //查找消息接收人
        QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        //通过商家的id去查消息接收人,返回一个集合;
        map1.put("shopId",d01OrderDO.getShopId());
        List<B01UserDO> list1 = b01UserDao.list(map1);
        if(CollectionUtils.isEmpty(list1)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家对应的申请用户不存在");
        }
        B01UserDO b01UserDO = list1.get(0);
        try {
            String alias = "yqc_" + b01UserDO.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,"", content, "1","1",null,null,null);  // IOS 单推
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, "", content, "1","1",null,null,null); // android 单推
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败"+e.getMessage());
        }
        return 1;
    }
    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return d01OrderDao.updateStatus(param);
    }
    @Override
    public int send(List<Long> ids) {
        List<D01OrderDO> list=new ArrayList<>();
        for (Long id : ids) {
            D01OrderDO d01OrderDO = d01OrderDao.get(id);
            if(d01OrderDO==null){
                throw new JsonException(ResultEnum.UPDATE_FAIL,"订单不存在");
            }
            if(StatusConstant.DeliveryStatus.shipped.getCode()==d01OrderDO.getDeliveryStatus()){
                throw new JsonException(ResultEnum.UPDATE_FAIL,"订单已经发货");
            }
            if(StatusConstant.OrderStatus.pay.getCode()!=d01OrderDO.getOrderStatus()){
                throw new JsonException(ResultEnum.UPDATE_FAIL,"订单状态不符");
            }
            d01OrderDO.setSendTime(LocalDateTime.now());
            d01OrderDO.setDeliveryStatus(StatusConstant.DeliveryStatus.shipped.getCode());
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.send.getCode());
            list.add(d01OrderDO);
        }
        int i = d01OrderDao.updateList(list);
        return i;
    }
    @Override
    public D06PayOrderDO getPayOrderWithItemById(Long id) {
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(id);
        //List<D02OrderItemDO> itemList=new ArrayList<>();
        //根据支付订单查询商家订单
        QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map1.put("payOrderId",d06PayOrderDO.getId());
        List<D01OrderDO> list1 = d01OrderDao.list(map1);
        if(list1!=null&&!list1.isEmpty()){
            for (D01OrderDO d01OrderDO : list1) {
                //根据商家订单ID查询订单明细
                QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map2.put("orderId",d01OrderDO.getId());
                List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                d01OrderDO.setOrderItem(list2);
            }
        }
        d06PayOrderDO.setShopOrders(list1);
        if(StatusConstant.OrderStatus.un_pay.getCode()==d06PayOrderDO.getOrderStatus()){
            //如果状态为待支付——校验是否过期
            //最后期限=创建时间+1小时
            LocalDateTime lastTime=d06PayOrderDO.getCreateTime().plusHours(1);
            LocalDateTime now=LocalDateTime.now();
            //如果当前时间小于最后期限则返回剩余支付时间，
            if(now.isBefore(lastTime)){
                Duration durtion= Duration.between(now,lastTime);
                //将时差秒转换成—— 分钟:秒 ；例如剩余时间：10:55
                String time=durtion.getSeconds()/60+"分钟"+durtion.getSeconds()%60+"秒";
                d06PayOrderDO.setSurplusTime(time);
            }else{//否则修改订单状态为已取消
                d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                d06PayOrderDao.updateById(d06PayOrderDO);
                //级联修改商家订单的状态
                for (D01OrderDO d01OrderDO : list1) {
                    d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                    d01OrderDao.updateById(d01OrderDO);
                }
            }
        }
        return d06PayOrderDO;
    }
    //删除订单=============================================================================================================
    @Override
    public int deleteShopOrderByShopIdAndOrderId(Long id) {
        //StatusConstant.OrderStatus.pay
        D01OrderDO d01= d01OrderDao.getOrderByShopIdAndOrderId(id);
        int number=d01.getOrderStatus();
        if(number==StatusConstant.OrderStatus.refund.getCode()||number==StatusConstant.OrderStatus.cancel.getCode()||number==StatusConstant.OrderStatus.refuse.getCode()){
            return d01OrderDao.deleteOrede(d01.getId());
        }
        return   0;
    }
    // 获取商家的总收益
    @Override
    public Map<String, Object> getShopIncome(Long shopid) {
        //通过商家的id获取商家账户信息
        F05ShopAccountDO f05ShopAccountDO= f05ShopAccountDao.get(shopid);
        //商家的余额
        BigDecimal TotalAccountBalance  =  f05ShopAccountDO.getAccountBalance();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
        Date date = new Date();
        HashMap<String, Object> map = new HashMap<>();
        map.put("shopid",Long.toString(shopid));
        //今天收益
        map.put("time",formatter.format(date));
        BigDecimal todayEarnings= d01OrderDao.getTodayShopincome(map);
        //昨天收益
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = (Date) calendar.getTime();
        map.put("time",formatter.format(yesterday));
        BigDecimal yesterdayEarnings= d01OrderDao.getTodayShopincome(map);

        //总收益
        /*
        * 1:通过商家的id先查询不是当天的订单所有数据
        * 2:肯定是个集合,.在把这个集合添加到商家账号收益表,
        * 3:添加的时候要搞一定的运算,因为表设计的是:第二天的原累积收益是前一天的最终累积收益,而当天收益就是当天的收益
        * 4:我们查询最后一条最终累积收益,在加上订单表里当天试试的订单金额
        * 5:最后相加就是商家实施的总收益.
        * */

       /*  map.put("code2", StatusConstant.OrderStatus.pay.getCode());
        map.put("code3", StatusConstant.OrderStatus.send.getCode());
        map.put("code4", StatusConstant.OrderStatus.end.getCode());
        BigDecimal totalRevenue= d01OrderDao.getTotalShopincome(map);*/
        map = new HashMap<>();
        //map.put("总收益",totalRevenue);
        map.put("今日收益",todayEarnings);
        map.put("昨日收益",yesterdayEarnings);
        map.put("商家余额",TotalAccountBalance);

        return map;
    }
//查询商家的订单
    @Override
    public List<D01OrderDOVo> getOrderByShopId(Map<String, Object> map) {
        List<D01OrderDOVo> d01OrderDOVos = d01OrderDao.listByShopIdHDPage(map);
        for (D01OrderDOVo d01:d01OrderDOVos
             ) {
            List<D02OrderItemDO> orderItmeByOrderId = d02OrderItemDao.selectList(
                    new EntityWrapper<D02OrderItemDO>()
                    .eq("order_id",d01.getId())
                    .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
            );
            d01.setD02OrderItemDOList(orderItmeByOrderId);
        }
        return d01OrderDOVos;
    }

    @Override
    public List<D06OrderStatusNumVO> getOrderNumberByUserId(Long userId) {
        return d06PayOrderDao.getOrderNumberByUserId(userId);
    }


    public static void main(String[] args) {
        try {
            UmengPush.sendAndroidCustomizedcast("yqc_type","yqc_302", "测试推送", "", "大王叫我来巡山", "1","1",null,null,null); // android 单推
            UmengPush.sendIOSCustomizedcast("yqc_type", "yqc_302", "测试推送","", "大王叫我来巡山2", "1","1",null,null,null);  // IOS 单推
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
