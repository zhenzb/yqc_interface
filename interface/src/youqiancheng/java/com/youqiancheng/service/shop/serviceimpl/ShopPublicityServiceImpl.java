package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.GoodsPicService;
import com.youqiancheng.service.shop.ShopC13PublicityAutio;
import com.youqiancheng.service.shop.ShopPublicityService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 16:22
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopPublicityServiceImpl implements ShopPublicityService {
    @Resource
    private C10PublicityDao publicityDao;
    @Resource
    private GoodsPicService goodsPicService;
    @Autowired
    private C03CategoryDao c03CategoryDao;
 @Autowired
    private F01ShopDao f01ShopDao;
 @Resource
 private C04GoodsExamineSetDao  c04GoodsExamineSetDao;

 @Autowired
 private C13PublicityAutioDao c13PublicityAutioDao;

    @Autowired
    private ShopC13PublicityAutio shopC13PublicityAutio;

    @Override
    public List<C10PublicityDO> listPublicityPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return publicityDao.listByShopManageHDPage(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return publicityDao.count(map);
    }

    @Override
    public C10PublicityDO getPublicityById(long id) {
        return publicityDao.selectById(id);
    }

  /*  @Override
    public String getCategoryNameById(long id) {
        C03CategoryDO c03CategoryDO = c03CategoryDao.get(id);
        if(c03CategoryDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到对应的分类信息");
        }
        return c03CategoryDO.getName();
    }*/

    @Override
    public Integer batchUpdatePublicityById(List<C10PublicityDO> publicitys) {
        return publicityDao.updateList(publicitys);
    }

    @Override
    public Integer saveOrUpdatePublicity(C10PublicityDO publicity) {
        Integer reBack = 0;
        if (publicity == null){
            return reBack;
        }
        //获取登录里的商家用户
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        F01ShopDO f01ShopDO = f01ShopDao.get(shopUser.getShopId());
        publicity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (publicity.getId() == 0){//添加
            if(StatusConstant.PublicityType.video.getCode()==publicity.getType()){
                QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map.put("shopId",shopUser.getShopId());
                map.put("type",StatusConstant.PublicityType.video.getCode());
                int count = publicityDao.count(map);
                if(count>0){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"视频只能上传一条");
                }
            }
            publicity.setShopId(shopUser.getShopId());
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            publicity.setCreateTime(LocalDateTime.now());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            publicity.setShopName(f01ShopDO.getName());
            reBack = publicityDao.insert(publicity);

            String[] picArr = publicity.getPicArr();
            if (!ArrayUtils.isEmpty(picArr)){
                int i = 0;
                for (String picUrl:picArr){
                    C02GoodsPicDO goodsPic = new C02GoodsPicDO();
                    goodsPic.setGoodsId(publicity.getId());
                    goodsPic.setPicUrl(picUrl);
                    goodsPic.setCarouselSort(i++);
                    goodsPic.setType(f01ShopDO.getType());
                    goodsPic.setCreateTime(LocalDateTime.now());
                    goodsPicService.saveOrUpdateGoodsPic(goodsPic);
                }
            }
            return reBack;
        }
        //编辑
        String[] picArr = publicity.getPicArr();
        if (!ArrayUtils.isEmpty(picArr)){
            //先删除原图，再添加新图
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew =new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
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
                goodsPic.setGoodsId(publicity.getId());
                goodsPic.setPicUrl(picUrl);
                goodsPic.setCarouselSort(i++);
                goodsPic.setType(f01ShopDO.getType());
                goodsPic.setCreateTime(LocalDateTime.now());
                goodsPicService.saveOrUpdateGoodsPic(goodsPic);
            }
            publicity.setExamineTime(LocalDateTime.now());
            publicity.setUpdateTime(LocalDateTime.now());
            publicity.setShopId(f01ShopDO.getId());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            //把状态改为未审核
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            return publicityDao.updateById(publicity);

            //假如前端就没有传图片的时候
        }else{
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew =new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
                List<C02GoodsPicDO> dellist = new ArrayList<>();
                for (C02GoodsPicDO goodsPic:list){
                    goodsPic.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                    goodsPic.setUpdateTime(LocalDateTime.now());
                    dellist.add(goodsPic);
                }
                //删原图
                goodsPicService.batchUpdateGoodsPicById(dellist);
            }
            publicity.setExamineTime(LocalDateTime.now());
            publicity.setUpdateTime(LocalDateTime.now());
            publicity.setShopId(f01ShopDO.getId());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            //把状态改为未审核
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            return publicityDao.updateById(publicity);
        }

    }

    @Override
    public Integer saveOrUpdatePublicity2(C10PublicityDO publicity) {
        Integer reBack = 0;
        if (publicity == null){
            return reBack;
        }
        F01ShopDO f01ShopDO = f01ShopDao.get(publicity.getShopId());
        publicity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (publicity.getId() == 0){//添加
            if(StatusConstant.PublicityType.video.getCode()==publicity.getType()){
                QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map.put("shopId",publicity.getShopId());
                map.put("type",StatusConstant.PublicityType.video.getCode());
                int count = publicityDao.count(map);
                if(count>0){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"视频只能上传一条");
                }
            }
            publicity.setShopId(publicity.getShopId());
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            publicity.setCreateTime(LocalDateTime.now());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            publicity.setShopName(f01ShopDO.getName());
            reBack = publicityDao.insert(publicity);

            String[] picArr = publicity.getPicArr();
            if (!ArrayUtils.isEmpty(picArr)){
                int i = 0;
                for (String picUrl:picArr){
                    C02GoodsPicDO goodsPic = new C02GoodsPicDO();
                    goodsPic.setGoodsId(publicity.getId());
                    goodsPic.setPicUrl(picUrl);
                    goodsPic.setCarouselSort(i++);
                    goodsPic.setType(f01ShopDO.getType());
                    goodsPic.setCreateTime(LocalDateTime.now());
                    goodsPicService.saveOrUpdateGoodsPic(goodsPic);
                }
            }
            return reBack;
        }
        //编辑
        String[] picArr = publicity.getPicArr();
        if (!ArrayUtils.isEmpty(picArr)){
            //先删除原图，再添加新图
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew =new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
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
                goodsPic.setGoodsId(publicity.getId());
                goodsPic.setPicUrl(picUrl);
                goodsPic.setCarouselSort(i++);
                goodsPic.setType(f01ShopDO.getType());
                goodsPic.setCreateTime(LocalDateTime.now());
                goodsPicService.saveOrUpdateGoodsPic(goodsPic);
            }
            publicity.setExamineTime(LocalDateTime.now());
            publicity.setUpdateTime(LocalDateTime.now());
            publicity.setShopId(f01ShopDO.getId());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            //把状态改为未审核
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            return publicityDao.updateById(publicity);

            //假如前端就没有传图片的时候
        }else{
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew =new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
                List<C02GoodsPicDO> dellist = new ArrayList<>();
                for (C02GoodsPicDO goodsPic:list){
                    goodsPic.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                    goodsPic.setUpdateTime(LocalDateTime.now());
                    dellist.add(goodsPic);
                }
                //删原图
                goodsPicService.batchUpdateGoodsPicById(dellist);
            }
            publicity.setExamineTime(LocalDateTime.now());
            publicity.setUpdateTime(LocalDateTime.now());
            publicity.setShopId(f01ShopDO.getId());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            //把状态改为未审核
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            return publicityDao.updateById(publicity);
        }
    }


    //因需求变动而对上面的改动-------------------------------------------------------------------------------
    /* @Override
    public Integer saveOrUpdatePublicity(C10PublicityDO publicity) {
        Integer reBack = 0;
        if (publicity == null){
            return reBack;
        }
        //获取登录里的商家用户
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        F01ShopDO f01ShopDO = f01ShopDao.get(shopUser.getShopId());
        publicity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        if (publicity.getId() == 0){//添加
            if(StatusConstant.PublicityType.video.getCode()==publicity.getType()){
                QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
                map.put("shopId",shopUser.getShopId());
                map.put("type",StatusConstant.PublicityType.video.getCode());
                int count = publicityDao.count(map);
                if(count>0){
                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"视频只能上传一条");
                }
            }
            publicity.setShopId(shopUser.getShopId());
            publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
            publicity.setCreateTime(LocalDateTime.now());
            //获取商家的类型
            publicity.setType(f01ShopDO.getType());
            publicity.setShopName(f01ShopDO.getName());
            reBack = publicityDao.insert(publicity);

            String[] picArr = publicity.getPicArr();
            if (!ArrayUtils.isEmpty(picArr)){
                int i = 0;
                for (String picUrl:picArr){
                    C02GoodsPicDO goodsPic = new C02GoodsPicDO();
                    goodsPic.setGoodsId(publicity.getId());
                    goodsPic.setPicUrl(picUrl);
                    goodsPic.setCarouselSort(i++);
                    goodsPic.setType(f01ShopDO.getType());
                    goodsPic.setCreateTime(LocalDateTime.now());
                    goodsPicService.saveOrUpdateGoodsPic(goodsPic);
                }
            }
           //添加音频
            List<C13PublicityAutioDO> audio = publicity.getAudiot();
            if(audio!=null && audio.size()>=0){
              //不为空，则添加音频
                for (C13PublicityAutioDO c13:audio ){
                    C13PublicityAutioDO c13PublicityAutioDO = new C13PublicityAutioDO();
                    c13PublicityAutioDO.setShopId(shopUser.getShopId());
                    c13PublicityAutioDO.setPublicityId(publicity.getId());
                    c13PublicityAutioDO.setAutioName(c13.getAutioName());
                    c13PublicityAutioDO.setAutioUrl(c13.getAutioUrl());
                    c13PublicityAutioDO.setCreatePerson(shopUser.getShopName());
                    c13PublicityAutioDO.setCreateTime(LocalDateTime.now());
                    c13PublicityAutioDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                    c13PublicityAutioDao.insert(c13PublicityAutioDO);
                }


            }
            return reBack;
        }
        //编辑
        String[] picArr = publicity.getPicArr();
        if (!ArrayUtils.isEmpty(picArr)){
            //先删除原图，再添加新图
            //获取原图
            EntityWrapper<C02GoodsPicDO> ew =new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
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
                goodsPic.setGoodsId(publicity.getId());
                goodsPic.setPicUrl(picUrl);
                goodsPic.setCarouselSort(i++);
                goodsPic.setType(f01ShopDO.getType());
                goodsPic.setCreateTime(LocalDateTime.now());
                goodsPicService.saveOrUpdateGoodsPic(goodsPic);
            }
        }

        //编辑音频
       *//*  List<C13PublicityAutioDO> audio = publicity.getAudiot();
         if(audio!=null && audio.size()>=0){
             //不为空， 先删除原来的音频，再添加新的音频
             //先获取
             EntityWrapper<C13PublicityAutioDO> ew =new EntityWrapper<>();
             ew.eq("publicity_id",publicity.getId());
             ew.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
             List<C13PublicityAutioDO> list = shopC13PublicityAutio.listC13Autio(ew);
             if (!CollectionUtils.isEmpty(list)){
                 List<C13PublicityAutioDO> delC13llist = new ArrayList<>();
                 for (C13PublicityAutioDO c13Autio:list){
                     c13Autio.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                     c13Autio.setUpdateTime(LocalDateTime.now());
                     delC13llist.add(c13Autio);
                 }
                 //删原来的音频
                 c13PublicityAutioDao.updateList(delC13llist);
             }

            //增加新的音频
             for (C13PublicityAutioDO c13:audio ){
                 C13PublicityAutioDO c13PublicityAutioDO = new C13PublicityAutioDO();
                 c13PublicityAutioDO.setShopId(shopUser.getShopId());
                 c13PublicityAutioDO.setPublicityId(publicity.getId());
                 c13PublicityAutioDO.setAutioName(c13.getAutioName());
                 c13PublicityAutioDO.setAutioUrl(c13.getAutioUrl());
                 c13PublicityAutioDO.setUpdatePerson(shopUser.getShopName());
                 c13PublicityAutioDO.setUpdateTime(LocalDateTime.now());
                 c13PublicityAutioDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
                 c13PublicityAutioDao.insert(c13PublicityAutioDO);
             }
         }*//*

     //编辑宣传
        publicity.setExamineTime(LocalDateTime.now());
        publicity.setUpdateTime(LocalDateTime.now());
        publicity.setShopId(f01ShopDO.getId());
        //获取商家的类型
        publicity.setType(f01ShopDO.getType());
        //把状态改为未审核
        publicity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        return publicityDao.updateById(publicity);
    }*/
}


