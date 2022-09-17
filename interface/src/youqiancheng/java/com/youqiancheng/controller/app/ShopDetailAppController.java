package com.youqiancheng.controller.app;//package com.youqiancheng.controller.app;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.UserGetRedEnvelopesAbility;
import com.youqiancheng.form.app.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.ReceiveRecordNumVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"手机端-商家详情页面接口"})
@RestController
@RequestMapping(value = "app/shop")
public class ShopDetailAppController {
    @Autowired
    private F01ShopAppService f01ShopService;
    @Autowired
    private C01GoodsAppService c01GoodsService;
    @Autowired
    private B05CollectionAppService b05CollectionService;
    @Autowired
    private E04RedenvelopesGrantRecordAppService e04RedenvelopesGrantRecordAppService;
    @Autowired
    private E05RedenvelopesReceiveRecordAppService e05RedenvelopesReceiveRecordAppService;
    @Autowired
    private UserGetRedEnvelopesAbility userGetRedEnvelopesAbility;


    /**
     * @api {GET} /app/shop/getShopInfo 001获取店铺信息接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取店铺信息接口
     * @apiParam {int} id 店铺Id[必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *   "f01ShopDO": {
     *             "id": 232, 店铺ID
     *             "type": 1, 店铺类型 1:商品店铺 2：宣传店铺
     *             "mainProject": 1, 1：线上商品 2：平台类 3：实体店 4：个人宣传店铺 5：企业宣传店铺
     *             "projectName": null,
     *             "countryId": 0,
     *             "countryName": "中国",
     *             "provinceCode": "110000",
     *             "cityCode": "110100",
     *             "areaCode": "110101",
     *             "province": "北京市", 省
     *             "city": "北京市", 市
     *             "area": "东城区", 区
     *             "address": "大望路", 详细地址
     *             "longitude": "116.4816", 经度
     *             "latitude": "39.92183", 纬度
     *             "name": "158的店铺", 店铺名称
     *             "browseVolume": 1,
     *             "contacts": "甄", 联系人
     *             "phone": "13520289158", 联系电话
     *             "logo": "http://client.youqiancheng.vip/files/49/98/1606269265579.jpg", 店铺logo
     *             "shopDesc": "测试", 店铺简介
     *             "orderNum": 0,
     *             "examineStatus": 2,
     *             "hide": 1,
     *             "reason": null,
     *             "examineTime": null,
     *             "status": 1,
     *             "createPerson": "13520289158",
     *             "createTime": "2020-08-21 14:41:40",
     *             "updatePerson": "13520289158",
     *             "updateTime": "2020-11-25 10:38:15",
     *             "deleteFlag": 1,
     *             "userId": 409,
     *             "collectionVolume": 0,
     *             "accountBalance": null,
     *             "distance": null,
     *             "sumGoods": null,
     *             "alipayUrl": "null",
     *             "wechatUrl": "null",
     *             "picList": [
     *      *             {
     *      *                 "id": 735, 图片ID
     *      *                 "shopId": 232, 店铺ID
     *      *                 "type": 1, 图片类型 1：营业执照 2：身份证照  3：其他证据照
     *      *                 "picUrl": "http://192.168.1.151:8080/files/89/16/1597992091468.jpg", 图片url
     *      *                 "createPerson": "13520289158",
     *      *                 "createTime": "2020-08-21 14:41:40",
     *      *                 "updatePerson": "13520289158",
     *      *                 "updateTime": "2020-08-21 14:41:40",
     *      *                 "deleteFlag": 1
     *      *             },
     *      *             {
     *      *                 "id": 736,
     *      *                 "shopId": 232,
     *      *                 "type": 2,
     *      *                 "picUrl": "http://192.168.1.151:8080/files/85/60/1597992093652.jpg",
     *      *                 "createPerson": "13520289158",
     *      *                 "createTime": "2020-08-21 14:41:40",
     *      *                 "updatePerson": "13520289158",
     *      *                 "updateTime": "2020-08-21 14:41:40",
     *      *                 "deleteFlag": 1
     *      *             },
     *      *             {
     *      *                 "id": 737,
     *      *                 "shopId": 232,
     *      *                 "type": 2,
     *      *                 "picUrl": "http://192.168.1.151:8080/files/85/13/1597992095238.jpg",
     *      *                 "createPerson": "13520289158",
     *      *                 "createTime": "2020-08-21 14:41:40",
     *      *                 "updatePerson": "13520289158",
     *      *                 "updateTime": "2020-08-21 14:41:40",
     *      *                 "deleteFlag": 1
     *      *             }
     *      *         ]
     *         },
     *         "weChatOpenId": null
     *     }
     * }
     */
    @ApiOperation(value = "获取商家详情信息；参数——商家ID")
    @GetMapping("/getShopInfo")
    ResultVo getShopInfo( Long id) {
        F01ShopDO f01ShopDO = f01ShopService.getShop(id);
        return ResultVOUtils.success(f01ShopDO);
    }

    /**
     * @api {POST} /app/shop/appShopApplyEnters 002编辑店铺信息接口
     * @apiGroup 001商品店铺相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 编辑店铺信息接口
     * @apiParam {String} token 用户token [必填]
     * @apiParam {int} id 店铺Id[必填]
     * @apiParam {int} userId 用户ID [必填]
     * @apiParam {int} type 店铺类型 [必填] 1:商品店铺 2：宣传店铺
     * @apiParam {int} mainProject 主营项目 [必填] 1：线上商品 2：平台类 3：实体店 4：个人宣传店铺 5：企业宣传店铺
     * @apiParam {String} name 店铺名称 [必填]
     * @apiParam {String} contacts  联系人 [必填]
     * @apiParam {String} phone 电话 [必填]
     * @apiParam {String} logo 店铺logoUrl [必填]
     * @apiParam {String} shopDesc 店铺简介 [必填]
     * @apiParam {int} hide 是否隐藏电话 [必填] 1：否 2：是
     * @apiParam {String} provinceCode 省编码 [必填]
     * @apiParam {String} province 省名称 [必填]
     * @apiParam {String} cityCode 市编码 [必填]
     * @apiParam {String} city 市 [必填]
     * @apiParam {String} areaCode 区编码 [必填]
     * @apiParam {String} area 区 [必填]
     * @apiParam {String} address 详情地址 [必填]
     * @apiParam {String} longitude 经度 [必填]
     * @apiParam {String} latitude 纬度 [必填]
     * @apiParam {array} licenseList 营业执照数组 [必填]
     * @apiParam {array} idCardList 身份证照数组 [必填]
     * @apiParam {array} otherInfoList 其他资料证件照数组 [必填]
     * @apiParam {String} alipayUrl 支付宝收款码 [非必填]
     * @apiParam {String} wechatUrl 微信收款码 [非必填]
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
    @PostMapping("/appShopApplyEnters")
    //@AuthRuleAnnotation()
    ResultVo appShopApplyEnters(@RequestBody @Valid com.youqiancheng.form.client.F01ShopAppSaveForm f01Shop ) {
        f01ShopService.updateApplyEnters(f01Shop);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "根据店铺查询商品（房间;参数——商家ID")
    @GetMapping("/getListByShopId")
    ResultVo<PageSimpleVO<C01GoodsDO>> getGoodsListByShopId(@Valid C01GoodsAppListForm form,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        List<C01GoodsDO> c01GoodsList = c01GoodsService.listGoods(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> c01GoodsSimpleVO = new PageSimpleVO<>();
            //c01GoodsSimpleVO.setTotalNumber(page.getTotalNumber());
            c01GoodsSimpleVO.setList(c01GoodsList);

        return ResultVOUtils.success(c01GoodsSimpleVO);
    }

    @ApiOperation(value = "获取商家上架商品总数;参数——商家ID")
    @GetMapping("/getGoodsCountByShopId")
    ResultVo getGoodsCountByShopId(@Valid C01GoodsAppListForm form  ) {

        QueryMap map = new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        int count = c01GoodsService.count(map);

        return ResultVOUtils.success(count);
    }


    @ApiOperation(value = "获取商家被收藏数量;参数——商家ID")
    @GetMapping("/getCollectionShopCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getShopCount(@Valid B05CollectionSearchCountForm form ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "获取商家是否被用户收藏——收藏数为0则未收藏;参数——商家ID,用户ID")
    @GetMapping("/getIsCollectionShop")
    @AuthRuleAnnotation()
    ResultVo  getIsCollectionShop(@Valid B05CollectionSearchIsForm form  ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "收藏商家;参数——商家ID,用户ID")
    @PostMapping("/collectionShop")
    @AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
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

    @ApiOperation(value = "取消收藏商家;参数——商家ID,用户ID")
    @PostMapping("/cancelCollectionShop")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
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

    @ApiOperation(value = "获取店铺红包列表；参数——街区ID，商家ID")
    @PostMapping("/getRedPacketList")
    ResultVo getRedPacketList(@RequestBody E04GrantRecordSearchForm form){
        QueryMap map =new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
        List<E05RedenvelopesReceiveRecordDO> shopRedPacket = e04RedenvelopesGrantRecordAppService.getShopRedPacket(map);
        if(shopRedPacket==null||shopRedPacket.isEmpty()){
            return ResultVOUtils.success(shopRedPacket);

        }
        return ResultVOUtils.success(shopRedPacket);

    }

    int flag = 1;
    @ApiOperation(value = "抢红包；参数——用户ID,红包记录ID（红包列表返回的ID）")
    @PostMapping("/receiveRedPacket")
    @AuthRuleAnnotation()
    ResultVo receiveRedPacket( E05ReceiveRecordUpdateForm form){
        ReceiveRecordSearchForm form1=new ReceiveRecordSearchForm();
        form1.setShopId(form.getShopId());
        form1.setStreetId(form.getStreetId());
        form1.setUserId(form.getUserId());
        //ReceiveRecordNumVO vo= e05RedenvelopesReceiveRecordAppService.getNum(form1);
        synchronized(this) {
            if(flag == 1){
                try{
                    flag = 0;
                    ReceiveRecordNumVO vo = userGetRedEnvelopesAbility.getReceiveRedPacketNum(form1);

                    if (vo.getUnReceiveNum() == 0) {
                        flag = 1;
                        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "您领取红包个 数已达到上限");
                    }
                    e05RedenvelopesReceiveRecordAppService.receiveRedPacket(form);
                    flag = 1;
                }catch (Exception e){
                    flag = 1;
                }

            }

        }

        //
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "可以抢红包个数；参数——街区ID，商家ID")
    @GetMapping("/getReceiveRedPacketNum")
    @AuthRuleAnnotation()
    ResultVo getReceiveRedPacketNum(ReceiveRecordSearchForm form){
        ReceiveRecordNumVO vo = userGetRedEnvelopesAbility.getReceiveRedPacketNum(form);
        return ResultVOUtils.success(vo);
    }

    @ApiOperation(value = "获取红包图片")
    @GetMapping("/getRedPacketUrl")
    ResultVo getRedPacketUrl(){
        E03RedenvelopesRuleDO url= e05RedenvelopesReceiveRecordAppService.getRedPacketUrl();
        return ResultVOUtils.success(url);
    }
}
