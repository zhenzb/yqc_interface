package com.youqiancheng.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.otther.ShopCommonForm;
import com.youqiancheng.form.shop.otther.ShopPublicityPageForm;
import com.youqiancheng.mybatis.dao.F01ShopDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.GoodsPicService;
import com.youqiancheng.service.shop.ShopC13PublicityAutio;
import com.youqiancheng.service.shop.ShopPublicityService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/14 16:02
 * @Version: V1.0
 */
@Api(tags = "商家管理--动态(宣传)管理")
@RestController
@RequestMapping("shop/publicityManagement")
public class ShopPublicityManagementController {
    @Resource
    private ShopPublicityService shopPublicityService;
    @Resource
    private GoodsPicService goodsPicService;
    @Resource
    private F01ShopDao f01ShopDao;
    @Autowired
    private ShopC13PublicityAutio shopC13PublicityAutio;


    @ApiOperation(value = "动态（宣传）列表", notes = "动态（宣传）列表")
    @GetMapping(value = "/pagePublicity")
    public ResultVo<PageSimpleVO<C10PublicityDO>> pagePublicity(@Valid ShopPublicityPageForm form, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        map.put("shopId",shopUser.getShopId());
        List<C10PublicityDO> publicitylist = shopPublicityService.listPublicityPage(map);
        //封装到分页
        PageSimpleVO<C10PublicityDO> shopPublicityPageSimpleVO = new PageSimpleVO<>();
        shopPublicityPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopPublicityPageSimpleVO.setList(publicitylist);
        return ResultVOUtils.success(shopPublicityPageSimpleVO);
    }

    @ApiOperation(value = "查看详情", notes = "查看详情")
    @PostMapping(value = "/getPublicityById")
    public ResultVo getPublicityById(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        C10PublicityDO publicity = shopPublicityService.getPublicityById(shopCommonForm.getIds()[0]);
        F01ShopDO f01ShopDO = f01ShopDao.get(publicity.getShopId());
        if (publicity != null){
         /*   publicity.setCategoryIdName(shopPublicityService.getCategoryNameById(publicity.getCategoryId()));
            publicity.setSecondCategoryIdName(shopPublicityService.getCategoryNameById(publicity.getSecondCategoryId()));
            publicity.setThirdCategoryIdName(shopPublicityService.getCategoryNameById(publicity.getThirdCategoryId()));*/
         //回显图片
            EntityWrapper<C02GoodsPicDO> ew = new EntityWrapper<>();
            ew.eq("goods_id",publicity.getId());
            ew.eq("type",f01ShopDO.getType());
            ew.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
            ew.orderBy("carousel_sort");
            List<C02GoodsPicDO> list = goodsPicService.listGoodsPic(ew);
            if (!CollectionUtils.isEmpty(list)){
                String[] picArr = new String[list.size()];
                int i = 0;
                for (C02GoodsPicDO goodsPic:list){
                    picArr[i++] = goodsPic.getPicUrl();
                }
                publicity.setPicArr(picArr);
            }
          //回显音频，废弃了
            /*EntityWrapper<C13PublicityAutioDO> ewt =new EntityWrapper<>();
            ewt.eq("publicity_id",publicity.getId());
            ewt.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
            List<C13PublicityAutioDO> listAutio = shopC13PublicityAutio.listC13Autio(ewt);
            publicity.setAudiot(listAutio);*/
        }
        return ResultVOUtils.success(publicity);
    }

    @ApiOperation(value = "删除动态", notes = "删除动态")
    @PostMapping(value = "/delPublicity")
    public ResultVo delPublicity(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C10PublicityDO> publicitys = new ArrayList<>();
        for (long id:shopCommonForm.getIds()){
            C10PublicityDO publicity = shopPublicityService.getPublicityById(id);
            if (publicity != null) {
                //删除音频,废弃了
                //  int i = shopC13PublicityAutio.delete(publicity.getId());
                publicity.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                publicitys.add(publicity);
            }
        }
        return ResultVOUtils.success(shopPublicityService.batchUpdatePublicityById(publicitys));
    }



    @ApiOperation(value = "添加/编辑动态", notes = "添加/编辑动态")
    @PostMapping(value = "/saveOrUpdatePublicity")
    public ResultVo saveOrUpdatePublicity(@Valid C10PublicityDO publicity){
        if (publicity == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopPublicityService.saveOrUpdatePublicity(publicity));
    }


}


