package com.youqiancheng.controller.client;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ConfirmReceiveAbility;
import com.youqiancheng.form.client.*;
import com.youqiancheng.form.client.my.PayBalanceForm;
import com.youqiancheng.mybatis.dao.F17PromotionIncomeDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.*;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"PC端-订单接口"})
@RestController
@RequestMapping(value = "pc/myOrder")
public class OrderController {
    @Autowired
    private D01OrderClientService d01OrderService;
    @Autowired
    private D02OrderItemClientService d02OrderItemAppService;
    @Autowired
    private D04GoodsEvaluateClientService d04GoodsEvaluateService;
    @Autowired
    private D03CustomerServiceClientService d03CustomerServiceService;
    @Resource
    private B02UserAccountClientService b02UserAccountClientService;
    @Autowired
    private ConfirmReceiveAbility confirmReceiveAbility;
    @Resource
    private F17PromotionIncomeDao f17PromotionIncomeDao;
    /**************************订单 **************************************/

//
//    @ApiOperation(value = "用户获取订单列表；参数——用户ID,订单状态，是否评价，分页参数")
//    @GetMapping("/getOrderListByStatus")
//    @AuthRuleAnnotation()
//    ResultVo<PageSimpleVO<D01OrderDO>> getOrderListByStatus(@Valid D02OrderItemSearchForm form, @Valid EntyPage page ) {
//
//        if(StatusConstant.IsEvaluate.un_evaluated.getCode()==form.getIsEvaluate()){
//            form.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
//        }
//        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
//        List<D02OrderItemClientVO> d02OrderItemVOs = d02OrderItemAppService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<D02OrderItemClientVO> d01OrderSimpleVO = new PageSimpleVO<>();
//        d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
//        d01OrderSimpleVO.setList(d02OrderItemVOs);
//
//        return ResultVOUtils.success(d01OrderSimpleVO);
//    }
    @ApiOperation(value = "用户获取订单列表；参数——用户ID,订单状态，是否评价，分页参数")
    @GetMapping("/getOrderListByStatus")
   // @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<D01OrderDO>> getOrderListByStatus(@Valid D02OrderItemSearchForm form, @Valid EntyPage page ) {
        List<NewOrderVO> vos=new ArrayList<>();
        //全部、待支付订单——(全部0)：1:待支付;
        if (form.getOrderStatus() == StatusConstant.OrderStatus.un_pay.getCode()) {
            QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
            map.put("invoiceType",999999);//线下消费，线上支付
            List<D06PayOrderDO> d06PayOrderDOS = d01OrderService.listPayOrder(map);
            PageSimpleVO<D06PayOrderDO> d01OrderSimpleVO = new PageSimpleVO<>();
            d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderSimpleVO.setList(d06PayOrderDOS);
            return ResultVOUtils.success(d01OrderSimpleVO);
        }//其他订单2:已支付/待发货;3:已发货/待收货
        else if (form.getOrderStatus() == StatusConstant.OrderStatus.pay.getCode()
                || form.getOrderStatus() == StatusConstant.OrderStatus.send.getCode()) {
            QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
            map.put("invoiceType",999999);
            List<D01OrderVO> list = d01OrderService.list(map);
            for(int i=0;i<list.size();i++){
                List<D02OrderItemDO> orderItem = list.get(i).getOrderItem();
                if(orderItem.size()==0){
                    list.remove(i);
                }
            }
            //封装到分页
            PageSimpleVO<D01OrderVO> d01OrderSimpleVO = new PageSimpleVO<>();
            d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderSimpleVO.setList(list);
            return ResultVOUtils.success(d01OrderSimpleVO);
        } else {
//            if(StatusConstant.IsEvaluate.un_evaluated.getCode()==form.getIsEvaluate()){
//                form.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
//            }
            int totalNumber = 0;
            if (StatusConstant.OrderStatus.end.getCode() == form.getOrderStatus()) {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemClientVO> d02OrderItemVOs = d02OrderItemAppService.listHDPage(map);
               // d02OrderItemVOs.stream().filter(item ->item.getGoodsId()==).collect(Collectors.toList());
                List<D02OrderItemClientVO> d02OrderItemVOss = new ArrayList<>();
                for(D02OrderItemClientVO d02OrderItemClientVO:d02OrderItemVOs){
                    if(d02OrderItemClientVO.getGoodsId() != 0){
                        d02OrderItemVOss.add(d02OrderItemClientVO);
                    }else{
                        ++totalNumber;

                    }
                }
                page.setTotalNumber(page.getTotalNumber()-totalNumber);
                //封装到分页
                PageSimpleVO<D02OrderItemClientVO> d01OrderSimpleVO = new PageSimpleVO<>();
                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemVOss);
                return ResultVOUtils.success(d01OrderSimpleVO);
            }
            else {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemClientVO> d02OrderItemVOs = d02OrderItemAppService.list5HDPage(map);
                //封装到分页
                //因为分页封装的要集合，所以先创建个集合，泛型是新建的对象这个对象里有个对象是放d02OrderItemVOs
                ArrayList<D02OrderItemClientVO2> d02OrderItemClientVO2 = new ArrayList<>();
                //ceshi开始==================
                for(D02OrderItemClientVO d02OrderItemVO:d02OrderItemVOs){
                    //每循环一次创建个对象
                    D02OrderItemClientVO2 ss = new D02OrderItemClientVO2();
                    /*
                    * 把从数据库查出来的对象复制到D02OrderItemClientVO2，
                    * 这时候D02OrderItemClientVO2对象里的orderItem字段还没有值呢
                    *所以在把这循环的这个对象复制给这个 字段
                    * 把ss放入集合里
                    *
                    * */

                    BeanUtils.copyProperties(d02OrderItemVO,ss);
                    ss.setOrderItem(d02OrderItemVO);
                    d02OrderItemClientVO2.add(ss);
                }
                //ceshi结束==============
                PageSimpleVO<D02OrderItemClientVO2> d01OrderSimpleVO = new PageSimpleVO<>();

                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemClientVO2);
                return ResultVOUtils.success(d01OrderSimpleVO);
            }
        }

    }

    @ApiOperation(value = "用户获取全部订单列表；参数——用户ID")
    @GetMapping("/getALLOrderByUserId")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<OrderVO>> getALLOrderByUserId(@Valid OrderSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("invoiceType",999999);
        List<NewOrderVO> orderVOS = d01OrderService.orderList(map);
        //封装到分页
        PageSimpleVO<NewOrderVO> d01OrderSimpleVO = new PageSimpleVO<>();
        d01OrderSimpleVO.setTotalNumber(orderVOS.size());
        d01OrderSimpleVO.setList(orderVOS);
        return ResultVOUtils.success(d01OrderSimpleVO);
    }


    @ApiOperation(value = "根据订单ID获取订单详情；参数——订单ID")
    @GetMapping("/getOrderInfoByOrderId")
    @AuthRuleAnnotation()
    ResultVo getOrderInfoByOrderId(Long id ){
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空");
        }
        D01OrderClientVO d01Order = d01OrderService.get(id);
        return ResultVOUtils.success(d01Order);
    }

    @ApiOperation(value = "删除订单")
    @GetMapping("/deleteByOrderId")
    @AuthRuleAnnotation()
    ResultVo deleteByOrderId(Long id ){
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空");
        }
        int i = d01OrderService.deleteByOrderId(id);
        return ResultVOUtils.success(i);
    }


    @ApiOperation(value = "取消订单")
    @GetMapping("/cancleOrder")
    @AuthRuleAnnotation()
    ResultVo cancleOrder(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单编码不能为空或者0");
        }
        D06PayOrderDO payOrderById = d01OrderService.getPayOrderById(id);
        if(payOrderById==null){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单信息不存在");
        }
        if(payOrderById.getOrderStatus()!=StatusConstant.OrderStatus.un_pay.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"非待支付订单不可以取消");
        }
        payOrderById.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
        int i = d01OrderService.updatePayOrderById(payOrderById);
        return ResultVOUtils.success(i);
    }



    @ApiOperation(value = "根据订单ID获取订单详情；参数——支付订单ID,类型：1，支付订单，2商家订单")
    @GetMapping("/getPayOrderInfoById")
    @AuthRuleAnnotation()
    ResultVo getPayOrderInfoById(Long id,int type ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"支付订单ID不能为空或者0");
        }
        if(type==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"类型不为空");
        }
        if(type==1){
            D06PayOrderDO payOrderById = d01OrderService.getPayOrderWithItemById(id);
            return ResultVOUtils.success(payOrderById);
        }else{
            D01OrderClientVO d01OrderClientVO = d01OrderService.get(id);
            return ResultVOUtils.success(d01OrderClientVO);
        }

    }

    @ApiOperation(value = "提醒发货;参数——商家订单ID")
    @GetMapping("/remindShipping")
    @AuthRuleAnnotation()
    ResultVo remindShipping(Long id ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家订单ID不能为空或者0");
        }
        d01OrderService.remindShipping(id);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "获取售后表列表;参数——用户ID")
    @GetMapping("/getCustomerServiceByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<D03CustomerServiceDO>> getCustomerServiceByUserId(@Valid D03CustomerServiceSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<D03CustomerServiceDO> d03CustomerServiceList = d03CustomerServiceService.listHDPage(map);
        //封装到分页
        PageSimpleVO<D03CustomerServiceDO> d03CustomerServiceSimpleVO = new PageSimpleVO<>();
        d03CustomerServiceSimpleVO.setTotalNumber(page.getTotalNumber());
        d03CustomerServiceSimpleVO.setList(d03CustomerServiceList);

        return ResultVOUtils.success(d03CustomerServiceSimpleVO);
    }

    @ApiOperation(value = "根据售后ID获取售后详情;参数——售后记录ID")
    @GetMapping("/getCustomerServiceById")
    @AuthRuleAnnotation()
    ResultVo getCustomerServiceById(Long id ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"售后记录ID不能为空或者0");
        }
        D03CustomerServiceDO d03CustomerService = d03CustomerServiceService.get(id);
        return ResultVOUtils.success(d03CustomerService);
    }

    @ApiOperation(value = "申请售后;参数——售后保存实体")
    @PostMapping("/applyCustomerService")
    @AuthRuleAnnotation()
    ResultVo applyCustomerService(@RequestBody @Valid D03CustomerServiceSaveForm d03CustomerService  ) {

        if(d03CustomerService.getOrderId()==0&&d03CustomerService.getOrderItemId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"退货申请关联单据ID不能为空");
        }
        int num=d03CustomerServiceService.save(d03CustomerService);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "根据订单查找售后信息；参数——订单ID")
    @GetMapping("/getCustomerServiceByOrderId")
    @AuthRuleAnnotation()
    ResultVo getCustomerServiceByOrderId(Long id ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家订单ID不能为空或者0");
        }
        D03CustomerServiceDO d03CustomerService = d03CustomerServiceService.getCustomerServiceByOrderId(id);
        return ResultVOUtils.success(d03CustomerService);
    }



    @ApiOperation(value = "保存用户商品评价记录；参数——评价保存实体")
    @PostMapping("/saveEvaluate")
    @AuthRuleAnnotation()
    ResultVo saveEvaluate(@RequestBody @Valid D04GoodsEvaluateSaveForm d04GoodsEvaluate ) {
        int num=d04GoodsEvaluateService.save(d04GoodsEvaluate);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "确认收货")
    @GetMapping("/confirmReceive")
    @AuthRuleAnnotation()
    ResultVo confirmReceive(Long id) {
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空或者0");
        }
       // int num=d01OrderService.confirmReceive(id);
        int num = confirmReceiveAbility.confirmReceive(id);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"确认收货失败");
        }
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "用户查询余额")
    @GetMapping("/getBalanceByUserId")
    //@AuthRuleAnnotation()
    ResultVo getBalanceByUserId(Long id) {
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID不能为空或者0");
        }
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",id);
        //map.put("countryId",1);
        List<B02UserAccountDO> list = b02UserAccountClientService.list(map);
        if(CollectionUtils.isEmpty(list)){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"余额信息不存在");
        }
        B02UserAccountDO b02UserAccountDO = list.get(0);
        if(b02UserAccountDO.getAccountBalance().compareTo(new BigDecimal("0"))== -1){
            b02UserAccountDO.setAccountBalance(new BigDecimal("0"));
        }
        //查询可视化收益金额
        Map<String,Object> f17Map = new HashMap<String,Object>();
        f17Map.put("userId",id);
        List<F17PromotionIncomeDO> list1 = f17PromotionIncomeDao.list(f17Map);
        if(list1.size()!=0){
            b02UserAccountDO.setPromotionMoeny(list1.get(0).getUserActualAmount());
        }else{
            b02UserAccountDO.setPromotionMoeny(new BigDecimal("0"));
        }

        return ResultVOUtils.success(b02UserAccountDO);
    }

    /**
     * 获取用户个订单状态的数量
     * @param userId
     * @return
     */
    @GetMapping("/getOrderStatsNum")
    public ResultVo getOrderStatsNum(Long userId){
        List<D06OrderStatusNumVO> orderVo = new ArrayList<>();
        //查询订单各状态数量
        List<D06OrderStatusNumVO> orderNumberList = d01OrderService.getOrderNumberByUserId(userId);
       return  ResultVOUtils.success(orderNumberList);
    }

    @ApiOperation(value = "余额支付")
    @PostMapping("/payByBalance")
    @AuthRuleAnnotation()
    ResultVo payByBalance(@Valid @RequestBody  PayBalanceForm form) {
        int i = b02UserAccountClientService.payByBalance(form);
        return ResultVOUtils.success(i);
    }




}
