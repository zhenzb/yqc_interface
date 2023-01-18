package com.youqiancheng.controller.client;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.*;
import com.youqiancheng.mybatis.dao.C09GoodsSkuDao;
import com.youqiancheng.mybatis.dao.D05EvaluatePicDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.C01GoodsClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"PC端-商品详情"})
@RestController
@RequestMapping(value = "pc/goods")
public class GoodsDetailClientController {
    @Autowired
    private C01GoodsClientService c01GoodsService;
    @Autowired
    private F01ShopClientService f01ShopClientService;
    @Autowired
    private D04GoodsEvaluateClientService d04GoodsEvaluateService;
    @Autowired
    private C04GoodsExamineSetClientService c04GoodsExamineSetService;
    @Autowired
    private B05CollectionClientService b05CollectionService;
    @Autowired
    private B04ShoppingCartClientService b04ShoppingCartService;
    @Resource
    private D05EvaluatePicDao d05EvaluatePicDao;

    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;

    @ApiOperation(value = "根据商品ID获取商品详情信息;参数——商品ID")
    @GetMapping("/getGoodsInfoById")
    ResultVo getGoodsInfoById(Long id,Long userId){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商品ID不能为空和0");
        }
        C01GoodsClientVO c01Goods = c01GoodsService.get(id);
        if(c01Goods==null){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到商品信息");
        }
        F01ShopDO f01ShopDO = f01ShopClientService.get(c01Goods.getShopId());
        if(f01ShopDO==null){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到商品关联的商家信息");
        }
        c01Goods.setProvince(f01ShopDO.getProvince());
        c01Goods.setCity(f01ShopDO.getCity());
        c01Goods.setArea(f01ShopDO.getArea());
        c01Goods.setShopLogo(f01ShopDO.getLogo());
        Map<String,Object> skuMap = new HashMap<>();
        skuMap.put("goodsId",id);
        skuMap.put("deleteFlag",0);
        List<C09GoodsSkuDO> list1 = c09GoodsSkuDao.list(skuMap);
        c01Goods.setSkuList(list1);
        B05CollectionSearchCountForm form = new B05CollectionSearchCountForm();
        form.setRelationId(id);
        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.goods.getCode());
        map.put("userId",userId);
        int count = b05CollectionService.count(map);
        c01Goods.setSpsc(count);
        return ResultVOUtils.success(c01Goods);
    }



    @ApiOperation(value = "根据商品ID获取商品规格信息;参数——商品ID")
    @GetMapping("/getSelectAttribute")
    ResultVo getSelectAttribute(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商品ID不能为空和0");
        }
        List<C08SelectAttributeDO> selectAttribute = c01GoodsService.getSelectAttribute(id);
        return ResultVOUtils.success(selectAttribute);
    }

    @ApiOperation(value = "根据商品ID和规格获取取库存;参数——商品ID，规格")
    @GetMapping("/getSku")
    ResultVo getSku(C09GoodsSkuSearchForm dto) {
        C09GoodsSkuDO selectAttribute = c01GoodsService.getSku(dto);
        return ResultVOUtils.success(selectAttribute);
    }
    @ApiOperation(value = "保存购物车记录;参数——商品信息，数量，总价")
    @PostMapping("/saveShoppingCart")
    //@AuthRuleAnnotation()
    ResultVo saveShoppingCart(@RequestBody @Valid B04ShoppingCartSaveForm dto  ) {

        B04ShoppingCartDO    b04ShoppingCart =new B04ShoppingCartDO();
        BeanUtils.copyProperties(dto,b04ShoppingCart);
        b04ShoppingCart.setUpdatePerson(dto.getCreatePerson());
        b04ShoppingCart.setCreateTime(LocalDateTime.now());
        b04ShoppingCart.setUpdateTime(LocalDateTime.now());
        b04ShoppingCart.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b04ShoppingCartService.insert(b04ShoppingCart);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "获取商品评价记录列表;参数——商品ID，分页参数（不传则默认查询全部））")
    @GetMapping("/getEvaluateByGoodsId")
    ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> getEvaluateByGoodsId(@Valid D04GoodsEvaluateSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<D04GoodsEvaluateDO> d04GoodsEvaluateList = d04GoodsEvaluateService.listHDPage(map);
        for(D04GoodsEvaluateDO d04:d04GoodsEvaluateList){
            if(d04.getHasPic() ==2){
                Map<String,Object> d05Map = new HashMap<>();
                d05Map.put("evaluateId",d04.getId());
                List<D05EvaluatePicDO> list = d05EvaluatePicDao.list(d05Map);
                d04.setD05EvaluatePicDO(list);
            }
        }
        //封装到分页
        PageSimpleVO<D04GoodsEvaluateDO> d04GoodsEvaluateSimpleVO = new PageSimpleVO<>();
        d04GoodsEvaluateSimpleVO.setTotalNumber(page.getTotalNumber());
        d04GoodsEvaluateSimpleVO.setList(d04GoodsEvaluateList);

        return ResultVOUtils.success(d04GoodsEvaluateSimpleVO);
    }
//    @ApiOperation(value = "根据ID获取用户商品评价记录记录")
//    @GetMapping("/getGoodsEvaluateInfoById")
//    ResultVo getGoodsEvaluateInfoById(Long id ){
//        D04GoodsEvaluateClientVO d04GoodsEvaluate = d04GoodsEvaluateService.get(id);
//        return ResultVOUtils.success(d04GoodsEvaluate);
//    }
//    @ApiOperation(value = "根据ID获取用户商品评价记录计数")
//    @GetMapping("/getGoodsEvaluateCount")
//    ResultVo getGoodsEvaluateCount(Long id ){
//        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//        int count = d04GoodsEvaluateService.count(map);
//        return ResultVOUtils.success(count);
//    }

    @ApiOperation(value = "获取购物须知")
    @GetMapping("/getBuyNeedKnow")
    ResultVo getBuyNeedKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<C04GoodsExamineSetDO> list = c04GoodsExamineSetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到购物须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }

    @ApiOperation(value = "收藏商品；参数——商品ID，用户ID")
    @PostMapping("/collectionGoods")
    //@AuthRuleAnnotation()
    ResultVo collectionGoods(@RequestBody @Valid B05CollectionSaveForm dto ) {

        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.goods.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b05CollectionService.insert(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"收藏商品失败");
        }
        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "获取商品被收藏数；参数——商品ID")
    @GetMapping("/getCollectionGoodsCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getGoodsCount(@Valid B05CollectionSearchCountForm form) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.goods.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "查询商品是否被收藏——0为未收藏;其他都为收藏；参数——商品ID，用户ID")
    @GetMapping("/getIsCollectionGoods")
    @AuthRuleAnnotation()
    ResultVo  getIsCollectionGoods(@Valid B05CollectionSearchIsForm  form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.goods.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "收藏商家；参数——商家ID，用户ID")
    @PostMapping("/collectionShop")
    @AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody  @Valid B05CollectionSaveForm dto ) {

        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b05CollectionService.insert(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "取消收藏商家；参数——商家ID，用户ID")
    @PostMapping("/cancelCollectionShop")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody @Valid B05CollectionSaveForm dto ) {

        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num=b05CollectionService.delete(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"取消收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "取消收藏商品；参数——商品ID，用户ID")
    @PostMapping("/cancelCollectionGoods")
    //@ApiImplicitParams({@ApiImplicitParam(paramType = "body",name ="dto", value ="收藏参数",type = "B05CollectionSaveForm",required = true)})
    //@AuthRuleAnnotation()
    ResultVo cancelCollectionGoods(@RequestBody @Valid B05CollectionSaveForm dto ) {

        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.goods.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num=b05CollectionService.delete(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"取消收藏商品失败");
        }
        return ResultVOUtils.success(num);
    }

}
