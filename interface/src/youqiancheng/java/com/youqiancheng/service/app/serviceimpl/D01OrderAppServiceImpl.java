package com.youqiancheng.service.app.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.NoUtil;
import com.youqiancheng.form.app.*;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.D01OrderAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.*;
import com.youqiancheng.vo.client.D01OrderDOVo;
import com.youqiancheng.vo.result.ResultEnum;
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
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Transactional
@Service
public class D01OrderAppServiceImpl implements D01OrderAppService {
    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private D06PayOrderDao d06PayOrderDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;
    @Autowired
    private B04ShoppingCartDao b04ShoppingCartDao;
    @Resource
    private A15MessageDao a15MessageDao;
    @Resource
    private A17MessageUserDao a17MessageUserDao;
    @Resource
    private F08ShopUserDao f08ShopUserDao;
    @Resource
    private F05ShopAccountDao f05ShopAccountDao;
    @Autowired
    private  F15ShopProfitDao f15ShopProfitDao;
    @Autowired
    private  B01UserDao b01UserDao;
    @Autowired
    private C09GoodsSkuDao  c09GoodsSkuDao;
    @Autowired
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Resource
    private C01GoodsDao c01GoodsDao;
    @Override
    public D01OrderVO get(Long id){
        D01OrderVO dto=new D01OrderVO();
        D01OrderDO d01OrderDO = d01OrderDao.get(id);
        if(d01OrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到订单数据");
        }
        BeanUtils.copyProperties(d01OrderDO,dto);
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",d01OrderDO.getId());
        map.put("orderStatus",2);
        map.put("orderStatus1",3);
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
    public D06PayOrderDO getPayOrderWithItemById(Long id) {
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(id);
        //List<D02OrderItemDO> itemList=new ArrayList<>();
        if(d06PayOrderDO==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"没有支付订单信息");
        }
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
                System.out.print("44444444444444444444-------------------------------------------------"+durtion);
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
                    //级联修改订单明细的状态
                    List<D02OrderItemDO> d02OrderItemDOS = d02OrderItemDao.selectList(
                            new EntityWrapper<D02OrderItemDO>()
                                    .eq("order_id",d01OrderDO.getId())
                    );
                    for (D02OrderItemDO d02:d02OrderItemDOS
                    ) {
                        d02.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                        d02OrderItemDao.updateById(d02);
                    }

                }
            }
        }
        return d06PayOrderDO;
    }

    @Override
    public int updatePayOrderById(D06PayOrderDO dto) {
        Integer integer = d06PayOrderDao.updateById(dto);
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                .eq("pay_order_id",dto.getId())

        );
        for (D01OrderDO d01OrderDO : d01OrderDOS) {
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
            d01OrderDO.setFlag(1);
            d01OrderDao.updateById(d01OrderDO);
            //级联修改订单明细的状态
            List<D02OrderItemDO> d02OrderItemDOS = d02OrderItemDao.selectList(
                    new EntityWrapper<D02OrderItemDO>()
                            .eq("order_id",d01OrderDO.getId())
            );
            for (D02OrderItemDO d02:d02OrderItemDOS
                 ) {
                d02.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                d02OrderItemDao.updateById(d02);
            }

        }
        return integer;
    }


    @Override
    public List<D01OrderDO> listHDPage(Map<String, Object> map) {
        return d01OrderDao.listHDPage(map);
    }

//    public List<OrderVO> orderList1(Map<String, Object> map) {
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
//                        //根据商家订单ID查询订单明细
//                        QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                        map2.put("orderId",d01OrderDO.getId());
//                        List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
//                        for (D02OrderItemDO d02OrderItemDO : list2) {
//                            D01OrderVO ovo=new D01OrderVO();
//                            BeanUtils.copyProperties(d01OrderDO,ovo);
//                            List<D02OrderItemDO> list3=new ArrayList<>();
//                            list3.add(d02OrderItemDO);
//                            ovo.setOrderItem(list3);
//                            soList.add(ovo);
//                        }
//                        itemList.addAll(list2);
//
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
    public List<OrderVO> orderList(Map<String, Object> map) {
        List<OrderVO> vos=new ArrayList<>();
        //根据用户查询支付订单
        List<D06PayOrderDO> list = d06PayOrderDao.listHDPage(map);
        if(list!=null&&!list.isEmpty()){
            for (D06PayOrderDO d06PayOrder : list) {
                //根据订单状态处理单据
                //如果订单为待支付或者已取消，则直接返回订单信息
                //根据商家订单ID查询订单明细
                if(StatusConstant.OrderStatus.un_pay.getCode()==d06PayOrder.getOrderStatus()
                ||StatusConstant.OrderStatus.cancel.getCode()==d06PayOrder.getOrderStatus()){
                    OrderVO vo=new OrderVO();
                    vo.setOrderStatus(d06PayOrder.getOrderStatus());
                    List<AllShopOrderItemVO> itemList=new ArrayList<>();
                    QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                    map2.put("payOrderId",d06PayOrder.getId());
                    List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                    int num=0;
                    for (D02OrderItemDO d02OrderItemDO : list2) {
                        AllShopOrderItemVO itemvo=new AllShopOrderItemVO();
                        BeanUtils.copyProperties(d02OrderItemDO,itemvo);
                        itemList.add(itemvo);
                        num=num+d02OrderItemDO.getNum();
                    }
                    //传入支付订单信息
                    ALLPayOrderVO payOrder=new ALLPayOrderVO();
                    BeanUtils.copyProperties(d06PayOrder,payOrder);
                    payOrder.setOrderItem(itemList);
                    payOrder.setCount(num);
                    vo.setPayOrder(payOrder);
                    vos.add(vo);
                }//如果支付订单状态为已支付
                else{

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
                            if(d01OrderDO.getOrderStatus()>=StatusConstant.OrderStatus.end.getCode()){
                                for (D02OrderItemDO d02OrderItemDO : list2) {
                                    OrderVO vo=new OrderVO();
                                    vo.setOrderStatus(d06PayOrder.getOrderStatus());
                                    ALLOrderVO ovo=new ALLOrderVO();
                                    BeanUtils.copyProperties(d01OrderDO,ovo);
                                    List<AllShopOrderItemVO> alist1=new ArrayList<>();
                                    AllShopOrderItemVO vo1=new AllShopOrderItemVO();
                                    BeanUtils.copyProperties(d02OrderItemDO,vo1);
                                    alist1.add(vo1);
                                    ovo.setOrderItem(alist1);
                                    //soList.add(ovo);
                                    vo.setShopOrder(ovo);
                                    vos.add(vo);
                                }
                                //List<ALLOrderVO> soList=new ArrayList<>();

                            }else{
                                OrderVO vo=new OrderVO();
                                vo.setOrderStatus(d06PayOrder.getOrderStatus());
                                ALLOrderVO ovo=new ALLOrderVO();
                                BeanUtils.copyProperties(d01OrderDO,ovo);
                                List<AllShopOrderItemVO> alist=new ArrayList<>();
                                for (D02OrderItemDO d02OrderItemDO : list2) {
                                    AllShopOrderItemVO vo1=new AllShopOrderItemVO();
                                    BeanUtils.copyProperties(d02OrderItemDO,vo1);
                                    alist.add(vo1);
                                }
                                //List<ALLOrderVO> soList=new ArrayList<>();
                                ovo.setOrderItem(alist);
                                //soList.add(ovo);
                                vo.setShopOrder(ovo);
                                vos.add(vo);
                            }

                        }

                    }
                   // vo.setOrderItem(itemList);

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
//                //根据订单状态处理单据
//                //如果订单为待支付或者已取消，则直接返回订单信息
//                //根据商家订单ID查询订单明细
//                if(StatusConstant.OrderStatus.un_pay.getCode()==d06PayOrder.getOrderStatus()
//                        ||StatusConstant.OrderStatus.cancel.getCode()==d06PayOrder.getOrderStatus()){
//
//                    List<AllShopOrderItemVO> itemList=new ArrayList<>();
//                    QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                    map2.put("payOrderId",d06PayOrder.getId());
//                    List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
//                    for (D02OrderItemDO d02OrderItemDO : list2) {
//                        AllShopOrderItemVO itemvo=new AllShopOrderItemVO();
//                        BeanUtils.copyProperties(d02OrderItemDO,itemvo);
//                        itemList.add(itemvo);
//                    }
//                    vo.setOrderItem(itemList);
//                }//如果支付订单状态为已支付
//                else{
//                    //根据支付订单查询商家订单
//                    List<AllShopOrderItemVO> alist=new ArrayList<>();
//                    QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                    map1.put("payOrderId",d06PayOrder.getId());
//                    List<D01OrderDO> list1 = d01OrderDao.list(map1);
//                    if(list1!=null&&!list1.isEmpty()){
//                        for (D01OrderDO d01OrderDO : list1) {
//                            //根据商家订单ID查询订单明细
//                            QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//                            map2.put("orderId",d01OrderDO.getId());
//                            List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
//                            for (D02OrderItemDO d02OrderItemDO : list2) {
//                                AllShopOrderItemVO vo1=new AllShopOrderItemVO();
//                                BeanUtils.copyProperties(d02OrderItemDO,vo1);
//                                alist.add(vo1);
//                            }
//                        }
//                        vo.setOrderItem(alist);
//                    }
//                    // vo.setOrderItem(itemList);
//
//                }
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
                //循环明细——移动端要求，多个明细拆分展示
//                List<D02OrderItemDO> itemList= new ArrayList<>();
//                for (D02OrderItemDO d02OrderItemDO : list2) {
//                    d02OrderItemDO.setOrderStatus(d01OrderDO.getOrderStatus());
//                    itemList.add(d02OrderItemDO);
//                }
                vo.setOrderItem(list2);
                voList.add(vo);
            }

        }

        return voList;
    }

    @Override
    public List<D06PayOrderDO>  listPayOrder(Map<String, Object> map) {
        //根据用户查询支付订单
        List<D06PayOrderDO> list = d06PayOrderDao.listHDPage(map);
        if(list!=null&&!list.isEmpty()){
            for (D06PayOrderDO d06PayOrder : list) {
                List<D02OrderItemDO> list3 = new ArrayList<>();
                //根据支付订单查询商家订单
                QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map1.put("payOrderId",d06PayOrder.getId());
                List<D01OrderDO> list1 = d01OrderDao.list(map1);
                if(list1!=null&&!list1.isEmpty()){
                    int num=0;
                    for (D01OrderDO d01OrderDO : list1) {
                        //根据商家订单ID查询订单明细
                        QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                        map2.put("orderId",d01OrderDO.getId());
                        List<D02OrderItemDO> list2 = d02OrderItemDao.list(map2);
                        for (D02OrderItemDO d02OrderItemDO : list2) {
                            d02OrderItemDO.setOrderStatus(d01OrderDO.getOrderStatus());
                            num=num+d02OrderItemDO.getNum();
                            list3.add(d02OrderItemDO);
                        }
                    }
                    d06PayOrder.setCount(num);
                    d06PayOrder.setOrderItem(list3);
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
    @Transactional
    public D06PayOrderDO save(D06PayOrderSearchForm  dto) {
        //保存支付订单信息
        D06PayOrderDO d06Order=new D06PayOrderDO();
        BeanUtils.copyProperties(dto,d06Order);
        d06Order.setCreateTime(LocalDateTime.now());
        //把购物车里的商品存入支付订单表,先设置未支付状态
        d06Order.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
        //用此类生成支付订单的订单编号
        String no = NoUtil.createNo(dto.getUserId(), TypeConstant.Order.getCode());
        d06Order.setOrderNo(no);
        d06Order.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        //把支付订单添加到支付订单表
        int insert = d06PayOrderDao.insert(d06Order);
        if(insert<=0){
            throw new JsonException(ResultEnum.INSERT_FAIL,"支付订单插入失败");
        }
        //商品明细按照商家分组
        if(dto.getOrderItem()==null||dto.getOrderItem().isEmpty()){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"订单明细不能为空");
        }
        List<Long> ids=new ArrayList<>();
        Map<Long,List<D02OrderItemSaveForm>> map=new HashMap<>();
        C01GoodsDO c01GoodsDO=null;
        for (D02OrderItemSaveForm formItem : dto.getOrderItem()) {
            //查询库存是否存在
            C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(formItem.getSkuId());
            if(c09GoodsSkuDO==null){
                c01GoodsDO = c01GoodsDao.get(formItem.getGoodsId());
                if(c01GoodsDO !=null){
//                    int num = c01GoodsDO.getStock()-1; //库存数
//                    c01GoodsDO.setStock(num);
//                    c01GoodsDao.updateById(c01GoodsDO);
                    if(c01GoodsDO.getStock()<formItem.getNum()){
                        throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"库存不足");
                    }
//                    else{
//                        int num = c09GoodsSkuDO.getNum()-formItem.getNum(); //库存数
//                        c09GoodsSkuDO.setNum(num);
//                        c09GoodsSkuDao.updateById(c09GoodsSkuDO);
//                    }
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
            d01OrderDO.setPayOrderId(d06Order.getId());
            d01OrderDO.setCreateTime(LocalDateTime.now());
            d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.un_pay.getCode());
            d01OrderDO.setDeliveryStatus(StatusConstant.DeliveryStatus.un_shipped.getCode());
            //生成商家订单的订单编号
            d01OrderDO.setFlag(1);
            d01OrderDO.setOrderNo(no);
            d01OrderDO.setShopName(map.get(aLong).get(0).getShopName());
            d01OrderDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            BigDecimal money=new BigDecimal("0");
            for (D02OrderItemSaveForm item : map.get(aLong)) {
                money=money.add(item.getTotalPrice());
            }
//            for (D02OrderItemSaveForm item : map.get(aLong)) {
//                BigDecimal price = item.getPrice();
//                int num = item.getNum();
//                BigDecimal bigDecimal = DecimalUtil.multiplicationBigMal(String.valueOf(price), String.valueOf(num), 2, null);
//                money=money.add(bigDecimal);
//            }

            d01OrderDO.setOrderPrice(money);
            Integer insert1 = d01OrderDao.insert(d01OrderDO);
            if(insert1<=0){
                throw new JsonException(ResultEnum.INSERT_FAIL,"商家订单插入失败");
            }

            List<D02OrderItemDO> list1=new ArrayList<>();
            for (D02OrderItemSaveForm item : map.get(aLong)) {
                D02OrderItemDO d02OrderItemDO=new D02OrderItemDO();
                BeanUtils.copyProperties(item,d02OrderItemDO);
                //d02OrderItemDO.setTotalPrice(money);
                d02OrderItemDO.setOrderId(d01OrderDO.getId());
                d02OrderItemDO.setIsEvaluate(StatusConstant.IsEvaluate.un_evaluated.getCode());
                d02OrderItemDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                d02OrderItemDO.setCreatePerson(dto.getUserPhone());
                d02OrderItemDO.setUpdatePerson(dto.getUserPhone());
                d02OrderItemDO.setCreateTime(LocalDateTime.now());
                d02OrderItemDO.setUpdateTime(LocalDateTime.now());
                d02OrderItemDO.setPayOrderId(d06Order.getId());
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
    public int payOrder(PayOrderPayForm form) {
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(form.getId());
        d06PayOrderDO.setPayTime(LocalDateTime.now());
        d06PayOrderDO.setTradeNo(form.getTradeNo());
        d06PayOrderDO.setPayType(form.getPayType());
        d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
        d06PayOrderDao.updateById(d06PayOrderDO);
        List<D01OrderDO> list = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                        .eq("pay_order_id", form.getId())
        );
        if(list==null||list.isEmpty()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到对应的商家订单");
        }
        for (D01OrderDO d01OrderDO : list) {
            d01OrderDO.setPayType(form.getPayType());
            d01OrderDO.setPayTime(d06PayOrderDO.getPayTime());
            d01OrderDO.setTradeNo(form.getTradeNo());
            d06PayOrderDO.setOrderStatus(StatusConstant.OrderStatus.pay.getCode());
            d01OrderDao.updateById(d01OrderDO);
        }
        return 0;
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
        messageDo.setTitle("发货提醒");
        //messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
        messageDo.setShopOrderId(d01OrderDO.getId());
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
        }
        //查找消息接收人
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        //通过商家的id去查消息接收人,返回一个集合;
        map.put("shopId",d01OrderDO.getShopId());
        List<F08ShopUserDO> list = f08ShopUserDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"商家用户不存在");
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
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return d01OrderDao.updateStatus(param);
    }

    @Override
    public int updateInfoByOrderId( D06PayOrderUpdateInfoForm form) {
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(form.getId());
        if(d06PayOrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单不存在");
        }
        BeanUtils.copyProperties(form,d06PayOrderDO);
        d06PayOrderDao.updateById(d06PayOrderDO);
        List<D01OrderDO> d01OrderDOS = d01OrderDao.selectList(
                new EntityWrapper<D01OrderDO>()
                .eq("pay_order_id",form.getId())
        );
        if(!CollectionUtils.isEmpty(d01OrderDOS)){
            for (D01OrderDO d01OrderDO : d01OrderDOS) {
                d01OrderDO.setProvince(form.getProvince());
                d01OrderDO.setCity(form.getCity());
                d01OrderDO.setArea(form.getArea());
                d01OrderDO.setShippingAddress(form.getShippingAddress());
                d01OrderDO.setShippingPhone(form.getShippingPhone());
                d01OrderDO.setShippingName(form.getShippingName());
            }
        }
        d01OrderDao.updateList(d01OrderDOS);
        return 1;
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

                            //级联修改订单明细的状态
                            List<D02OrderItemDO> d02OrderItemDOS = d02OrderItemDao.selectList(
                                    new EntityWrapper<D02OrderItemDO>()
                                            .eq("order_id",d01OrderDO.getId())
                            );
                            for (D02OrderItemDO d02:d02OrderItemDOS
                            ) {
                                d02.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
                                d02OrderItemDao.updateById(d02);
                            }

                        }
                    }
                }
        }
        return 1;
    }
    @Override
    public Map<String, Object> getAppShopIncome(Long id) {
        // 获取商家的总收益

            //通过商家的id获取商家账户信息
            F05ShopAccountDO appf05ShopAccountDao = f05ShopAccountDao.getShopAccountById(id);
            if(appf05ShopAccountDao==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST, "没有此对象");
            }
            //商家的余额
            BigDecimal TotalAccountBalance = appf05ShopAccountDao.getAccountBalance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            HashMap<String, Object> map = new HashMap<>();
            map.put("shopid", Long.toString(id));
            //今天收益
            map.put("time", formatter.format(date));
            BigDecimal todayEarnings = d01OrderDao.getTodayShopincome(map);
            //昨天收益
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = (Date) calendar.getTime();
            map.put("time", formatter.format(yesterday));
            BigDecimal yesterdayEarnings = d01OrderDao.getTodayShopincome(map);

            //总收益
            BigDecimal  totalIncome= f15ShopProfitDao.getTotalIncome(id);

            map = new HashMap<>();
            map.put("todayIncome", todayEarnings);
            map.put("yestdayIncome", yesterdayEarnings);
            map.put("shopBalance", TotalAccountBalance);
            map.put("totalIncome",totalIncome);
            return map;

    }

    @Override
    public List<D01OrderDOVo> getAppOrderByShopId(Map<String, Object> map) {
        List<D01OrderDOVo> d01OrderDOVos = d01OrderDao.listByShopIdHDPage(map);
        for (D01OrderDOVo d01:d01OrderDOVos
        ) {
            List<D02OrderItemDO> orderItmeByOrderId = d02OrderItemDao.getOrderItmeByOrderId(d01.getId());
            d01.setD02OrderItemDOList(orderItmeByOrderId);
        }
        return d01OrderDOVos;
    }
    //查商家的今日订单个数
    @Override
    public int getAppTodayShopOrderCountByShopId(Long ShopId) {
        return d01OrderDao.getAppTodayShopOrderCountByShopId(ShopId);
    }
    //查商家的订单各个状态的个数
    @Override
    public  Map<String, Object> getOrderStatusCountById(Long ShopId) {
        Map<String, Object> map = new HashMap<>();
        List<D01OrderDO>   orderStatuslist= d01OrderDao.getOrderStatusCountById(ShopId);
        List<D02OrderItemDO>  d02OrderItemStatusList= d02OrderItemDao.getD02OrderItemDaoById(ShopId);
        List<D01OrderDO> paylist = orderStatuslist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.pay.getCode()).collect(Collectors.toList());
        List<D01OrderDO> unPaylist = orderStatuslist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.un_pay.getCode()).collect(Collectors.toList());
        List<D01OrderDO> sendlist = orderStatuslist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.send.getCode()).collect(Collectors.toList());
        List<D01OrderDO> endlist = orderStatuslist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.end.getCode()).collect(Collectors.toList());
        List<D02OrderItemDO> applyPassRefuseRefundlist = d02OrderItemStatusList.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.pass.getCode()||s.getOrderStatus()==StatusConstant.OrderStatus.apply_refund.getCode()||s.getOrderStatus()==StatusConstant.OrderStatus.refuse.getCode()||s.getOrderStatus()==StatusConstant.OrderStatus.refund.getCode()).collect(Collectors.toList());

        map.put("pay",paylist.size());
        map.put("unPay",unPaylist.size());
        map.put("send",sendlist.size());
        map.put("end",endlist.size());
        map.put("pass",applyPassRefuseRefundlist.size());
        return map;
    }
    //查商家的各个状态下的订单信息
    @Override
    public List<D01OrderStatusVo> getAppShopStatusOrder1(D01OrderStatusrForm d01OrderStatusrForm) { ;
           return d01OrderDao.getAppShopStatusOrder(d01OrderStatusrForm);
    }
    //查商家的各个状态下的订单信息
    @Override
    public List<D01OrderStatusVo> getAppShopStatusOrder(D01OrderStatusrForm d01OrderStatusrForm) { ;
           return d01OrderDao.getAppShopStatusOrder(d01OrderStatusrForm);
    }

   /*    @Override
    public Map<String, Object> getUserOrderStatusCountByUserId(Map<String, Object> map) {

            *//*    un_pay(1,"待支付"),
                pay(2,"已支付/待发货"),
                send(3,"已发货/待收货"),
                end(4,"已收货/已完成"),
                apply_refund(5,"退货待审核"),
                pass(6,"通过/待退款"),
                refuse(7,"拒绝"),
                refund(8,"已退款"),
                cancel(9,"已取消");*//*
            //先定义个map,用了装各个状态的个数
        Map<String, Object> mapCount = new HashMap<>();
        //在定义在此方法里的十个全局变量,来存放各个订单状态的个数;
        int unPayCount;
        int payCount;
        int cancelCount;
        int sendCount=0;
        int endCount=0;
        int apply_refunCount=0;
        int passCount=0;
        int refuseCount=0;
        int refundCount=0;
        int pass;
        int sum;
        //根据用户查询支付订单
        List<D06PayOrderDO> payOrderlist = d06PayOrderDao.list(map);
        if(payOrderlist!=null&&!payOrderlist.isEmpty()){
            //在支付订单里只有三种状态,所以在这假如集合不为空取出这三种状态的个数
            List<D06PayOrderDO> unPaylist = payOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.un_pay.getCode()).collect(Collectors.toList());
            List<D06PayOrderDO> paylist = payOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.pay.getCode()).collect(Collectors.toList());
            List<D06PayOrderDO> cancellist = payOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.cancel.getCode()).collect(Collectors.toList());
            unPayCount = unPaylist.size();//未支付个数
            payCount = paylist.size(); //支付个数
            cancelCount = cancellist.size();//取消个数
            mapCount.put("un_pay",unPayCount);
            mapCount.put("pay",payCount);
            mapCount.put("cancel",cancelCount);
            for (D06PayOrderDO d06PayOrder : payOrderlist) {
                //根据支付订单查询商家订单
                QueryMap map1=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map1.put("payOrderId",d06PayOrder.getId());
                List<D01OrderDO> shopOrderlist = d01OrderDao.list(map1);
                if(shopOrderlist!=null&&!shopOrderlist.isEmpty()){
                    //在商家订单里只有3 ,4状态,所以在这假如集合不为空取出这两种状态的个数
                    List<D01OrderDO> sendlist = shopOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.send.getCode()).collect(Collectors.toList());
                    List<D01OrderDO> endlist = shopOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.end.getCode()).collect(Collectors.toList());
                    sendCount +=  sendlist.size();
                     endCount += endlist.size();
                    mapCount.put("send",sendCount);//待收货  3
                    mapCount.put("end",endCount);  //待评价  4
                    for (D01OrderDO d01OrderDO : shopOrderlist) {
                        //根据商家订单ID查询订单明细
                        QueryMap map2=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                        map2.put("orderId",d01OrderDO.getId());
                        List<D02OrderItemDO> OrderItemlist = d02OrderItemDao.list(map2);
                        if(OrderItemlist!=null&&OrderItemlist.isEmpty()){
                            //在订单明细里只有5,6,7,8状态,所以在这假如集合不为空取出这四种状态的个数
                            List<D02OrderItemDO> apply_refundlist = OrderItemlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.apply_refund.getCode()).collect(Collectors.toList());
                            List<D02OrderItemDO> passlist = OrderItemlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.pass.getCode()).collect(Collectors.toList());
                            List<D02OrderItemDO> refuselist = OrderItemlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.refuse.getCode()).collect(Collectors.toList());
                            List<D02OrderItemDO> refundlist = OrderItemlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.refund.getCode()).collect(Collectors.toList());
                            apply_refunCount += apply_refundlist.size();
                            passCount+= passlist.size();
                            refuseCount += refuselist.size();
                            refundCount += refundlist.size();

                        }
                    }
                }
            }
            mapCount.put("un_pay",unPayCount);//代付款  1
            mapCount.put("pay",payCount);       //待收货  2
           // mapCount.put("cancel",cancelCount);
            pass=apply_refunCount+passCount+refuseCount+refundCount;
            mapCount.put("pass",pass);
            sum = unPayCount+payCount+cancelCount;//全部个数
            // sum = unPayCount+payCount+sendCount+ endCount+apply_refunCount+passCount+refuseCount+refundCount+cancelCount;//代付款  1
            mapCount.put("total",sum);
        }
        return mapCount;
    }*/


    @Override
    public Map<String, Object> getUserOrderStatusCountByUserId(Map<String, Object> map) {

            /*    un_pay(1,"待支付"),
                pay(2,"已支付/待发货"),
                send(3,"已发货/待收货"),
                end(4,"已收货/已完成"),
                apply_refund(5,"退货待审核"),
                pass(6,"通过/待退款"),
                refuse(7,"拒绝"),
                refund(8,"已退款"),
                cancel(9,"已取消");*/
        //先定义个map,用了装各个状态的个数
        Map<String, Object> mapCount = new HashMap<>();
        //在定义在此方法里的十个全局变量,来存放各个订单状态的个数;
        int unPayCount;
        int payCount = 0;
        int cancelCount;
        int sendCount = 0;
        int endCount = 0;
        int apply_refunCount = 0;
        int passCount = 0;
        int refuseCount = 0;
        int refundCount = 0;
        int pass;
        int sum;
        //根据用户查询支付订单
        List<D06PayOrderDO> payOrderlist = d06PayOrderDao.list(map);
        if (payOrderlist != null && !payOrderlist.isEmpty()) {
            //在支付订单里只有三种状态,所以在这假如集合不为空取出这三种状态的个数
            List<D06PayOrderDO> unPaylist = payOrderlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.un_pay.getCode()).collect(Collectors.toList());
            // List<D06PayOrderDO> paylist = payOrderlist.stream().filter(s -> s.getOrderStatus()==StatusConstant.OrderStatus.pay.getCode()).collect(Collectors.toList());
            List<D06PayOrderDO> cancellist = payOrderlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.cancel.getCode()).collect(Collectors.toList());
            unPayCount = unPaylist.size();//未支付个数
            cancelCount = cancellist.size();//取消个数
            mapCount.put("un_pay", unPayCount);
            mapCount.put("cancel", cancelCount);

            //根据用户查询商家订单
            List<D01OrderDO> shopOrderlist = d01OrderDao.list(map);
            if (shopOrderlist != null && !shopOrderlist.isEmpty()) {
                //在商家订单里用2,3 ,4状态,所以在这假如集合不为空取出这两种状态的个数
                List<D01OrderDO> paylist = shopOrderlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.pay.getCode()).collect(Collectors.toList());
                List<D01OrderDO> sendlist = shopOrderlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.send.getCode()).collect(Collectors.toList());
                // List<D01OrderDO> endlist = shopOrderlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.end.getCode()).collect(Collectors.toList());
                payCount += paylist.size(); //支付个数
                sendCount += sendlist.size();

                mapCount.put("pay", payCount);
                mapCount.put("send", sendCount);//待收货  3


            }
            for (D01OrderDO d01OrderDO : shopOrderlist) {
                //根据商家订单ID查询订单明细
                QueryMap map2 = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map2.put("orderId", d01OrderDO.getId());
                List<D02OrderItemDO> OrderItemlist = d02OrderItemDao.list(map2);
                if (OrderItemlist != null && !OrderItemlist.isEmpty()) {
                    //在订单明细里有4,5,6,7,8状态,所以在这假如集合不为空取出这五种状态的个数
                    List<D02OrderItemDO> endlist = OrderItemlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.end.getCode() && s.getIsEvaluate()==StatusConstant.IsEvaluate.un_evaluated.getCode()).collect(Collectors.toList());
                    List<D02OrderItemDO> apply_refundlist = OrderItemlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.apply_refund.getCode()).collect(Collectors.toList());
                    List<D02OrderItemDO> passlist = OrderItemlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.pass.getCode()).collect(Collectors.toList());
                    List<D02OrderItemDO> refuselist = OrderItemlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.refuse.getCode()).collect(Collectors.toList());
                    List<D02OrderItemDO> refundlist = OrderItemlist.stream().filter(s -> s.getOrderStatus() == StatusConstant.OrderStatus.refund.getCode()).collect(Collectors.toList());

                    endCount += endlist.size();
                    apply_refunCount += apply_refundlist.size();
                    passCount += passlist.size();
                    refuseCount += refuselist.size();
                    refundCount += refundlist.size();

                }

            }

            mapCount.put("un_pay", unPayCount);//代付款  1
            mapCount.put("pay", payCount);       //待发货  2
            // mapCount.put("cancel",cancelCount);
            mapCount.put("end", endCount);  //待评价  4
            pass = apply_refunCount + passCount + refuseCount + refundCount;
            mapCount.put("pass", pass);
            sum = unPayCount + payCount + cancelCount;//全部个数
            // sum = unPayCount+payCount+sendCount+ endCount+apply_refunCount+passCount+refuseCount+refundCount+cancelCount;//代付款  1
            mapCount.put("total", sum);
        }
        return mapCount;
    }




}
