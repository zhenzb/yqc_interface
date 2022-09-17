package com.youqiancheng.controller.app;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ConfirmReceiveAbility;
import com.youqiancheng.form.app.*;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.app.D02OrderItemVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-订单接口"})
@RestController
@RequestMapping(value = "app/myOrder")
public class OrderController {
    @Autowired
    private D01OrderAppService d01OrderService;
    @Autowired
    private D02OrderItemAppService d02OrderItemAppService;
    @Autowired
    private D04GoodsEvaluateAppService d04GoodsEvaluateService;
    @Autowired
    private D03CustomerServiceAppService d03CustomerServiceService;
    @Autowired
    private D06PayOrderAppService d06PayOrderAppService;
    @Autowired
    private  B02UserAccountAppService b02UserAccountAppService;
    @Autowired
    private ConfirmReceiveAbility confirmReceiveAbility;

    @ApiOperation(value = "根据订单ID获取订单详情；参数——支付订单ID,类型：1，支付订单，2商家订单")
    @GetMapping("/getPayOrderInfoById")
    @AuthRuleAnnotation()
    ResultVo getPayOrderInfoById(Long id,int type ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"支付订单ID不能为空或者0");
        }
        if(type==1){
            D06PayOrderDO payOrderById = d01OrderService.getPayOrderWithItemById(id);
            return ResultVOUtils.success(payOrderById);
        }else{
            D01OrderVO d01Order = d01OrderService.get(id);
            return ResultVOUtils.success(d01Order);
        }

    }
    @ApiOperation(value = "根据支付订单ID修改物流信息")
    @PostMapping("/updateInfoByOrderId")
    @AuthRuleAnnotation()
    ResultVo updateInfoByOrderId (@RequestBody D06PayOrderUpdateInfoForm form){
        d01OrderService.updateInfoByOrderId(form);
        return  ResultVOUtils.success();
    }

    @ApiOperation(value = "根据订单明细ID获取订单详情；参数——订单明细ID")
    @GetMapping("/getOrderItemById")
    @AuthRuleAnnotation()
    ResultVo getOrderItmeById(Long id){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单明细ID不能为空或者0");
        }
            D02OrderItemVO d02OrderItemDO = d02OrderItemAppService.getOrderItmeAndOrder(id);
            return ResultVOUtils.success(d02OrderItemDO);
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


    /**
     * @api {POST} /app/myOrder/applyCustomerService 001申请售后退款
     * @apiGroup 004订单模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 申请售后退款接口
     * @apiHeader {String} X-Type 用户token [必填] 默认值 APP
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-UserId 用户 [必填]
     * @apiParam {long} userId 用户Id [必填]
     * @apiParam {long} orderId 订单Id [必填]
     * @apiParam {long} orderItemId 订单明细Id [必填]
     * @apiParam {String} orderNo 订单编码 [必填]
     * @apiParam {BigDecimal} money 退款金额 [必填]
     * @apiParam {String} reason 退款原因 [必填]
     * @apiParam {String} createPerson 创建人 [必填]
     * @apiParam {String} explain 退款说明 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "申请售后;参数——售后保存实体")
    @PostMapping("/applyCustomerService")
    @AuthRuleAnnotation()
    ResultVo applyCustomerService(@RequestBody @Valid  D03CustomerServiceSaveForm d03CustomerService ) {

        if(d03CustomerService.getOrderId()==0&&d03CustomerService.getOrderItemId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"退货申请关联单据ID不能为空");
        }
        int num=d03CustomerServiceService.save(d03CustomerService);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }



    /**
     * @api {GET} /app/myOrder/deleteOrderByOrderItemId 002删除订单接口
     * @apiGroup 004订单模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 删除订单接口
     * @apiHeader {String} X-Type 用户token [必填] 默认值 APP
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-UserId 用户 [必填]
     * @apiParam {long} orderItemId 订单明细Id [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "删除订单,参数---订单明细id")
    @GetMapping("/deleteOrderByOrderItemId")
    @AuthRuleAnnotation()
    ResultVo deleteOrderByOrderItemId(Long orderItemId) {
        int num=d06PayOrderAppService.deleteOrderByOrderItemId(orderItemId);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"删除失败");
        }

        return ResultVOUtils.success(num);
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

    @ApiOperation(value = "根据售后ID获取售后详情；参数——售后ID")
    @GetMapping("/getCustomerServiceById")
    @AuthRuleAnnotation()
    ResultVo getCustomerServiceById(Long id ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"售后ID不能为空或者0");
        }
        D03CustomerServiceDO d03CustomerService = d03CustomerServiceService.get(id);
        return ResultVOUtils.success(d03CustomerService);
    }

    @ApiOperation(value = "根据订单明细查找售后信息；参数——订单明细ID")
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
    ResultVo saveEvaluate(@RequestBody D04GoodsEvaluateSaveForm d04GoodsEvaluate) {
        int num=d04GoodsEvaluateService.save(d04GoodsEvaluate);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"评价失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "确认收货")
    @GetMapping("/confirmReceive")
    @AuthRuleAnnotation()
    ResultVo confirmReceive(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家订单ID不能为空");
        }
        //int num=d01OrderService.confirmReceive(id);
        int num = confirmReceiveAbility.confirmReceive(id);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"确认收货失败");
        }

        return ResultVOUtils.success();
    }



    @ApiOperation(value = "删除订单,参数---订单id")
    @GetMapping("/deleteOrderById")
    @AuthRuleAnnotation()
    ResultVo deleteOrderById(Long id) {
        int num=d06PayOrderAppService.deleteOrderById(id);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"删除失败");
        }

        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "获取用户的账号余额,参数---用户id")
    @GetMapping("/getAccountBalanceByUserId")
    @AuthRuleAnnotation()
    ResultVo getAccountBalance(Long id) {
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户订单ID不能为空");
        }
        //查用户余额
        BigDecimal accountBalance=b02UserAccountAppService.getAccountBalanceByUserId(id);
        return ResultVOUtils.success(accountBalance);
    }
    @ApiOperation(value = "余额支付,参数---余额实体类")
    @PostMapping("/payBalance")
    @AuthRuleAnnotation()
    ResultVo payBalance(@Valid  @RequestBody B02AppPayBalanceForm from) {
        //用户的余额支付
       int i=   b02UserAccountAppService.payByUserBalance(from);
       return  ResultVOUtils.success(i);
    }


}
