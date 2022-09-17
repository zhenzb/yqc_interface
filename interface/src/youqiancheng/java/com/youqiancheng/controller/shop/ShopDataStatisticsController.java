package com.youqiancheng.controller.shop;

import com.youqiancheng.service.shop.ShopDataStatisticService;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/1 15:58
 * @Version: V1.0
 */
@Api(tags = "商家管理--数据统计")
@RestController
@RequestMapping("shop/dataStatistics")
public class ShopDataStatisticsController {
    @Resource
    ShopDataStatisticService dataStatisticsService;

    @ApiOperation(value = "数据展示", notes = "数据展示")
    @PostMapping(value = "/dataShow")
    public ResultVo dataShow() {
        return dataStatisticsService.DataStatistics();
    }


}

