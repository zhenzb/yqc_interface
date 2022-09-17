package com.youqiancheng.controller.app;//package com.youqiancheng.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.*;
import com.youqiancheng.form.app.C09GoodsSkuSearchForm;
import com.youqiancheng.form.shop.*;
import com.youqiancheng.form.shop.otther.ShopGoodsForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.service.shop.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.C01GoodsAppVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"手机端-商品详情"})
@RestController
@RequestMapping(value = "app/goods")
public class GoodsDetailAppController {
    @Autowired
    private C01GoodsAppService c01GoodsService;
    @Autowired
    private F01ShopAppService f01ShopAppService;
    @Autowired
    private B05CollectionAppService b05CollectionService;
    @Autowired
    private B04ShoppingCartAppService b04ShoppingCartService;
    @Autowired
    private D04GoodsEvaluateAppService d04GoodsEvaluateService;
    @Resource
    private ShopGoodsService shopGoodsService;
    @Resource
    private ShopGoodsSkuService shopGoodsSkuService;
    @Resource
    private ShopAttributeProjectService shopAttributeProjectService;
    @Resource
    private ShopSelectProjectService shopSelectProjectService;
    @Resource
    private ShopSelectAttributeService shopSelectAttributeService;
    @Resource
    private ShopEvaluateService shopEvaluateService;
    @Resource
    private CustomerServiceService customerServiceService;

    /**
     * @api {GET} /app/goods/getGoodsInfoById 006获取商品详情接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} id 商品Id [必填]
     * @apiDescription APP- 获取商品详情接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "id": 493, 商品ID
     *         "categoryId": 1,
     *         "secondCategoryId": 116,
     *         "thirdCategoryId": 483,
     *         "name": "莴笋", 商品名称
     *         "price": 80, 商品单价
     *         "stock": 1000, 商品库存
     *         "goodsDesc": "<p>莴笋简介</p>", 商品详情
     *         "saleNum": 29,
     *         "isSale": 2, 上架状态： 2：上架 3：下架
     *         "examineStatus": 2, 审核状态 1:待审核 2：已通过 3：已拒绝
     *         "shopId": 232,
     *         "shopName": "158的店铺",
     *         "icon": "http://client.youqiancheng.vip/files/21/43/1597993553617.jpg", 商品缩略图
     *         "browseVolume": 73,
     *         "createPerson": null,
     *         "createTime": "2020-08-21 15:06:00", 发布时间
     *         "updatePerson": null,
     *         "updateTime": "2020-08-21 15:07:08",
     *         "deleteFlag": 1,
     *         "comprehensiveSort": 0,
     *         "evaluateSort": 0,
     *         "type": "1",
     *         "goodsNo": "001", 商品编号
     *         "collectionVolume": null,
     *         "picList": [ 商品轮播图
     *             "http://client.youqiancheng.vip/files/77/52/1597993556337.jpg"
     *         ],
     *         "province": "北京市",
     *         "city": "北京市",
     *         "area": "东城区",
     *         "shopLogo": "http://client.youqiancheng.vip/files/49/98/1606269265579.jpg",
     *         "evaluateCount": 2,
     *         "goodsType": null,
     *         "userId": 409,
     *         "introduction": "商品简介", 商品简介
     *         "categoryIdName": "中国",  一级分类
     *         "secondCategoryIdName": "餐饮（实体）", 二级分类
     *         "thirdCategoryIdName": null 三级分类
     *         "reason": null 拒绝原因
     *     }
     * }
     */
    @ApiOperation(value = "根据商品ID获取商品详情;参数——商品ID")
    @GetMapping("/getGoodsInfoById")
    ResultVo getGoodsInfoById(Long id) {
        if (id == null || id == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "参数不能为空或者0");
        }
        C01GoodsAppVO c01Goods = c01GoodsService.get(id);
        if (c01Goods == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "未查询到商品信息");
        }
        F01ShopDO f01ShopDO = f01ShopAppService.get(c01Goods.getShopId());
        if (f01ShopDO == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "未查询到商品关联的商家信息");
        }
        if (c01Goods != null) {
            C03CategoryDO category = shopGoodsService.getById(c01Goods.getCategoryId());
            if (category != null) {
                c01Goods.setCategoryIdName(category.getName());
            }
            category = shopGoodsService.getById(c01Goods.getSecondCategoryId());
            if (category != null) {
                c01Goods.setSecondCategoryIdName(category.getName());
            }
            category = shopGoodsService.getById(c01Goods.getThirdCategoryId());
            if (category != null) {
                c01Goods.setThirdCategoryIdName(category.getName());
            }
        }
        //把商家的省 市,区也返回给前端
        c01Goods.setProvince(f01ShopDO.getProvince());
        c01Goods.setCity(f01ShopDO.getCity());
        c01Goods.setArea(f01ShopDO.getArea());
        c01Goods.setShopLogo(f01ShopDO.getLogo());
        c01Goods.setGoodsType(c01Goods.getType());
        c01Goods.setType(f01ShopDO.getType() + "");
        c01Goods.setUserId(f01ShopDO.getUserId());
        return ResultVOUtils.success(c01Goods);
    }



    /**
     * @api {POST} /app/goods/pageGoodsSku 007获取商品规格接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} goodsId 商品Id [必填]
     * @apiParam {int} currentPage 当前页 [必填]
     * @apiParam {int} pageSize 每页条数 [必填]
     * @apiDescription APP- 获取商品规格接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "currentPage": null, 当前页数
     *         "totalNumber": 2, 总条数
     *         "list": [
     *             {
     *                 "id": 2273, 规格ID
     *                 "specifications": "重量:2斤", 规格名称
     *                 "num": 90, 规格数量
     *                 "goodsPrice": 0.02, 规格价格
     *                 "goodsId": 493,
     *                 "createPerson": null,
     *                 "createTime": "2020-08-21 15:06:24",
     *                 "updatePerson": null,
     *                 "updateTime": "2020-08-21 15:06:24",
     *                 "deleteFlag": 1,
     *                 "version": 1
     *             },
     *             {
     *                 "id": 2272,
     *                 "specifications": "重量:1斤",
     *                 "num": 92,
     *                 "goodsPrice": 0.01,
     *                 "goodsId": 493,
     *                 "createPerson": null,
     *                 "createTime": "2020-08-21 15:06:24",
     *                 "updatePerson": null,
     *                 "updateTime": "2020-08-22 15:11:48",
     *                 "deleteFlag": 1,
     *                 "version": 1
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "商品规则列表", notes = "商品规则列表")
    @PostMapping(value = "/pageGoodsSku")
    public ResultVo pageGoodsSku(@Valid com.youqiancheng.form.shop.C09GoodsSkuSearchForm form , @Valid EntyPage page ){

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        List<C09GoodsSkuDO> goodsSkulist = shopGoodsSkuService.listGoodsSkuHDPage(map);
        //封装到分页
        PageSimpleVO<C09GoodsSkuDO> shopGoodsSkuPageSimpleVO = new PageSimpleVO<>();
        shopGoodsSkuPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopGoodsSkuPageSimpleVO.setList(goodsSkulist);
        return ResultVOUtils.success(shopGoodsSkuPageSimpleVO);
    }


    /**
     * @api {POST} /app/goods/upDownGoods 008商品上下架接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} ids 商品Id [必填]
     * @apiParam {int} isSale 上下架状态 [必填] 2：上架 3：下架
     * @apiDescription APP- 获取商品规格接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
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

    /**
     * @api {POST} /app/goods/delGoods 009商品删除接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} ids 商品Id [必填]
     * @apiDescription APP- 商品删除接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
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

    /**
     * @api {POST} /app/goods/saveOrUpdateAttributeProject 010添加/编辑商品属性接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} id 属性Id [非必填]
     * @apiParam {long} shopId 属性Id [必填]
     * @apiParam {String} name 属性名称 [必填]
     * @apiParam {String} content 属性元素 [必填]
     * @apiDescription APP- 添加/编辑商品属性接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
    @ApiOperation(value = "添加/编辑商品属性", notes = "添加/编辑商品属性")
    @PostMapping(value = "/saveOrUpdateAttributeProject")
    public ResultVo saveOrUpdateAttributeProject(@Valid C06AttributeProjectDO attributeProject){
        if(attributeProject == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopAttributeProjectService.saveOrUpdateAttributeProject(attributeProject));
    }

    /**
     * @api {POST} /app/goods/listAttributeProject 011商品属性列表接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} shopId 属性Id [必填]
     * @apiDescription APP- 商品属性列表接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *         {
     *             "id": 160, 属性ID
     *             "shopId": 232, 店铺Id
     *             "name": "重量", 属性名称
     *             "content": "1斤,2斤", 属性值
     *             "status": 0,
     *             "createPerson": null,
     *             "createTime": "2020-08-21 15:04:27",
     *             "updatePerson": null,
     *             "updateTime": null,
     *             "deleteFlag": 1
     *         },
     *         {
     *             "id": 254,
     *             "shopId": 232,
     *             "name": "大小",
     *             "content": "12cm,15cm",
     *             "status": 0,
     *             "createPerson": null,
     *             "createTime": "2020-11-27 09:26:03",
     *             "updatePerson": null,
     *             "updateTime": null,
     *             "deleteFlag": 1
     *         }
     *     ]
     * }
     */
    @ApiOperation(value = "商品属性列表（不分页）", notes = "商品属性列表（不分页）")
    @PostMapping(value = "/listAttributeProject")
    public ResultVo listAttributeProject(int shopId){
        EntityWrapper<C06AttributeProjectDO> ew = new EntityWrapper<>();
        ew.eq("shop_id",shopId).eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
        return ResultVOUtils.success(shopAttributeProjectService.listAttributeProject(ew));
    }

    /**
     * @api {POST} /app/goods/addlistSelectProject 012保存商品选择属性项接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} goodsId 商品Id [必填]
     * @apiParam {array} projectIdList 所选属性Id数组 [必填]
     * @apiDescription APP- 保存商品选择属性项接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
    @ApiOperation(value = "保存商品选择属性项", notes = "保存商品选择属性项")
    @PostMapping(value = "/addlistSelectProject")
    public ResultVo addlistSelectProject(@Valid C07SelectProjectSaveForm form){

        return ResultVOUtils.success(shopSelectProjectService.save(form));
    }

    /**
     * @api {POST} /app/goods/listSelectProject 013商品选择属性项列表接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} goodsId 商品Id [必填]
     * @apiDescription APP- 商品选择属性项列表接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *         {
     *             "id": 1197, 属性Id
     *             "projectId": 254,
     *             "goodsId": 541,
     *             "status": 0,
     *             "createPerson": null,
     *             "createTime": "2020-11-27 10:39:08",
     *             "updatePerson": null,
     *             "updateTime": "2020-11-27 10:39:08",
     *             "deleteFlag": 1,
     *             "content": "12cm,15cm", 属性值
     *             "name": "大小" 属性名称
     *         },
     *         {
     *             "id": 1198,
     *             "projectId": 256,
     *             "goodsId": 541,
     *             "status": 0,
     *             "createPerson": null,
     *             "createTime": "2020-11-27 10:39:08",
     *             "updatePerson": null,
     *             "updateTime": "2020-11-27 10:39:08",
     *             "deleteFlag": 1,
     *             "content": "白色，红色",
     *             "name": "颜色"
     *         }
     *     ]
     * }
     */
    @ApiOperation(value = "商品选择属性项列表——带内容")
    @PostMapping(value = "/listSelectProject")
    public ResultVo listSelectProject(@Valid C07SelectProjectSearchForm form  ){

        QueryMap map=new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        return ResultVOUtils.success(shopSelectProjectService.listWithContent(map));
    }


    /**
     * @api {POST} /app/goods/addSelectAttribute 014添加商品选择属性接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParamExample {json}  所选属性集合 [必填]  示例
     * [
     *   {
     *     "content1": "string",
     *     "goodsId": 0,
     *     "projectId": 0,
     *     "projectName": "string"
     *   }
     * ]
     * @apiDescription APP- 添加商品选择属性接口 json 格式提交
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
    @ApiOperation(value = "添加商品选择属性", notes = "添加商品选择属性")
    @PostMapping(value = "/addSelectAttribute")
    public ResultVo addSelectAttribute(@Valid   @RequestBody  List<C08SelectAttributeSaveForm> list){
        return ResultVOUtils.success(shopSelectAttributeService.save(list));
    }

    /**
     * @api {POST} /app/goods/addGoodsSku 015添加商品规则接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {list} list 所选属性集合 [必填]  示例[
     *   {
     *     "goodsId": 0,
     *     "goodsPrice": 0,
     *     "num": 0,
     *     "specifications": "string"
     *   }
     * ]
     * @apiDescription APP- 添加商品规则接口 json 格式提交
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     * }
     */
    @ApiOperation(value = "添加商品规则", notes = "添加商品规则")
    @PostMapping(value = "/addGoodsSku")
    public ResultVo addGoodsSku(@Valid @RequestBody List<C09GoodsSkuSaveForm> list){
        return ResultVOUtils.success(shopGoodsSkuService.save(list));
    }


    /**
     * @api {POST} /app/goods/addGoods 016店铺商品添加编辑接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺商品添加编辑接口
     * @apiParam {int} id 商品Id[必填]
     * @apiParam {int} shopId 店铺Id[必填]
     * @apiParam {String} name 商品名称 [必填]
     * @apiParam {String} goodsNo  商品编码 [必填]
     * @apiParam {int} categoryId 一级分类 [必填]
     * @apiParam {int} secondCategoryId 二级分类 [必填]
     * @apiParam {int} thirdCategoryId 三级分类 [必填]
     * @apiParam {double} price 商品单价 [必填]
     * @apiParam {int} stock 库存总数 [必填]
     * @apiParam {String} introduction 商品简介 [必填]
     * @apiParam {String} icon 商品缩略图 [必填]
     * @apiParam {arr} picArr 商品轮播图 [必填]
     * @apiParam {String} goodsDesc 商品详情 [必填]
     * @apiParam {double} price 商品单价 [必填]
     * @apiParam {double} price 商品单价 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *      accountBalance: null
     *      address: "大望路"
     *      alipayUrl: "null"
     *      area: "东城区"
     *      areaCode: "110101"
     *      browseVolume: 1
     *      city: "北京市"
     *      cityCode: "110100"
     *      collectionVolume: 0
     *      contacts: "甄"
     *      countryId: 0
     *      countryName: "中国"
     *      createPerson: "13520289158"
     *      createTime: "2020-08-21 14:41:40"
     *      deleteFlag: 1
     *      distance: null
     *      examineStatus: 2
     *      examineTime: null
     *      hide: 1
     *      id: 232
     *      latitude: "39.92183"
     *      logo: "http://client.youqiancheng.vip/files/49/98/1606269265579.jpg"
     *      longitude: "116.4816"
     *      mainProject: 1
     *      name: "158的店铺"
     *      orderNum: 0
     *      phone: "13520289158"
     *      picList: null
     *      projectName: null
     *      province: "北京市"
     *      provinceCode: "110000"
     *      reason: null
     *      shopDesc: "测试"
     *      status: 1
     *      sumGoods: null
     *      type: 1
     *      updatePerson: "13520289158"
     *      updateTime: "2020-11-25 10:38:14"
     *      userId: 409
     *      wechatUrl: "null"
     *     }
     * }
     */
    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping(value = "/addGoods")
    public ResultVo addGoods(@Valid C01GoodsDO goods){
        if(goods == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        shopGoodsService.saveOrUpdateGoods(goods);
        return ResultVOUtils.success(goods.getId());
    }



    /**
     * @api {GET} /app/goods/pageGoodsEvaluate 019店铺评论管理列表接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺评论管理列表接口
     * @apiParam {long} shopId 店铺Id[必填]
     * @apiParam {String} goodsName 商品名称[非必填]
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize  每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *          "currentPage": null,
     *         "totalNumber": 2,
     *         "list": [
     *             {
     *                 "id": 136, 评价ID
     *                 "goodsId": 493, 商品ID
     *                 "goodsName": "莴笋", 商品名称
     *                 "icon": "http://client.youqiancheng.vip/files/21/43/1597993553617.jpg", 商品缩略图
     *                 "userId": 430, 用户id
     *                 "orderId": 1104, 订单Id
     *                 "star": 0, 评价星级
     *                 "content": "九月份公益金估计给冋故㕤", 评价内容
     *                 "hasPic": 0, 评论是否有图 0：否 1：是
     *                 "orderItemId": 1270, 订单明细Id
     *                 "createPerson": "17886511868", 创建人
     *                 "createTime": "2020-08-22 15:58:45", 添加时间
     *                 "updatePerson": "17886511868", 修改人
     *                 "updateTime": "2020-08-22 15:58:45", 修改时间
     *                 "deleteFlag": 1, 是否删除 1：否 2：是
     *                 "d05EvaluatePicDO": null, 评价记录图片
     *                 "pic": null, 用户头像
     *                 "nick": null, 用户昵称
     *                 "reply": null, 回复内容
     *                 "replyTime": null 回复时间
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "商品评价记录", notes = "商品评价记录")
    @GetMapping(value = "/pageGoodsEvaluate")
    public ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> pageGoodsEvaluate(String goodsName,@Valid EntyPage page,Long shopId ){
        QueryMap map=new QueryMap(page);
        map.put("shopId",shopId);
        map.put("goodsName",goodsName);
        List<D04GoodsEvaluateDO>  goodsEvaluatelist= shopEvaluateService.listEvaluateHDPage(map);
        //封装到分页
        PageSimpleVO<D04GoodsEvaluateDO> shopEvaluatePageSimpleVO = new PageSimpleVO<>();
        shopEvaluatePageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopEvaluatePageSimpleVO.setList(goodsEvaluatelist);
        return ResultVOUtils.success(shopEvaluatePageSimpleVO);
    }


    /**
     * @api {POST} /app/goods/replyEvaluate 020店铺评论回复接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺评论回复接口
     * @apiParam {int} id 评论Id[必填]
     * @apiParam {String} reply 回复内容 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "回复评价", notes = "回复评价")
    @PostMapping(value = "/replyEvaluate")
    public ResultVo replyEvaluate(@Valid EvaluateReplyForm form){
        D04GoodsEvaluateDO evaluateById = shopEvaluateService.getEvaluateById(form.getId());
        if(evaluateById==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到评价数据");
        }
        evaluateById.setReply(form.getReply());
        evaluateById.setReplyTime(LocalDateTime.now());
        int i = shopEvaluateService.updateEvaluateById(evaluateById);
        if(i<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"回复失败");
        }
        return ResultVOUtils.success();
    }

    /**
     * @api {POST} /app/goods/delEvaluate 021删除商品评论接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 删除商品评论接口
     * @apiHeader {String} X-Type 用户token [必填] 默认值 APP
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-UserId 用户 [必填]
     * @apiParam {int} id 评论Id[必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "删除商品评论", notes = "删除商品评论")
    @PostMapping(value = "/delEvaluate")
    @AuthRuleAnnotation()
    public ResultVo delEvaluate(@Valid EvaluateReplyForm form){
        D04GoodsEvaluateDO evaluateById = shopEvaluateService.getEvaluateById(form.getId());
        if(evaluateById==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到评价数据");
        }
        evaluateById.setReply(form.getReply());
        evaluateById.setDeleteFlag(2);
        int i = shopEvaluateService.updateEvaluateById(evaluateById);
        if(i<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"回复失败");
        }
        return ResultVOUtils.success();
    }



    /**
     * @api {POST} /app/goods/pageOrders 022店铺售后管理列表接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺售后管理列表接口
     * @apiParam {long} shopId 店铺Id[必填]
     * @apiParam {String} serviceNo 售后单号 [非必填]
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize  每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *       "currentPage": null,
     *         "totalNumber": 5,
     *         "list": [
     *             {
     *                 "id": 228, 售后Id
     *                 "userId": 435, 用户Id
     *                 "orderId": 1167,订单id
     *                 "orderNo": "DD202008261502140213603435", 订单号
     *                 "serviceNo": "SH202008261502373321630435", 售后单号
     *                 "type": 1,售后方式 1：退款 2：换货
     *                 "money": 0.02, 退款金额
     *                 "isReturnGoods": 1, 是否需要退货 1：否 2：是
     *                 "reason": "222222222222", 原因
     *                 "time": "2020-08-26 15:02:37", 申请售后时间
     *                 "status": 1, 状态
     *                 "examineStatus": 1, 审核状态 1：待审核 2：通过 3：拒绝
     *                 "examineTime": null, 审核时间
     *                 "createPerson": "13520289157", 创建人
     *                 "createTime": "2020-08-26 15:02:37", 创建时间
     *                 "updatePerson": "13520289157", 修改人
     *                 "updateTime": "2020-08-26 15:02:37", 修改时间
     *                 "deleteFlag": 1, 删除标识 1：正常 2：已删除
     *                 "orderItemId": 1382, 订单明细Id
     *                 "refuseReason": null, 拒绝理由
     *                 "tradeNo": null,
     *                 "refundNo": null,退款单号
     *                 "refundType": 0, 退款方式 1支付宝，2微信，3余额
     *                 "explain": null 退款说明
     *             }
     *         ],
     *         "map": null
     *     }
     *   }
     */
    @ApiOperation(value = "售后列表", notes = "售后列表")
    @PostMapping(value = "/pageOrders")
    public ResultVo<PageSimpleVO<D01OrderDO>> pageOrders( @Valid CustomerServicePageForm form, @Valid EntyPage page ){

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",form.getShopId());
        List<D03CustomerServiceDO> d03CustomerServiceDOS = customerServiceService.listHDPage(map);

        //封装到分页
        PageSimpleVO<D03CustomerServiceDO> simpleVO = new PageSimpleVO<>();
        simpleVO.setTotalNumber(page.getTotalNumber());
        simpleVO.setList(d03CustomerServiceDOS);
        return ResultVOUtils.success(simpleVO);
    }


    /**
     * @api {POST} /app/goods/examineOrderPass 023店铺售后审核通过接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺售后审核通过接口
     * @apiParam {String} refuseReason 通过理由 [非必填]
     * @apiParam {int} id 售后Id [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @ApiOperation(value = "审核通过")
    @PostMapping(value = "/examineOrderPass")
    public ResultVo examineOrderPass(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderPass(form);
        return ResultVOUtils.success();
    }


    /**
     * @api {POST} /app/goods/examineOrderRefuse 024店铺售后审核拒绝接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 店铺售后审核拒绝接口
     * @apiParam {String} refuseReason 通过理由 [非必填]
     * @apiParam {int} id 售后ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @ApiOperation(value = "审核拒绝")
    @PostMapping(value = "/examineOrderRefuse")
    public ResultVo examineOrderRefuse(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderRefuse(form);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "根据商品ID获取商品规格信息;参数——商品ID")
    @GetMapping("/getSelectAttribute")
    ResultVo getSelectAttribute(Long id) {
        if (id == null || id == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "参数不能为空或者0");
        }
        List<C08SelectAttributeDO> selectAttribute = c01GoodsService.getSelectAttribute(id);
        if(selectAttribute.size()==0){
            //兼容实体店无商品规则的商品下单
            C08SelectAttributeDO c08SelectAttributeDO = new C08SelectAttributeDO();
            c08SelectAttributeDO.setContent("");
            c08SelectAttributeDO.setCreateTime(LocalDateTime.now());
            c08SelectAttributeDO.setDeleteFlag(1);
            c08SelectAttributeDO.setGoodsId(id);
            c08SelectAttributeDO.setGoodsId(1);
            c08SelectAttributeDO.setProjectId(1);
            c08SelectAttributeDO.setProjectName("");
            selectAttribute.add(c08SelectAttributeDO);
        }
        return ResultVOUtils.success(selectAttribute);
    }

    @ApiOperation(value = "根据商品ID和规格获取取库存;参数——商品ID，规格")
    @GetMapping("/getSku")
    ResultVo getSku(C09GoodsSkuSearchForm form) {

        C09GoodsSkuDO selectAttribute = c01GoodsService.getSku(form);
        if(selectAttribute == null){
            C09GoodsSkuDO selectAttribute1 = new C09GoodsSkuDO();
            C01GoodsAppVO c01GoodsAppVO = c01GoodsService.get(form.getGoodsId());
            selectAttribute1.setNum(1);
            selectAttribute1.setCreateTime(LocalDateTime.now());
            selectAttribute1.setDeleteFlag(1);
            selectAttribute1.setGoodsPrice(c01GoodsAppVO.getPrice());
            selectAttribute1.setGoodsId(form.getGoodsId());
            selectAttribute1.setSpecifications("");
            selectAttribute1.setVersion(1);
            return ResultVOUtils.success(selectAttribute1);
        }
        return ResultVOUtils.success(selectAttribute);
    }

    @ApiOperation(value = "保存购物车;参数——商品信息，数量，总价")
    @PostMapping("/saveShoppingCart")
    @AuthRuleAnnotation()
    ResultVo saveShoppingCart(@RequestBody B04ShoppingCartSaveForm dto) {

        B04ShoppingCartDO b04ShoppingCart = new B04ShoppingCartDO();
        BeanUtils.copyProperties(dto, b04ShoppingCart);
        b04ShoppingCart.setUpdatePerson(dto.getCreatePerson());
        b04ShoppingCart.setCreateTime(LocalDateTime.now());
        b04ShoppingCart.setUpdateTime(LocalDateTime.now());
        b04ShoppingCart.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num = b04ShoppingCartService.insert(b04ShoppingCart);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "获取商品评价列表;参数——商品ID，分页参数（不传则默认查询全部）")
    @GetMapping("/getEvaluateByGoodsId")
    ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> getEvaluateByGoodsId(@Valid D04GoodsEvaluateSearchForm form, @Valid EntyPage page) {

        QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
        List<D04GoodsEvaluateDO> d04GoodsEvaluateList = d04GoodsEvaluateService.listHDPage(map);
        //封装到分页
        PageSimpleVO<D04GoodsEvaluateDO> d04GoodsEvaluateSimpleVO = new PageSimpleVO<>();
        d04GoodsEvaluateSimpleVO.setTotalNumber(page.getTotalNumber());
        d04GoodsEvaluateSimpleVO.setList(d04GoodsEvaluateList);

        return ResultVOUtils.success(d04GoodsEvaluateSimpleVO);
    }

    //    @ApiOperation(value = "根据ID获取用户商品评价记录详情")
//    @GetMapping("/getGoodsEvaluateInfoById")
//    ResultVo getGoodsEvaluateInfoById( Long id ){
//        D04GoodsEvaluateVO d04GoodsEvaluate = d04GoodsEvaluateService.get(id);
//        return ResultVOUtils.success(d04GoodsEvaluate);
//    }
    @ApiOperation(value = "获取商品被收藏数；参数——商品ID")
    @GetMapping("/getCollectionGoodsCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getGoodsCount(@Valid B05CollectionSearchCountForm form) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type", StatusConstant.CollectionType.goods.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }


    @ApiOperation(value = "收藏商品;参数——用户ID,商品ID")
    @PostMapping("/collectionGoods")
    @AuthRuleAnnotation()
    ResultVo collectionGoods(@RequestBody B05CollectionSaveForm dto) {

        if (dto.getUserId() == null || dto.getRelationId() == null || dto.getUserId() == 0 || dto.getRelationId() == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection = new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.goods.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num = b05CollectionService.insert(b05Collection);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL, "收藏商品失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "收藏商家;参数——用户ID,商家ID")
    @PostMapping("/collectionShop")
    @AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody B05CollectionSaveForm dto) {

        if (dto.getUserId() == null || dto.getRelationId() == null || dto.getUserId() == 0 || dto.getRelationId() == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection = new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num = b05CollectionService.insert(b05Collection);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL, "收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "判断商品是否被用户收藏——返回收藏数：等于0——未收藏;参数——用户ID,商品ID")
    @GetMapping("/getGoodsIsCollection")
    @AuthRuleAnnotation()
    ResultVo getGoodsIsCollection(@Valid B05CollectionSearchIsForm form) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type", StatusConstant.CollectionType.goods.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }


    @ApiOperation(value = "取消收藏商家;参数——用户ID,商家ID")
    @PostMapping("/cancelCollectionShop")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody B05CollectionSaveForm dto) {

        if (dto.getUserId() == null || dto.getRelationId() == null || dto.getUserId() == 0 || dto.getRelationId() == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection = new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num = b05CollectionService.delete(b05Collection);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL, "取消收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "取消收藏商品;参数——用户ID,商品ID")
    @PostMapping("/cancelCollectionGoods")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionGoods(@RequestBody B05CollectionSaveForm dto) {

        if (dto.getUserId() == null || dto.getRelationId() == null || dto.getUserId() == 0 || dto.getRelationId() == 0) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection = new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.goods.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num = b05CollectionService.delete(b05Collection);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL, "取消收藏商品失败");
        }
        return ResultVOUtils.success(num);
    }

}
