package com.youqiancheng.controller.app;


import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.C10PublicitySearchForm;
import com.youqiancheng.form.app.D01OrderStatusrForm;
import com.youqiancheng.form.manager.C05CommentReplyForm;
import com.youqiancheng.form.shop.CustomerServiceUpdateForm;
import com.youqiancheng.form.shop.F06WithdrawalApplicationSaveForm;
import com.youqiancheng.form.shop.ShopOrderSendForm;
import com.youqiancheng.form.shop.otther.ShopWithdrawalApplicationPageForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.service.client.service.F05ShopAccountService;
import com.youqiancheng.service.shop.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import com.youqiancheng.vo.shop.WithdrawalParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"手机端-我的店铺接口"})
@RestController
@RequestMapping(value = "app/myShop")
public class MyShopController {
    @Autowired
    private D01OrderAppService d01OrderService;
    @Autowired
    private D02OrderItemAppService d02OrderItemAppService;
    @Autowired
    private B01UserAppService b01UserAppService;
    @Autowired
    private  D01OrderAppService d01OrderAppService;
    @Autowired
    private F05ShopAPPAccountService f05ShopAPPAccountService;
    @Autowired
    private C01GoodsAppService c01GoodsAppService;
    @Autowired
    private F01ShopAppService f01ShopAppService;
    @Autowired
    private B02UserAccountAppService b02UserAccountAppService;
    @Autowired
    private  C10PublicityAppService c10PublicityService;
    @Autowired
    private F05ShopAccountService f05ShopAccountService;
    @Resource
    private CustomerServiceService customerServiceService;
    @Autowired
    private F01ShopClientService f01ShopClientService;
    @Autowired
    private F08ShopAppService f08ShopAppService;
    @Resource
    private ShopOrderService shopOrderService;
    @Resource
    private ShopWithdrawalApplicationService shopWithdrawalApplicationService;
    @Resource
    private ShopPublicityService shopPublicityService;

    @Resource
    private ShopEvaluateService shopEvaluateService;


    @ApiOperation(value = "根据订单ID获取订单详情；参数——订单ID")
    @GetMapping("/getShopOrderInfoById")
    @AuthRuleAnnotation()
    ResultVo getShopOrderInfoById(Long id ){
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空或者0");
        }
            D01OrderVO d01Order = d01OrderService.get(id);
            return ResultVOUtils.success(d01Order);
    }
    @ApiOperation(value = "根据商家ID查询,商家自己的名字,图片,商家的今天订单,余额信息：参数——商家ID")
    @GetMapping("/getAppShopAccountInfoById")
    @AuthRuleAnnotation()
    ResultVo getShopInfoById(Long id) {
        HashMap<Object, Object> map = new HashMap<>();
        //第一步:查商家信息,要得到他的名称,和图片.
     List<F01ShopDO>  user=  f01ShopAppService.getShopInfoById(id);
     if(user==null&&user.isEmpty()){
         return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"没查到响应的数据");
     }
     map.put("nick",user.get(0).getName());
     map.put("pic",user.get(0).getLogo());

     //第二步:查商家今日的订单个数
    int appTodayConut= d01OrderAppService.getAppTodayShopOrderCountByShopId(id);
    map.put("appTodayConut",appTodayConut);
    //第三步:查商家余额;
        BigDecimal TotalBalance =f05ShopAPPAccountService.getShopTotalBalanceByShopId(id);
        map.put("totalBalance",TotalBalance);
        //第四步:查商家商品的小图标和商品名称;
       List<C01GoodsDO>  goodsList= c01GoodsAppService.getShopAppGoodsByShopId(id);
        if(goodsList==null&&user.isEmpty()){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"没查到响应的数据");
        }
        //goodsList.get(0).getIcon();
        map.put("goodsList",goodsList);
        return  ResultVOUtils.success(map);
    }

    @ApiOperation(value = "根据商家ID查询,商家订单状态类型个数：参数——商家ID")
    @GetMapping("/getOrderorderStatusById")
    @AuthRuleAnnotation()
    ResultVo  getOrderStatusCountById(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        //1:前端有五个状态类型,去查每个状态类型各个个数
        Map<String, Object>  OrderStatusCount=  d01OrderAppService.getOrderStatusCountById(id);
        return  ResultVOUtils.success(OrderStatusCount);
    }
//    @ApiOperation(value = "根据商家ID查询,商家余额信息,商家今日收益,昨日收益,总收益：参数——商家ID")
//    @GetMapping("/getShopIncomeInfoById")
//    @AuthRuleAnnotation()
//    ResultVo getShopAccountInfo(Long id) {
//        Map<String, Object> map= d01OrderAppService.getAppShopIncome(id);
//
//        return  ResultVOUtils.success(map);
//
//    }
    @ApiOperation(value = "根据商家ID查询,商家余额信息,商家今日收益,昨日收益,总收益：参数——商家ID")
    @GetMapping("/getShopAccountInfoById")
    @AuthRuleAnnotation()
    ResultVo getShopAccountInfo(Long id) {
        if(id==null||id==0){
            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家ID不能为空");
        }
        F05ShopAccountDO profit = f01ShopClientService.getProfit(id);
        return  ResultVOUtils.success(profit);

    }
//    @ApiOperation(value = "根据商家Id,状态类型,查询在各个状态下的订单：参数——商家ID,状态类型")
//    @GetMapping("/getShopStatusOrderInfo1")
//    @AuthRuleAnnotation()
//    ResultVo getShopStatusOrderInfoById1(@Valid  D01OrderStatusrForm d01OrderStatusrForm) {
//      List<D01OrderStatusVo> orderStatusList = d01OrderAppService.getAppShopStatusOrder(d01OrderStatusrForm);
//        System.out.println("----------------------------------------------------------------------"+orderStatusList);
//        return  ResultVOUtils.success(orderStatusList);
//
//    }
    @ApiOperation(value = "根据商家Id,状态类型,查询在各个状态下的订单：参数——商家ID,状态类型")
    @GetMapping("/getShopStatusOrderInfo")
    @AuthRuleAnnotation()
    ResultVo getShopStatusOrderInfoById(@Valid  D01OrderStatusrForm form  ,@Valid EntyPage page) {
        //form.setOrderStatus(8);
        if(form.getOrderStatus()<5){
            QueryMap map =new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
            List<D01OrderVO> list = d01OrderAppService.list(map);
            //封装到分页
            PageSimpleVO<D01OrderVO> orderPageSimpleVO = new PageSimpleVO<>();
            orderPageSimpleVO.setTotalNumber(page.getTotalNumber());
            orderPageSimpleVO.setList(list);
            return  ResultVOUtils.success(orderPageSimpleVO);
        }else{
            QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
            List<D01OrderStatusVo> appShopStatusOrderHDPage = d02OrderItemAppService.getAppShopStatusOrderHDPage(map);
            //封装到分页
            PageSimpleVO<D01OrderStatusVo> d01OrderSimpleVO = new PageSimpleVO<>();
            d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderSimpleVO.setList(appShopStatusOrderHDPage);
            return ResultVOUtils.success(d01OrderSimpleVO);
        }


    }

    @ApiOperation(value = "根据用户Id,查询用户信息,商家信息：参数——用户ID")
    @GetMapping("/getUserAndShopInfo")
    @AuthRuleAnnotation()
    ResultVo getUserAndShopInfoByUserId(Long id) {
      B01UserDO b01UserDO=  b01UserAppService.get(id);
      Map<String, Object> map = new HashMap<>();
      //获取用户的信息
      map.put("id",b01UserDO.getId());
      map.put("nick",b01UserDO.getNick());
      map.put("phone",b01UserDO.getMobile());
      map.put("isShop",b01UserDO.getIsShop());
      if(b01UserDO.getIsShop()== TypeConstant.isShop.yes.getCode()){//判断是否是商家
          F01ShopDO f01ShopDO= f01ShopAppService.get(b01UserDO.getShopId());
          if(f01ShopDO==null){
              return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"商家信息不存在");
          }
          /**
           * 1:在这里已经得知此用户是商家,并且商家信息存在
           * 2:无论你是什么商家类型,只要你别冻结,都不能进入商家店铺
           * 3:所以查看商家账号是否被冻结,还有一个商家只有一个账号,所以一对一,返回对象
           */
          QueryMap queryMap = new QueryMap();
          queryMap.put("shopId",f01ShopDO.getId());
          queryMap.put("delete_flag",StatusConstant.CreatFlag.delete.getCode());
          //通过这条件去查询商家账号信息
          F08ShopUserDO f08ShopUserDO = f08ShopAppService.getf08ShopUserDO(queryMap);
          if(f08ShopUserDO==null){
              //return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"商家账号信息不存在");
              return ResultVOUtils.success();
          }

          //是商家,获取商家的信息
          map.put("shopId",f01ShopDO.getId());
          map.put("shopName",f01ShopDO.getName());
          map.put("type",f01ShopDO.getType());
          map.put("isShow", TypeConstant.isShow.no.getCode());//看商家的展示状态,初始是不展示的
          if(StatusConstant.ExamineStatus.adopt.getCode()==f01ShopDO.getExamineStatus()){//判断商家的审核状态,审核通过了
              map.put("isShow", TypeConstant.isShow.yes.getCode());//通过后把商家的展示状态改为已展示
              map.put("status",f08ShopUserDO.getStatus());//看商家是否启用
          }
      }
    /*  QueryMap param=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        param.put("userId",b01UserDO.getId());
        param.put("countryId",1);
        List<B02UserAccountDO> list = b02UserAccountAppService.list(param);
        if(CollectionUtils.isEmpty(list)){
            map.put("balance", 0);
        }else{
            map.put("balance", list.get(0).getAccountBalance());
        }*/
        return  ResultVOUtils.success(map);

    }


    /**
     * @api {GET} /app/myShop/getPublicityByShopId 001宣传店铺宣传管理列表接口
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传店铺宣传管理列表接口
     * @apiParam {long} shopId 店铺ID [必填]
     * @apiParam {int} currentPage 当前页 [必填]
     * @apiParam {int} pageSize 每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *         "currentPage": null,
     *         "totalNumber": 3,
     *         "list": [
     *             {
     *                 "createPerson": null,
     *                 "createTime": "2020-11-25 17:11:11", 添加时间
     *                 "updatePerson": "admin",
     *                 "updateTime": "2020-11-25 17:11:50",
     *                 "deleteFlag": 1,
     *                 "id": 239,
     *                 "categoryId": 0,
     *                 "secondCategoryId": 0,
     *                 "thirdCategoryId": 0,
     *                 "shopId": 262,
     *                 "shopName": "157宣传店铺",
     *                 "type": 2,
     *                 "title": "157的第三个宣传", 宣传标题
     *                 "goodsDesc": null, 简介
     *                 "icon": "http://client.youqiancheng.vip/files/59/76/1606294781945.jpg", 主题图
     *                 "contentUrl": null,
     *                 "duration": null,
     *                 "content": "这个宣传好不好", 内容摘要
     *                 "orderNum": 3, 排序
     *                 "publicTime": "2020-11-25 17:11:50",
     *                 "isSale": 2,
     *                 "examineStatus": 2, 审核状态 1:待审核 2：已通过 3:已拒绝
     *                 "examineTime": "2020-11-25 17:11:50",
     *                 "collectionVolume": null, 收藏数
     *                 "browseVolume": 2,浏览量
     *                 "picArr": null,
     *                 "commentCount": 0,
     *                 "reason": null,
     *                 "audio": null
     *             },
     *             {
     *                 "createPerson": null,
     *                 "createTime": "2020-11-25 16:59:59",
     *                 "updatePerson": "admin",
     *                 "updateTime": "2020-11-25 17:11:50",
     *                 "deleteFlag": 1,
     *                 "id": 238,
     *                 "categoryId": 0,
     *                 "secondCategoryId": 0,
     *                 "thirdCategoryId": 0,
     *                 "shopId": 262,
     *                 "shopName": "157宣传店铺",
     *                 "type": 2,
     *                 "title": "157的第二个宣传",
     *                 "goodsDesc": null,
     *                 "icon": "http://client.youqiancheng.vip/files/59/76/1606294781945.jpg",
     *                 "contentUrl": null,
     *                 "duration": null,
     *                 "content": "<p>ww</p>",
     *                 "orderNum": 2,
     *                 "publicTime": "2020-11-25 17:11:50",
     *                 "isSale": 2,
     *                 "examineStatus": 2,
     *                 "examineTime": "2020-11-25 17:11:50",
     *                 "collectionVolume": null,
     *                 "browseVolume": 1,
     *                 "picArr": null,
     *                 "commentCount": 0,
     *                 "reason": null,
     *                 "audio": null
     *             }
     *         ],
     *         "map": null
     *     }
     *   }
     */
    @ApiOperation(value = "根据商家获取商家的宣传图文和音频信息列表——按照时间降序；参数——商家ID,分页参数")
    @GetMapping("/getPublicityByShopId")
    ResultVo<PageSimpleVO<C10PublicityDO>> getPublicityByShopId(@Valid C10PublicitySearchForm form , @Valid EntyPage page ) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",form.getShopId());
        List<C10PublicityDO> c10PublicityList = c10PublicityService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C10PublicityDO> c10PublicitySimpleVO = new PageSimpleVO<>();
        c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
        c10PublicitySimpleVO.setList(c10PublicityList);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }

    /**
     * @api {POST} /app/myShop/saveOrUpdatePublicity 002宣传店铺新增/编辑宣传接口
     * @apiHeader {String} X-Token 用户登录时返回的token
     * @apiHeader {String} X-Type 固定值 APP
     * @apiHeader {String} X-UserId 用户Id
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传店铺新增/编辑宣传接口
     * @apiParam {long} id 宣传Id[必填]
     * @apiParam {long} shopId 店铺Id[必填]
     * @apiParam {String} title 宣传标题 [必填]
     * @apiParam {int} orderNum 排序 [必填]
     * @apiParam {String} content 宣传详情 [必填]
     * @apiParam {String} icon 主题图 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": 1
     *   }
     */
    @ApiOperation(value = "添加/编辑动态", notes = "添加/编辑动态")
    @PostMapping(value = "/saveOrUpdatePublicity")
    @AuthRuleAnnotation()
    public ResultVo saveOrUpdatePublicity(@Valid C10PublicityDO publicity){
        if (publicity == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopPublicityService.saveOrUpdatePublicity2(publicity));
    }



    /**
     * @api {GET} /app/myShop/listCommentHDPage 003宣传店铺评论列表接口
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传店铺评论列表接口
     * @apiParam {long} shopId 店铺Id[必填]
     * @apiParam {String} goodsName 宣传标题 [非必填]
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize 每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *       "currentPage": null,
     *         "totalNumber": 1,
     *         "list": [
     *             {
     *                 "id": 96, 评论ID
     *                 "goodsId": 237, 宣传ID
     *                 "goodsName": "157的第一个宣传", 宣传标题
     *                 "goodsIcon": "http://client.youqiancheng.vip/files/66/95/1606294371983.jpg", 主题图
     *                 "content": "不错哦", 评论内容
     *                 "reply": null, 回复内容
     *                 "level": 0,
     *                 "userId": 435, 用户Id
     *                 "userName": "13520289157", 用户名称
     *                 "createPerson": "13520289157", 添加人
     *                 "createTime": "2020-11-25 17:18:00", 添加时间
     *                 "updatePerson": "13520289157", 评论者昵称
     *                 "updateTime": "2020-11-25 17:18:00", 评论时间
     *                 "deleteFlag": 1,
     *                 "pic": null, 评论者头像
     *                 "shopName": null,
     *                 "replyComentNumber": 0
     *             }
     *         ],
     *         "map": null
     *   }
     *   }
     */
    @ApiOperation(value = "宣传评论", notes = "宣传评论")
    @GetMapping(value = "/listCommentHDPage")
    public ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> listCommentHDPage(String goodsName,long shopId,@Valid EntyPage page ){
        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",shopId);
        map.put("goodsName",goodsName);
        List<C05CommentDO> c05CommentDOS =shopEvaluateService.listCommentHDPage(map);
        //封装到分页
        PageSimpleVO<C05CommentDO> shopEvaluatePageSimpleVO = new PageSimpleVO<>();
        shopEvaluatePageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopEvaluatePageSimpleVO.setList(c05CommentDOS);
        return ResultVOUtils.success(shopEvaluatePageSimpleVO);
    }



    /**
     * @api {POST} /app/myShop/replyComment 004宣传店铺回复评论接口
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传店铺回复评论接口
     * @apiParam {String} reply 回复内容 [必填]
     * @apiParam {int} id 评论ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @ApiOperation(value = "宣传回复", notes = "宣传回复")
    @PostMapping(value = "/replyComment")
    public ResultVo  replyComment(@Valid C05CommentReplyForm form ){

        C05CommentDO commentById = shopEvaluateService.getCommentById(form.getId());
        if(commentById==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到评论数据");
        }
        commentById.setReply(form.getReply());
        int i = shopEvaluateService.updateCommentById(commentById);
        if(i<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"回复失败");
        }
        return ResultVOUtils.success();
    }


    /**
     * @api {POST} /app/myShop/delComment 005宣传店铺删除评论接口
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传店铺删除评论接口
     * @apiParam {int} id 评论ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @ApiOperation(value = "删除宣传评论", notes = "删除宣传评论")
    @PostMapping(value = "/delComment")
    public ResultVo delComment(String id){
        if(id == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C05CommentDO> commentDo = new ArrayList<>();
        String[] split = id.split(",");
        for (String commentId:split){
            C05CommentDO commentBy = shopEvaluateService.getCommentById((Long.valueOf(commentId)));
            if (commentBy != null){
                commentBy.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                commentBy.setUpdateTime(LocalDateTime.now());
                commentDo.add(commentBy);
            }
        }
        return ResultVOUtils.success(shopEvaluateService.batckUpdateComment(commentDo));
    }

    /**
     * @api {Get} /app/myShop/putOrOff 006宣传作品上下架接口
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 宣传作品上下架接口
     * @apiParam {Long} publicityId 宣传ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @ApiOperation(value = "宣传作品上下架接口", notes = "宣传作品上下架接口")
    @GetMapping(value = "/putOrOff")
    public ResultVo PutOrOff(Long publicityId){
        int i = c10PublicityService.PutOrOff(publicityId);
        if(i == 1){
            return ResultVOUtils.success();
        }else{
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"上下架失败");
        }
    }

    /**
     * @api {Get} /app/myShop/delPublicity 007删除宣传作品接口
     * @apiHeader {String} X-Token 用户登录时返回的token
     * @apiHeader {String} X-Type 固定值 APP
     * @apiHeader {String} X-UserId 用户Id
     * @apiGroup 002宣传店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 删除宣传作品接口
     * @apiParam {Long} publicityId 宣传ID [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     *   }
     */
    @GetMapping(value = "/delPublicity")
    @AuthRuleAnnotation()
    public ResultVo delPublicity(Long publicityId){
        int i = c10PublicityService.delPublicity(publicityId);
        if(i == 1){
            return ResultVOUtils.success();
        }else{
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"删除失败");
        }
    }

    @ApiOperation(value = "根据商家获取商家的宣传视频信息；参数——商家ID")
    @GetMapping("/getVideoByShopId")
    @AuthRuleAnnotation()
    ResultVo getVideoByShopId(@Valid C10PublicitySearchForm form  ) {

        QueryMap map = new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        map.put("type",StatusConstant.PublicityType.video.getCode());
        List<C10PublicityDO> list = c10PublicityService.list(map);
        if(CollectionUtils.isEmpty(list)){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.success(list.get(0));
    }

//    @ApiOperation(value = "退款：参数——售后记录ID")
//    @PostMapping(value = "/refundOrder")
//    public ResultVo refundOrder(Long  id){
//        if(id==null||id==0){
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"参数不能为空");
//        }
//        ResultVo vo =customerServiceService.refundOrder(id);
//        return vo;
//    }

    @ApiOperation(value = "订单发货")
    @PostMapping(value = "/editOrderStatusById")
    public ResultVo editOrderStatusById(@Valid  ShopOrderSendForm form){
        return ResultVOUtils.success(shopOrderService.send(form));
    }
    @ApiOperation(value = "审核通过")
    @PostMapping(value = "/examineOrderPass")
    @AuthRuleAnnotation()
    public ResultVo examineOrderPass(@Valid CustomerServiceUpdateForm form){
        return  customerServiceService.examineOrderPassApp(form);
    }
    @ApiOperation(value = "审核拒绝")
    @PostMapping(value = "/examineOrderRefuse")
    @AuthRuleAnnotation()
    public ResultVo examineOrderRefuse(@Valid CustomerServiceUpdateForm form){
        customerServiceService.examineOrderRefuseApp(form);
        return ResultVOUtils.success();
    }


    @ApiOperation(value = "发起提现申请", notes = "发起提现申请")
    @PostMapping(value = "/creatWithdrawalApplication")
    @AuthRuleAnnotation()
    public ResultVo creatWithdrawalApplication(@Valid @RequestBody F06WithdrawalApplicationSaveForm dto){
        if(dto == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        //通过提现申请的账号id查询账号信息
        F05ShopAccountDO f05ShopAccountDO = f05ShopAccountService.get(dto.getAccountId());
        QueryMap queryMap = new QueryMap();
        queryMap.put("shopId",f05ShopAccountDO.getShopId());
        queryMap.put("delete_flag",StatusConstant.CreatFlag.delete.getCode());
        //通过账号信息里的商家id去查商家账号的用户信息
        F08ShopUserDO f08ShopUserDO= f08ShopAppService.getf08ShopUserDO(queryMap);
        //判断有没有这商家用户
        if(f08ShopUserDO==null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.DATA_NOT_EXIST.getMessage());
        }
        //判断商家用户是否被冻结,假如冻结直接提示用户
        if(f08ShopUserDO.getStatus()==StatusConstant.disable.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.ONFREEZE_FAIL.getMessage());
        }
        if(dto.getWithdrawalMoney().compareTo(f05ShopAccountDO.getAvailableWithdrawMoney())>0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"提现金额不能大于可提现金额");
        }
        //计算实际提现到账金额
        BigDecimal bigDecimal =  DecimalUtil.subtractionBigMal(String.valueOf(dto.getWithdrawalMoney()),String.valueOf(dto.getServiceMoney()),0,0);
        F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();
        BeanUtils.copyProperties(dto,entity);
        entity.setActualWithdrawalMoney(bigDecimal);//实际提现金额
        entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        entity.setApplyTime(LocalDateTime.now());

        return ResultVOUtils.success(f05ShopAccountService.save(entity));
    }

    @ApiOperation(value = "保存微信授权openid", notes = "保存微信授权openid")
    @PostMapping(value = "/updateUserOpenId")
    @AuthRuleAnnotation()
    public ResultVo updateUserOpenId(Long userId,String weChatopenId){
        B01UserDO b01UserDO = b01UserAppService.get(userId);
        b01UserDO.setWechatOpenid(weChatopenId);
        b01UserAppService.update(b01UserDO);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "获取提现参数——ID")
    @PostMapping(value = "/getWithdrawalParam")
    @AuthRuleAnnotation()
    public ResultVo getWithdrawalParam(Long id){
        WithdrawalParamVO vo=f05ShopAccountService.getWithdrawalParam(id);
        return ResultVOUtils.success(vo);
    }

    @ApiOperation(value = "提现列表", notes = "提现列表")
    @GetMapping(value = "/pageWithdrawalApplication")
    @AuthRuleAnnotation()
    public ResultVo<PageSimpleVO<F06WithdrawalApplicationDO>> pagePublicity(@Valid ShopWithdrawalApplicationPageForm form, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<F06WithdrawalApplicationDO> withdrawalApplication = shopWithdrawalApplicationService.listHDPage(map);
        //封装到分页
        PageSimpleVO<F06WithdrawalApplicationDO> shopWithdrawalApplicationPageSimpleVO = new PageSimpleVO<>();
        shopWithdrawalApplicationPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopWithdrawalApplicationPageSimpleVO.setList(withdrawalApplication);
        return ResultVOUtils.success(shopWithdrawalApplicationPageSimpleVO);
    }

   @ApiOperation(value = "宣传店铺获取信息")
    @GetMapping(value = "/getAccountByShopId")
    @AuthRuleAnnotation()
    public ResultVo  getAccountByShopId(Long shopId) {
        List<F05ShopAccountDO> accountByShopId = f05ShopAccountService.getAccountByShopId(shopId);
        if(CollectionUtils.isEmpty(accountByShopId)){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"商家不存在");
        }
        return ResultVOUtils.success(accountByShopId.get(0));
    }
    @ApiOperation(value = "获取宣传店铺流水")
    @GetMapping(value = "/getAccountFlow")
    @AuthRuleAnnotation()
    public ResultVo  getAccountFlow(Long accountId,EntyPage page) {
        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("accountId",accountId);
        List<F07ShopAccountFlowDO> flow = f05ShopAccountService.getFlow(map);
        return ResultVOUtils.success(flow);
    }
}
