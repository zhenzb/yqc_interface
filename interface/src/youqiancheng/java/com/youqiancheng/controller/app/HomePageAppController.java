package com.youqiancheng.controller.app;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.DictTypeConstants;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.E01RedenvelopesStreetSearchForm;
import com.youqiancheng.form.app.E04RedenvelopesGrantRecordSearchForm;
import com.youqiancheng.form.app.F01ShopAppSaveForm;
import com.youqiancheng.form.shop.otther.ShopGoodsPageForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.service.manager.service.InitDataService;
import com.youqiancheng.service.shop.ShopGoodsService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.E01RedenvelopesStreetVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-首页"})
@RestController
@RequestMapping(value = "app/homePage")
public class HomePageAppController {
    @Autowired
    private A15MessageAppService a15MessageService;
    @Autowired
    private E04RedenvelopesGrantRecordAppService e04RedenvelopesGrantRecordService;
    @Autowired
    private E01RedenvelopesStreetAppService e01RedenvelopesStreetService;
    @Autowired
    private F01ShopAppService f01ShopService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private C01GoodsAppService c01GoodsService;
    @Autowired
    private C03CategoryAppService c03CategoryAppService;
    @Resource
    private A09NoticeAppService a09NoticeService;
    @Autowired
    private A10CarouselPicAppService a10CarouselPicService;
    @Resource
    private ShopGoodsService shopGoodsService;

    /**
     　* @Description:查询浏览量
     　* @author shalongteng
     　* @date 2020/4/24 15:04
     　*/
    @ApiOperation(value = "查询浏览量")
    @GetMapping("/getBrowseVolume")
    public ResultVo<A06BaseInfoDO> getBrowseVolume() {
        A06BaseInfoDO a06BaseInfo = a15MessageService.getA06BaseInfo();
        return ResultVOUtils.success(a06BaseInfo.getBrowseVolume());
    }
    /**
     　* @Description:增加一次浏览量
     　* @author shalongteng
     　* @date 2020/4/24 15:04
     　*/
    @ApiOperation(value = "增加一次浏览量", notes = "增加一次浏览量")
    @PostMapping("/addBrowseVolume")
    public ResultVo<A06BaseInfoDO> addBrowseVolume() {
        A06BaseInfoDO a06BaseInfo = a15MessageService.addA06BaseInfo();
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "商家入驻申请；参数——商家实体")
    @PostMapping("/shopApplyEnters")
   // @AuthRuleAnnotation()
    ResultVo applyEnters(@RequestBody  @Valid F01ShopAppSaveForm f01Shop ) {
        int shopApplyEnters = f01ShopService.isShopApplyEnters(f01Shop.getUserId());
        if(shopApplyEnters==2||shopApplyEnters==3){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"您已申请入驻，无法重复申请入驻");
        }
        F01ShopDO f01ShopDO = f01ShopService.applyEnters(f01Shop);
        return ResultVOUtils.success(f01ShopDO);
    }
    @ApiOperation(value = "是否可以商家入驻——返回结果:1:无商家可以申请入驻，2已入驻待审核，3已入驻审核通过，4已入驻审核拒绝；参数——用户ID")
    @PostMapping("/isShopApplyEnters")
    @AuthRuleAnnotation()
    ResultVo isShopApplyEnters(Long userId) {
        if(userId==null||userId==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID不能为空或者0");
        }
        return ResultVOUtils.success(f01ShopService.isShopApplyEnters(userId));
    }

    /**
     * @api {GET} /app/homePage/getShopType 003获取店铺类型接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取店铺类型接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": [
     *     {
     *       "id": 267,
     *       "name": "宣传",
     *       "value": 2,
     *       "type": "shopType",
     *       "description": "商家类型",
     *       "orderNum": 2,
     *       "pid": 0,
     *       "createPerson": 0,
     *       "createTime": "2020-04-09 00:00:00",
     *       "updatePerson": 0,
     *       "updateTime": "2020-06-29 15:59:35",
     *       "remark": null,
     *       "deleteFlag": 1,
     *       "status": 1
     *     },
     *     {
     *       "id": 266,
     *       "name": "商品",
     *       "value": 1,
     *       "type": "shopType",
     *       "description": "商家类型",
     *       "orderNum": 1,
     *       "pid": 0,
     *       "createPerson": 0,
     *       "createTime": "2020-04-09 00:00:00",
     *       "updatePerson": 0,
     *       "updateTime": "2020-06-29 15:59:28",
     *       "remark": null,
     *       "deleteFlag": 1,
     *       "status": 1
     *     }
     *   ]
     * }
     */
    @ApiOperation(value = "返回商家类型")
    @GetMapping("/getShopType")
    ResultVo<List<A16SysDictDO>> getShopType() {
        List<A16SysDictDO> sysDictByType = initDataService.getSysDictByType(DictTypeConstants.SHOP_TYPE);
        return ResultVOUtils.success(sysDictByType);
    }


    /**
     * @api {GET} /app/homePage/getShopProjectByType 004获取店铺主营项目接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {int} type 店铺类型 [必填]
     * @apiDescription APP- 获取店铺主营项目接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *    "data": [
     *     {
     *       "createPerson": null,
     *       "createTime": null,
     *       "updatePerson": "admin",
     *       "updateTime": "2020-07-10 10:18:26",
     *       "deleteFlag": 1,
     *       "id": 3,
     *       "type": 1, 类型值
     *       "name": "实体店", 名称
     *       "picUrl": "http://49.233.136.163:8070/files/84/11/1594347504455.jpg",
     *       "orderNum": 2,
     *       "status": 1
     *     },
     *     {
     *       "createPerson": null,
     *       "createTime": null,
     *       "updatePerson": "admin",
     *       "updateTime": "2020-07-10 10:19:08",
     *       "deleteFlag": 1,
     *       "id": 2,
     *       "type": 1,
     *       "name": "平台类",
     *       "picUrl": "http://49.233.136.163:8070/files/92/73/1594347546507.jpg",
     *       "orderNum": 7,
     *       "status": 1
     *     },
     *     {
     *       "createPerson": null,
     *       "createTime": null,
     *       "updatePerson": "admin",
     *       "updateTime": "2020-07-10 10:18:56",
     *       "deleteFlag": 1,
     *       "id": 1,
     *       "type": 1,
     *       "name": "线上商品",
     *       "picUrl": "http://49.233.136.163:8070/files/32/93/1594347534627.jpg",
     *       "orderNum": 1,
     *       "status": 1
     *     }
     *   ]
     * }
     */
    @ApiOperation(value = "根据商家类型返回商家主营项目;参数——商家类型")
    @GetMapping("/getShopProjectByType")
    ResultVo<List<F03MainProjectDO>> getShopProjectByType(String type ){
        if(StringUtils.isBlank(type)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数商家类型不能为空");
        }
        List<F03MainProjectDO> list = f01ShopService.getShopProjectByType(type);
        return ResultVOUtils.success(list);
    }

    /**
     * @api {GET} /app/homePage/pageGoods 005获取店铺商品列表接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiParam {long} shopId 店铺Id [必填]
     * @apiParam {int} goodsStatus 商品审核状态 [必填]  2:已通过 3：已拒绝  不填写为全部
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize 每页条数 [必填]
     * @apiDescription APP- 获取店铺商品列表接口
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "currentPage": null,
     *         "totalNumber": 1,
     *         "list": [
     *             {
     *                 "id": 493, 商品Id
     *                 "categoryId": 1,
     *                 "secondCategoryId": 116,
     *                 "thirdCategoryId": 483,
     *                 "name": "莴笋",
     *                 "price": 80, 单价
     *                 "stock": 1000, 库存
     *                 "goodsDesc": "<p>莴笋简介</p>",
     *                 "saleNum": 29,
     *                 "isSale": 2,  上架状态 1:下架 2:上架
     *                 "examineStatus": 2,
     *                 "shopId": 232,
     *                 "shopName": "158的店铺",
     *                 "icon": "http://client.youqiancheng.vip/files/21/43/1597993553617.jpg",
     *                 "browseVolume": 69,
     *                 "createPerson": null,
     *                 "reason": null,
     *                 "createTime": "2020-08-21 15:06:00",
     *                 "updatePerson": null,
     *                 "updateTime": "2020-08-21 15:07:08",
     *                 "deleteFlag": 1,
     *                 "comprehensiveSort": 0,
     *                 "evaluateSort": 0,
     *                 "type": null,
     *                 "goodsNo": "001",
     *                 "introduction": "商品简介",
     *                 "collectionVolume": null,
     *                 "picArr": null,
     *                 "c02GoodsPicDO": null,
     *                 "categoryIdName": null,
     *                 "secondCategoryIdName": null,
     *                 "thirdCategoryIdName": null,
     *                 "picList": null,
     *                 "province": null,
     *                 "city": null,
     *                 "area": null,
     *                 "evaluateCount": 0,
     *                 "skuList": null
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "全部商品/已审核/已拒绝", notes = "全部商品/已审核/已拒绝")
    @GetMapping(value = "/pageGoods")
    public ResultVo<PageSimpleVO<C01GoodsDO>> pageGoods(@Valid ShopGoodsPageForm shopGoodsPageForm, @Valid EntyPage page,
                                                        long shopId){

//        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
//        if (shopUser != null){
//            shopGoodsPageForm.setShopId(shopUser.getShopId());
//        }
        shopGoodsPageForm.setShopId(shopId);
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


    @ApiOperation(value = "获取首页轮播图列表")
    @GetMapping("/getCarouselPic")
    ResultVo getCarouselPic() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.statusAndDelete.getCode());
        map.put("type", TypeConstant.CarouselPicType.home_page.getCode());
        map.put("status",StatusConstant.enable.getCode());
        List<A10CarouselPicDO> list = a10CarouselPicService.list(map);
        return ResultVOUtils.success(list);
    }

    /**
 　* @Description: 获取公告信息
 　* @author yutf
 　* @date 2020/4/2 13:54
 　*/
    @ApiOperation(value = "获取公告信息")
    @GetMapping("/getNoticeList")
    public ResultVo<PageSimpleVO<A09NoticeDO>> getNoticeList(@Valid EntyPage page) {
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        List<A09NoticeDO> a09NoticeDOList = a09NoticeService.listNoticePage(map);
        //封装到分页
        PageSimpleVO<A09NoticeDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(a09NoticeDOList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }

    /**
     　* @Description: 获取商品分类信息
     　* @author yutf
     　* @date 2020/4/2 13:54
     　*/
    @ApiOperation(value = "获取一级商品分类信息（一级商品分类即为国家）")
    @GetMapping("/getFistLevelCategory")
    public ResultVo<C03CategoryDO> getFistLevelCategory() {
        List<C03CategoryDO> list = c03CategoryAppService.listFirst();
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "获取红包街列表（分页+过滤参数）——自动返回12家商家；参数——一级分类ID")
    @GetMapping("/getRedStreetList")
    ResultVo<PageSimpleVO<E01RedenvelopesStreetDO>> getRedStreetList(@Valid E01RedenvelopesStreetSearchForm form , @Valid EntyPage page) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.statusAndDelete.getCode());
        List<E01RedenvelopesStreetVO> e01RedenvelopesStreetVOS = e01RedenvelopesStreetService.listHDPageWithShopList(map);
        //封装到分页
        PageSimpleVO<E01RedenvelopesStreetVO> e01RedenvelopesStreetSimpleVO = new PageSimpleVO<>();
        e01RedenvelopesStreetSimpleVO.setTotalNumber(page.getTotalNumber());
        e01RedenvelopesStreetSimpleVO.setList(e01RedenvelopesStreetVOS);

        return ResultVOUtils.success(e01RedenvelopesStreetSimpleVO);
    }

    @ApiOperation(value = "更多——获取红包街商家列表;参数——APP——红包发放记录查询实体")
    @GetMapping("/getShopListByStreetId")
    ResultVo<PageSimpleVO<F01ShopDO>> getShopListByStreetId(@Valid E04RedenvelopesGrantRecordSearchForm form, @Valid EntyPage page) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
        List<F01ShopDO> shopListByRedEnvelopes = e04RedenvelopesGrantRecordService.getShopListByRedEnvelopes(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> e04RedenvelopesGrantRecordSimpleVO = new PageSimpleVO<>();
        e04RedenvelopesGrantRecordSimpleVO.setTotalNumber(page.getTotalNumber());
        e04RedenvelopesGrantRecordSimpleVO.setList(shopListByRedEnvelopes);

        return ResultVOUtils.success(e04RedenvelopesGrantRecordSimpleVO);
    }
}
