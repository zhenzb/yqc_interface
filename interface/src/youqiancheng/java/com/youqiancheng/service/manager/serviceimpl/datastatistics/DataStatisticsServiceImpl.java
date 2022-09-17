package com.youqiancheng.service.manager.serviceimpl.datastatistics;

import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.D01OrderDao;
import com.youqiancheng.mybatis.dao.F01ShopDao;
import com.youqiancheng.service.manager.service.datastatistics.DataStatisticsService;
import com.youqiancheng.vo.manager.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DataStatisticsServiceImpl implements DataStatisticsService {
    @Resource
    private B01UserDao userDao;
    @Resource
    private F01ShopDao shopDao;
    @Resource
    private C01GoodsDao goodsDao;
    @Resource
    private D01OrderDao orderDao;
    /**
    　* @Description: 获取用户数据
    　* @author shalongteng
    　* @date 2020/4/7 14:16
    　*/
    @Override
    public UserVO getUserInfo() {
        Long userForDay = userDao.selectForDay();
        Long userForMonth = userDao.selectForMonth();
        Long userForAll = userDao.selectForAll();
        UserVO userVO = new UserVO(userForDay, userForMonth, userForAll);
        return userVO;
    }
    /**
    　* @Description:获取商家数据
    　* @author shalongteng
    　* @date 2020/4/7 14:47
    　*/
    @Override
    public ShopVO getShopInfo() {
        Long shoprForDay = shopDao.selectForDay();
        Long shopForMonth = shopDao.selectForMonth();
        Long shoprForAll = shopDao.selectForAll();

        ShopVO shopVO = new ShopVO(shoprForDay, shopForMonth, shoprForAll);
        return shopVO;
    }
    /**
    　* @Description: 获取商品数据
    　* @author shalongteng
    　* @date 2020/4/7 15:08
    　*/
    @Override
    public GoodsVO getGoodsInfo() {
        Long goodsForDay = goodsDao.selectForDay();
        Long goodsForMonth = goodsDao.selectForMonth();
        Long goodsForAll = goodsDao.selectForAll();

        GoodsVO goodsVO = new GoodsVO(goodsForDay, goodsForMonth, goodsForAll);
        return goodsVO;
    }
    /**
    　* @Description: 获取订单数据
    　* @author shalongteng
    　* @date 2020/4/7 16:02
    　*/
    @Override
    public OrderVO getOrderInfo() {
        Long goodsForDay = orderDao.selectForDay();
        Long goodsForWeek = orderDao.selectForWeek();
        Long goodsForMonth = orderDao.selectForMonth();

        OrderVO orderVO = new OrderVO(goodsForDay, goodsForWeek, goodsForMonth);
        return orderVO;
    }
    /**
    　* @Description: 获取销售额数据
    　* @author shalongteng
    　* @date 2020/4/7 16:17
    　*/
    @Override
    public SaleVO getSaleInfo() {
        Long saleForDay = orderDao.selectSaleForDay();
        Long saleForWeek = orderDao.selectSaleForWeek();
        Long saleForMonth = orderDao.selectSaleForMonth();

        SaleVO saleVO = new SaleVO(saleForDay==null?0:saleForDay, saleForWeek, saleForMonth);
        return saleVO;
    }
}
