package com.youqiancheng.service.manager.service.datastatistics;


import com.youqiancheng.vo.manager.*;

public interface DataStatisticsService {
    UserVO getUserInfo();

    ShopVO getShopInfo();

    GoodsVO getGoodsInfo();

    OrderVO getOrderInfo();

    SaleVO getSaleInfo();
}
