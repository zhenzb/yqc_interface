package com.youqiancheng.controller.manager.ordermanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.order.CustomerServiceManageForm;
import com.youqiancheng.form.manager.order.OrderQueryForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.service.manager.service.ordermanagement.OrderListService;
import com.youqiancheng.service.shop.CustomerServiceService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
　* @Description: 订单管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_order/order")
@Api(tags = "总管理后台-订单管理")
public class OrderListController {
    private static final Log log = LogFactory.getLog(OrderListController.class);
    @Resource
    private CustomerServiceService customerServiceService;
    @Resource
    private OrderListService orderListService;
    /**
    　* @Description: 获取订单列表
    　* @author shalongteng
    　* @date 2020/4/8 9:11
    　*/
    @ApiOperation(value = "获取订单列表", notes = "获取订单列表")
    @GetMapping("listOrder")
    public ResultVo<PageSimpleVO<D01OrderDO>> listShop(@Valid OrderQueryForm form,@Valid EntyPage page) {


        QueryMap map = new QueryMap(form,page);
        List<D01OrderDO> authAdminList = orderListService.listOrderHDPage(map);
        //封装到分页
        PageSimpleVO<D01OrderDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }
    /**
    　* @Description: 订单详情
    　* @author shalongteng
    　* @date 2020/4/16 18:28
    　*/
    @ApiOperation(value = "订单详情", notes = "订单详情")
    @GetMapping("/orderInfo")
    public ResultVo<D01OrderDO> orderInfo(Integer id) {
        D01OrderDO d01OrderDO = orderListService.orderDetail(id);

        return ResultVOUtils.success(d01OrderDO);
    }

    /**
     　* @Description: 订单明细
     　* @author shalongteng
     　* @date 2020/4/16 18:28
     　*/
    @ApiOperation(value = "订单明细", notes = "订单明细")
    @GetMapping("/getOrderDetail")
    public ResultVo<D01OrderDO> getOrderDetail(Long id,  @Valid EntyPage page    ) {


        QueryMap map =new QueryMap(page);
        map.put("orderId",id);
        List<D02OrderItemDO> detail = orderListService.getDetail(map);

        return ResultVOUtils.success(detail);
    }


    @ApiOperation(value = "售后列表", notes = "售后列表")
    @PostMapping(value = "/getCustomerService")
    public ResultVo<PageSimpleVO<D01OrderDO>> getCustomerService(@Valid CustomerServiceManageForm form, @Valid EntyPage page ){
        QueryMap map=new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<D03CustomerServiceDO> d03CustomerServiceDOS = customerServiceService.listHDPageByManage(map);
        //封装到分页
        PageSimpleVO<D03CustomerServiceDO> simpleVO = new PageSimpleVO<>();
        simpleVO.setTotalNumber(page.getTotalNumber());
        simpleVO.setList(d03CustomerServiceDOS);
        return ResultVOUtils.success(simpleVO);
    }

}
