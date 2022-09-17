package com.youqiancheng.service.shop.serviceimpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.TimeTest;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.otther.ShopGoodsStatisticsForm;
import com.youqiancheng.form.shop.otther.ShopOrderStatisticsForm;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.service.shop.ShopDataStatisticService;
import com.youqiancheng.service.shop.ShopGoodsService;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/1 14:57
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopDataStatisticsServiceImpl implements ShopDataStatisticService {
    @Resource
    private D01OrderDao d01OrderMapper;
    @Resource
    ShopOrderService shopOrderService;
    @Resource
    ShopGoodsService shopGoodsService;

    @Override
    public List<D01OrderDO> getListBy(EntityWrapper<D01OrderDO> ew) {
        return d01OrderMapper.selectList(ew);
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/8 10:40
     * @Param:
     * @return:
     * @Description: 数据统计
     */
    @Override
    public ResultVo DataStatistics() {
        Map<String,Object> map = new HashMap<>();
        ShopOrderStatisticsForm shopOrderStatisticsForm = new ShopOrderStatisticsForm();
        shopOrderStatisticsForm.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
        TimeTest tt = new TimeTest();
        //销售盘点
        //--今日订单统计/今日销售总额
        shopOrderStatisticsForm.setStartTime(tt.getNowTime("yyyy-MM-dd"));

        D01OrderDO order = shopOrderService.orderStatistic(shopOrderStatisticsForm);
        if (order != null){
            map.put("todayTotalOrders",order.getTotalOrders());
            map.put("todayTotalSales",order.getTotalSales());
        }
        //--昨日销售总额
        shopOrderStatisticsForm.setStartTime(tt.getyd());
        shopOrderStatisticsForm.setEndTime(tt.getNowTime("yyyy-MM-dd"));
        order = shopOrderService.orderStatistic(shopOrderStatisticsForm);
        if (order != null){
            map.put("yesterdayTotalSales",order.getTotalSales());
        }
        //--近7天销售总额
        shopOrderStatisticsForm.setStartTime(tt.getyd(7));
        shopOrderStatisticsForm.setEndTime(null);
        order = shopOrderService.orderStatistic(shopOrderStatisticsForm);
        if (order != null){
            map.put("sevenDaysTotalSales",order.getTotalSales());
        }
        //--本月销售总额
        shopOrderStatisticsForm.setStartTime(tt.getFirstDayOfMonth());
        shopOrderStatisticsForm.setEndTime(null);
        order = shopOrderService.orderStatistic(shopOrderStatisticsForm);
        if (order != null){
            map.put("thisMonthTotalSales",order.getTotalSales());
        }
        //商品总览
        //--商品总数
        map.put("totalGoods",shopGoodsService.goodsCount());
        //--今日新增
        ShopGoodsStatisticsForm shopGoodsStatisticsForm = new ShopGoodsStatisticsForm();
        shopGoodsStatisticsForm.setStartTime(tt.getNowTime("yyyy-MM-dd"));
        //shopGoodsStatisticsForm.setEndTime(tt.getNowTime("yyyy-MM-dd"));
        map.put("todayNewGoods",shopGoodsService.goodsStatistics(shopGoodsStatisticsForm));
        return ResultVOUtils.success(map);
    }
}


