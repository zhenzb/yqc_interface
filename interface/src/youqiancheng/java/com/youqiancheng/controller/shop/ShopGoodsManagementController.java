package com.youqiancheng.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.otther.ShopGoodsForm;
import com.youqiancheng.form.shop.otther.ShopGoodsPageForm;
import com.youqiancheng.mybatis.dao.C09GoodsSkuDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.GoodsPicService;
import com.youqiancheng.service.shop.ShopGoodsService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 14:26
 * @Version: V1.0
 */
@Api(tags = "商家管理--商品管理")
@RestController
@RequestMapping("shop/goodsManagement")
public class ShopGoodsManagementController {

    @Resource
    private ShopGoodsService shopGoodsService;
    @Resource
    private GoodsPicService goodsPicService;
    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;


    @ApiOperation(value = "全部商品/已审核/已拒绝", notes = "全部商品/已审核/已拒绝")
    @GetMapping(value = "/pageGoods")
    public ResultVo<PageSimpleVO<C01GoodsDO>> pageGoods(@Valid ShopGoodsPageForm shopGoodsPageForm, @Valid EntyPage page ){

        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser != null){
            shopGoodsPageForm.setShopId(shopUser.getShopId());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("shopGoodsPageForm", shopGoodsPageForm);
        map.put("page", page);
        List<C01GoodsDO> goodslist = shopGoodsService.listGoodsHDPage(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> shopGoodsPageSimpleVO = new PageSimpleVO<>();
        shopGoodsPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopGoodsPageSimpleVO.setList(goodslist);
        return ResultVOUtils.success(shopGoodsPageSimpleVO);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @PostMapping(value = "/getGoodsById")
    public ResultVo getGoodsById(@Valid ShopGoodsForm shopGoodsForm){
        if(shopGoodsForm == null || ArrayUtils.isEmpty(shopGoodsForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        C01GoodsDO goods = shopGoodsService.getGoodsById(shopGoodsForm.getIds()[0]);
        if (goods != null){
            C03CategoryDO category = shopGoodsService.getById(goods.getCategoryId());
            if (category != null){
                goods.setCategoryIdName(category.getName());
            }
            category = shopGoodsService.getById(goods.getSecondCategoryId());
            if (category != null){
                goods.setSecondCategoryIdName(category.getName());
            }
            category = shopGoodsService.getById(goods.getThirdCategoryId());
            if (category != null){
                goods.setThirdCategoryIdName(category.getName());
            }
            EntityWrapper<C02GoodsPicDO> ew = new EntityWrapper<>();
            ew.eq("goods_id",goods.getId());
            ew.eq("type", TypeConstant.CoodsPicType.goods.getCode());
            ew.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
            ew.orderBy("carousel_sort");
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
                String[] picArr = new String[list.size()];
                int i = 0;
                for (C02GoodsPicDO goodsPic:list){
                    picArr[i++] = goodsPic.getPicUrl();
                }
                goods.setPicArr(picArr);
            }
            Map<String,Object> skuMap = new HashMap<>();
            skuMap.put("goodsId",goods.getId());
            skuMap.put("deleteFlag",0);
            List<C09GoodsSkuDO> list1 = c09GoodsSkuDao.list(skuMap);
            goods.setSkuList(list1);
        }
        return ResultVOUtils.success(goods);
    }

    @ApiOperation(value = "编辑商品", notes = "编辑商品")
    @PostMapping(value = "/editGoodsById")
    public ResultVo editGoodsById(@Valid C01GoodsDO goods){
        if(goods == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        if (shopGoodsService.getGoodsById(goods.getId()) == null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"商品不存在");
        }
        return ResultVOUtils.success(shopGoodsService.saveOrUpdateGoods(goods));
    }


    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping(value = "/addGoods")
    public ResultVo addGoods(@Valid C01GoodsDO goods){
        if(goods == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        shopGoodsService.saveOrUpdateGoods(goods);
        return ResultVOUtils.success(goods.getId());
    }

    @ApiOperation(value = "添加社交圈", notes = "添加社交圈")
    @PostMapping(value = "/addGoodsSocialCircle")
    public ResultVo addGoodsSocialCircle(@Valid C01GoodsSocialCircleDO goodsSocialCircle){
        if(goodsSocialCircle == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        C01GoodsDO goods = new C01GoodsDO();
        goods.setIntroduction(goodsSocialCircle.getIntroduction());
        goods.setPicArr(goodsSocialCircle.getPicArr());
        goods.setType(goodsSocialCircle.getType());
        if(goodsSocialCircle.getId() !=null){
            goods.setId(goodsSocialCircle.getId());
        }
        shopGoodsService.saveOrUpdateGoods(goods);
        return ResultVOUtils.success(goods.getId());
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @PostMapping(value = "/delGoods")
    public ResultVo delGoods(@Valid ShopGoodsForm shopGoodsForm){
        if(shopGoodsForm == null || ArrayUtils.isEmpty(shopGoodsForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C01GoodsDO> goodsList = new ArrayList<>();
        for (long id:shopGoodsForm.getIds()){
            C01GoodsDO goods = shopGoodsService.getGoodsById(id);
            if (goods != null){
                goods.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                goods.setUpdateTime(LocalDateTime.now());
                goodsList.add(goods);
            }
        }
        return ResultVOUtils.success(shopGoodsService.batchUpdateGoods(goodsList));
    }

    @ApiOperation(value = "上架/下架商品", notes = "上架/下架商品")
    @PostMapping(value = "upDownGoods")
    public ResultVo upDownGoods(@Valid ShopGoodsForm shopGoodsForm){
        if(shopGoodsForm == null || ArrayUtils.isEmpty(shopGoodsForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C01GoodsDO> goodsList = new ArrayList<>();
        for (long id:shopGoodsForm.getIds()){
            C01GoodsDO goods = shopGoodsService.getGoodsById(id);
            if (goods != null){
                goods.setIsSale(shopGoodsForm.getIsSale());
                goods.setUpdateTime(LocalDateTime.now());
                goodsList.add(goods);
            }
        }
        return ResultVOUtils.success(shopGoodsService.batchUpdateGoods(goodsList));
    }


    @ApiOperation(value = "根据ID获取商品分类")
    @PostMapping(value = "/getCategoryListByPid")
    public ResultVo getCategoryById(Long id){
        List<C03CategoryDO> categoryById = shopGoodsService.getCategoryById(id);
        return ResultVOUtils.success(categoryById);
    }

    @ApiOperation(value = "根据ID获取商品分类")
    @PostMapping(value = "/getCategoryList")
    public ResultVo getCategoryList(){
        List<C03CategoryDO> list = shopGoodsService.getCategoryList();
        return ResultVOUtils.success(list);
    }
}


