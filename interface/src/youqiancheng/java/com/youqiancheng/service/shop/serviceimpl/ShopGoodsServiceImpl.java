package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.youqiancheng.form.shop.otther.ShopGoodsStatisticsForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.GoodsPicService;
import com.youqiancheng.service.shop.ShopGoodsService;
import com.youqiancheng.util.QueryMap;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/13 14:33
 * @Version: V1.0
 */
@Log4j
@Service
@Transactional
public class ShopGoodsServiceImpl implements ShopGoodsService {

    @Resource
    private C01GoodsDao goodsDao;

    @Resource
    private C03CategoryDao  c03CategoryDao;

    @Resource
    private GoodsPicService goodsPicService;
    @Resource
    private C04GoodsExamineSetDao c04GoodsExamineSetDao;
    @Resource
    private F01ShopDao f01ShopDaO;

    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;



    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/13 14:33
     * @Param:
     * @return:
     * @Description:
     */

    //今日新增商品
    @Override
    public Integer goodsStatistics(ShopGoodsStatisticsForm shopGoodsStatisticsForm) {
        if (shopGoodsStatisticsForm != null){
            F08ShopUserDO shopUser = SecurityUtils.getShopUser();
            if (shopUser == null){
                throw new JsonException(Constants.$Null, "登录超时");
            }

            EntityWrapper<C01GoodsDO> ew = new EntityWrapper<>();
            ew.eq("shop_id",shopUser.getShopId());
            if (StringUtils.isNotBlank(shopGoodsStatisticsForm.getStartTime())){
                ew.ge("create_time",shopGoodsStatisticsForm.getStartTime());
            }
            if (StringUtils.isNotBlank(shopGoodsStatisticsForm.getEndTime())){
                ew.le("create_time",shopGoodsStatisticsForm.getEndTime());
            }
            return goodsDao.selectCount(ew);
        }
        return 0;
    }
  //商品总数
    @Override
    public Integer goodsCount() {
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        EntityWrapper<C01GoodsDO> ew = new EntityWrapper<>();
        ew.eq("shop_id",shopUser.getShopId());
        ew.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
        return goodsDao.selectCount(ew);
    }

    //商品列表
    @Override
    public List<C01GoodsDO> listGoodsHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return goodsDao.listGoodsHDPage(map);
    }

    //查看详情
    @Override
    public C01GoodsDO getGoodsById(long id) {
        return goodsDao.selectById(id);
    }

    //添加/编辑商品
    @Override
    public Integer saveOrUpdateGoods(C01GoodsDO goods) {
        Integer reBack = 0;
        if (goods == null){
            return reBack;
        }
        goods.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        goods.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        goods.setIsSale(StatusConstant.SaleFlag.un_sale.getCode());
        long shopId = goods.getShopId();
        if(shopId == 0){
            F08ShopUserDO shopUser = SecurityUtils.getShopUser();
            if (shopUser == null){
                throw new JsonException(Constants.$Null, "登录超时");
            }
            goods.setShopId(shopUser.getShopId());
            goods.setShopName(shopUser.getShopName());
        }else{
            F01ShopDO f01ShopDO = f01ShopDaO.get(shopId);
            goods.setShopId(goods.getShopId());
            goods.setShopName(f01ShopDO.getName());
        }
        if (goods.getId() == null){//添加
            goods.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            List<C04GoodsExamineSetDO> list = c04GoodsExamineSetDao.list(new QueryMap(StatusConstant.CreatFlag.delete.getCode()));
            if(!CollectionUtils.isEmpty(list)){
                C04GoodsExamineSetDO c04GoodsExamineSetDO = list.get(0);
                if(2==c04GoodsExamineSetDO.getExamineFlag()){
                    goods.setExamineStatus(StatusConstant.ExamineStatus.adopt.getCode());
                }
            }
            goods.setCreateTime(LocalDateTime.now());
            goods.setIsSale(StatusConstant.SaleFlag.un_sale.getCode());
            if(goods.getIcon() == null && goods.getPicArr().length !=0){
                goods.setIcon(goods.getPicArr()[0]);
            }
            //获取店铺的分类数据
//            if(goods.getShopId() !=0){
//                F01ShopDO f01ShopDO = f01ShopDaO.get(goods.getShopId());
//
//            }
            reBack = goodsDao.insert(goods);
            //新增属性
            //addGoodsSku(goods);

            String[] picArr = goods.getPicArr();
            if (!ArrayUtils.isEmpty(picArr)){
                int i = 0;
                for (String picUrl:picArr){
                    C02GoodsPicDO goodsPic = new C02GoodsPicDO();
                    goodsPic.setGoodsId(goods.getId());
                    goodsPic.setPicUrl(picUrl);
                    goodsPic.setCarouselSort(i++);
                    goodsPic.setType(StatusConstant.ShopType.product.getCode());
                    goodsPic.setCreateTime(LocalDateTime.now());
                    goodsPicService.saveOrUpdateGoodsPic(goodsPic);
                }
            }
            return reBack;
        }
        //编辑
        String[] picArr = goods.getPicArr();
        if (!ArrayUtils.isEmpty(picArr)){
            //先删除原图，再添加新图
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew = new EntityWrapper<>();
            ew.eq("goods_id",goods.getId());
            ew.eq("type",StatusConstant.ShopType.product.getCode());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
                goods.setIcon(picArr[0]);
                List<C02GoodsPicDO> dellist = new ArrayList<>();
                for (C02GoodsPicDO goodsPic:list){
                    goodsPic.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                    goodsPic.setUpdateTime(LocalDateTime.now());
                    dellist.add(goodsPic);
                }
                //删原图
                goodsPicService.batchUpdateGoodsPicById(dellist);
            }
            //新增新图
            int i = 0;
            for (String picUrl:picArr){
                C02GoodsPicDO goodsPic = new C02GoodsPicDO();
                goodsPic.setGoodsId(goods.getId());
                goodsPic.setPicUrl(picUrl);
                goodsPic.setCarouselSort(i++);
                goodsPic.setType(StatusConstant.ShopType.product.getCode());
                goodsPic.setCreateTime(LocalDateTime.now());
                goodsPicService.saveOrUpdateGoodsPic(goodsPic);
            }
        }
        //删除原有属性
        Map<String,Object> skuMap = new HashMap<>();
        skuMap.put("goods_id",goods.getId());
        c09GoodsSkuDao.deleteByMap(skuMap);
        //新增商品属性
        addGoodsSku(goods);
        goods.setUpdateTime(LocalDateTime.now());

        return goodsDao.updateById(goods);
    }

    /**
     * 添加商品属性
     * @param goods
     */
    private void addGoodsSku(C01GoodsDO goods) {
        try {
            if(goods.getGoods_desc() !=null && !goods.getGoods_desc().equals("")){
                JSONArray jsonArray = JSONArray.fromObject(goods.getGoods_desc());
                if(jsonArray.size()>0){
                    for(int i=0;i<jsonArray.size();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        C09GoodsSkuDO c09GoodsSkuDO = new C09GoodsSkuDO();
                        c09GoodsSkuDO.setGoodsId(goods.getId());
                        c09GoodsSkuDO.setGoodsDesc(jsonObject.getString("sx"));
                        c09GoodsSkuDO.setGoodsPrice(new BigDecimal(jsonObject.getString("goodsPrice")));
                        c09GoodsSkuDO.setNum(jsonObject.getInt("num"));
                        c09GoodsSkuDO.setCreateTime(LocalDateTime.now());
                        c09GoodsSkuDO.setUpdateTime(LocalDateTime.now());
                        c09GoodsSkuDao.insert(c09GoodsSkuDO);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
       String str = "[{\"sx\":\"1\",\"num\":\"1\",\"goodsPrice\":\"1\"},{\"sx\":\"2\",\"num\":\"2\",\"goodsPrice\":\"2\"}]";
        //JSONObject jsonObject = JSONObject.fromObject(str);
        JSONArray jsonArray = JSONArray.fromObject(str);

    }

    @Override
    public Integer batchUpdateGoods(List<C01GoodsDO> goods) {
        if (CollectionUtils.isEmpty(goods)){
            return 0;
        }
        return goodsDao.updateList(goods);
    }

    @Override
    public List<C03CategoryDO> getCategoryById(Long id) {
        List<C03CategoryDO> c03CategoryDOS = c03CategoryDao.selectList(
                new EntityWrapper<C03CategoryDO>().eq("parent_id",id).eq("status",1)
        );
        Collections.sort(c03CategoryDOS, Comparator.comparingInt(C03CategoryDO::getOrderNum));
        return c03CategoryDOS;
    }

    @Override
    public C03CategoryDO getById(Long id) {
        return c03CategoryDao.selectById(id);
    }

    @Override
    public List<C03CategoryDO> getCategoryList() {
        List<C03CategoryDO> c03CategoryDOS = c03CategoryDao.list(new HashMap<>());
        return c03CategoryDOS;
    }

    @Override
    public List<C03CategoryDO> listCategoryListBy(EntityWrapper<C03CategoryDO> ew) {
        return c03CategoryDao.selectList(ew);
    }
}


