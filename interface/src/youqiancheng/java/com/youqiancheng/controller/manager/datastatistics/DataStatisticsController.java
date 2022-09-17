package com.youqiancheng.controller.manager.datastatistics;

import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.service.manager.service.datastatistics.DataStatisticsService;
import com.youqiancheng.vo.manager.*;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
　* @Description:数据统计控制器
　* @author shalongteng
　* @date 2020/4/7 13:53
　*/
@RestController
@RequestMapping("admin/data")
@Api(tags = "总管理后台-数据概况")
public class DataStatisticsController {

    @Resource
    private DataStatisticsService dataStatisticsService;

    /**
    　* @Description: 获取用户数据
    　* @author shalongteng
    　* @date 2020/4/7 14:04
    　*/
    @ApiOperation(value = "获取用户数据", notes = "获取用户数据")
    @GetMapping("getUserInfo")
    public ResultVo getUserInfo() {
        UserVO userVO = dataStatisticsService.getUserInfo();

        return ResultVOUtils.success(userVO);
    }
    /**
    　* @Description: 获取商家数据
    　* @author shalongteng
    　* @date 2020/4/7 14:44
    　*/
    @ApiOperation(value = "获取商家数据", notes = "获取商家数据")
    @GetMapping("getShopInfo")
    public ResultVo getShopInfo() {
        ShopVO shopVO = dataStatisticsService.getShopInfo();

        return ResultVOUtils.success(shopVO);
    }
    /**
    　* @Description: 获取商品数据
    　* @author shalongteng
    　* @date 2020/4/7 15:06
    　*/
    @ApiOperation(value = "获取商品数据", notes = "获取商品数据")
    @GetMapping("getGoodsInfo")
    public ResultVo getGoodsInfo() {
        GoodsVO goodsVO = dataStatisticsService.getGoodsInfo();

        return ResultVOUtils.success(goodsVO);
    }
    /**
    　* @Description: 获取销售数据
    　* @author shalongteng
    　* @date 2020/4/7 15:25
    　*/
    @ApiOperation(value = "获取销售数据", notes = "获取销售数据")
    @GetMapping("getSaleInfo")
    public ResultVo getSaleInfo() {
        SaleVO saleVO = dataStatisticsService.getSaleInfo();

        return ResultVOUtils.success(saleVO);
    }

    /**
    　* @Description: 获取订单数据
    　* @author shalongteng
    　* @date 2020/4/7 15:59
    　*/
    @ApiOperation(value = "获取订单数据", notes = "获取订单数据")
    @GetMapping("getOrderInfo")
    public ResultVo getOrderInfo() {
        OrderVO orderVO = dataStatisticsService.getOrderInfo();

        return ResultVOUtils.success(orderVO);
    }

}
