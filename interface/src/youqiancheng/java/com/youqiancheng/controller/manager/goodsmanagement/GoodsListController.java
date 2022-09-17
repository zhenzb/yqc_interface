package com.youqiancheng.controller.manager.goodsmanagement;

import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.Goods.GoodsDeleteForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.shop.otther.ShopGoodsPageForm;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 商品管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_goods/goods")
@Api(tags = "总管理后台-商品管理")
public class GoodsListController {
    private static final Log log = LogFactory.getLog(GoodsListController.class);

    @Resource
    private GoodsListService goodsListService;

    /**
    　* @Description: 获取商品列表
    　* @author shalongteng
    　* @date 2020/4/8 9:11
    　*/
    @ApiOperation(value = "获取商品列表", notes = "获取商品列表")
    @GetMapping("listGoods")
    public ResultVo<PageSimpleVO<C01GoodsDO>> listShop(@Valid ShopGoodsPageForm shopGoodsPageForm   ,
                                                       @Valid EntyPage page) {


        Map<String, Object> map = new HashMap<>();
        shopGoodsPageForm.setType(3);
        map.put("shopGoodsPageForm", shopGoodsPageForm);
        map.put("page", page);
        List<C01GoodsDO> authAdminList = goodsListService.listGoodsHDPage(map);

        //封装到分页
        PageSimpleVO<C01GoodsDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }



    /**
    　* @Description: 批量删除商品
    　* @author shalongteng
    　* @date 2020/4/16 14:24
    　*/
    @ApiOperation(value = "批量删除商品", notes = "批量删除商品")
    @PostMapping("deleteGoods")
    public ResultVo deleteGoods(@RequestBody List<GoodsDeleteForm>  goodsDeleteFormList) {
        if(goodsDeleteFormList == null || goodsDeleteFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = goodsListService.deleteGoods(goodsDeleteFormList);
            if(integer >= 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
    }

    /**
     　* @Description: 批量通过拒绝
     　* @author shalongteng
     　* @date 2020/4/13 14:41
     　*/
    @ApiOperation(value = "批量通过拒绝", notes = "批量通过拒绝")
    @PostMapping("batchPassRefuse")
    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = goodsListService.batchPassRefuse(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }
    /**
    　* @Description:批量上架下架
    　* @author shalongteng
    　* @date 2020/4/16 11:28
    　*/
    @ApiOperation(value = "批量上架下架", notes = "批量上架下架")
    @PostMapping("batchUpDown")
    public ResultVo batchUpDown(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = goodsListService.batchUpDown(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }
    /**
    　* @Description:编辑商品回显接口
    　* @author shalongteng
    　* @date 2020/4/16 10:34
    　*/
    @ApiOperation(value = "查看商品:商品ID")
    @GetMapping("getGoodsInfo")
    public ResultVo getGoodsInfo(@ApiParam(name = "id", value = "商品id", required = true) @RequestParam("id")  Long id) {

        C01GoodsDO c01GoodsDO= goodsListService.getEditGoods(id);

        return ResultVOUtils.success(c01GoodsDO);
    }
}
