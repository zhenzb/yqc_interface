package com.youqiancheng.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.ShopOrderSendForm;
import com.youqiancheng.form.shop.otther.ShopOrderPageForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:16
 * @Version: V1.0
 */
@Api(tags = "商家管理--订单管理")
@RestController
@RequestMapping("shop/orderManagement")
public class ShopOrderManagementController {
    @Resource
    private ShopOrderService shopOrderService;


    @ApiOperation(value = "订单列表", notes = "订单列表")
    @PostMapping(value = "/pageOrders")
    public ResultVo<PageSimpleVO<D01OrderDO>> pageOrders(@Valid ShopOrderPageForm shopOrderPageForm, @Valid EntyPage page ){

        Map<String, Object> map = new HashMap<>();
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        map.put("shopId",shopUser.getShopId());
        map.put("shopOrderPageForm", shopOrderPageForm);
        map.put("page", page);
        List<D01OrderDO> orderlist = shopOrderService.listOrderHDPage(map);
        if (!CollectionUtils.isEmpty(orderlist)){
            //拼接详细地址
            for (D01OrderDO d01OrderDO : orderlist) {
                d01OrderDO.setShippingAddress(d01OrderDO.getProvince()+d01OrderDO.getCity()+d01OrderDO.getArea()+d01OrderDO.getShippingAddress());
            }
            for (D01OrderDO order:orderlist){
                EntityWrapper<D02OrderItemDO> ew = new EntityWrapper<>();
                ew.eq("order_id",order.getId())
                .orderBy("id",false)
                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
                List<D02OrderItemDO> d02OrderItemDOS = shopOrderService.listOrderItemBy(ew);
                int sum = 0;
                for(D02OrderItemDO d02:d02OrderItemDOS){
                    sum = sum+d02.getNum();
                }
                order.setNum(sum);
                order.setOrderItem(d02OrderItemDOS);
            }

        }
        //封装到分页
        PageSimpleVO<D01OrderDO> shopOrderPageSimpleVO = new PageSimpleVO<>();
        shopOrderPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopOrderPageSimpleVO.setList(orderlist);
        return ResultVOUtils.success(shopOrderPageSimpleVO);
    }

    @ApiOperation(value = "订单发货")
    @PostMapping(value = "/editOrderStatusById")
    public ResultVo editOrderStatusById(@Valid ShopOrderSendForm form){
        return ResultVOUtils.success(shopOrderService.send(form));
    }

    @ApiOperation(value = "搜索——获取快递公司名称或者快递公司编号；参数——快递公司名称, 编号类型等")
    @GetMapping("/searchCourierServicesCompanyList")
    ResultVo<A19ExpressDO> searchShopList(@Valid A19ExpressDO a19ExpressDO ) {
        QueryMap map = new QueryMap(a19ExpressDO, StatusConstant.CreatFlag.delete.getCode());
        List<A19ExpressDO> a19ExpressDOList = shopOrderService.getCourierServicesCompanylist(map);

        return ResultVOUtils.success(a19ExpressDOList);
    }

//    @ApiOperation(value = "批量审核通过")
//    @PostMapping(value = "/examineOrderPass")
//    public ResultVo examineOrderPass(@Valid ShopOrderStatusEditForm shopOrderStatusEditForm){
//        if(shopOrderStatusEditForm == null || ArrayUtils.isEmpty(shopOrderStatusEditForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        Integer integer =   shopOrderService.examineOrderPass(shopOrderStatusEditForm.getIds());
//        return ResultVOUtils.success();
//    }
//    @ApiOperation(value = "批量审核拒绝")
//    @PostMapping(value = "/examineOrderRefuse")
//    public ResultVo examineOrderRefuse(@Valid ShopOrderStatusEditForm shopOrderStatusEditForm){
//        if(shopOrderStatusEditForm == null || ArrayUtils.isEmpty(shopOrderStatusEditForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        Integer integer =   shopOrderService.examineOrderRefuse(shopOrderStatusEditForm.getIds());
//        return ResultVOUtils.success();
//    }
//
//    @ApiOperation(value = "批量退款")
//    @PostMapping(value = "/batchRefundOrder")
//    public ResultVo batchRefundOrder(@Valid ShopOrderStatusEditForm shopOrderStatusEditForm){
//        if(shopOrderStatusEditForm == null || ArrayUtils.isEmpty(shopOrderStatusEditForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        Integer integer =   shopOrderService.batchRefundOrder(shopOrderStatusEditForm.getIds());
//        return ResultVOUtils.success(integer);
//    }

}


