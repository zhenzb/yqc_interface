package com.youqiancheng.controller.client;

import com.alibaba.fastjson.JSON;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.easemob.method.ChatMessagesTest;
import com.handongkeji.util.AESUtil;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.UserGetRedEnvelopesAbility;
import com.youqiancheng.controller.websocket.GreetingController;
import com.youqiancheng.controller.wechatpay.weixinpay.util.IpAddressUtil;
import com.youqiancheng.form.client.*;
import com.youqiancheng.mybatis.dao.F02ShopPicDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.ReceiveRecordNumVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"PC端-商家详情"})
@RestController
@RequestMapping(value = "pc/shop")
public class ShopDetailClientController {
    @Autowired
    private F01ShopClientService f01ShopService;
    @Autowired
    private C01GoodsClientService c01GoodsService;
    @Autowired
    private B05CollectionClientService b05CollectionService;
    @Autowired
    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
    @Autowired
    private E05RedenvelopesReceiveRecordClientService e05RedenvelopesReceiveRecordClientService;
    @Autowired
    private E01RedenvelopesStreetClientService e1RedenvelopesStreetClientService;
    @Autowired
    private UserGetRedEnvelopesAbility userGetRedEnvelopesAbility;
    @Autowired
    private F18LinksAppService f18LinksService;
    @Resource
    private F02ShopPicDao f02ShopPicDao;

    private static final Logger logger = LoggerFactory.getLogger(ShopDetailClientController.class);
    //存储进入店铺的实时人数
    Map<Integer,Integer> peopleNumberMap = new HashMap<Integer,Integer>();
    Map<String,String> peopleIpMap = new HashMap<String,String>();
    int totalPeopleNum = 0;
//    Map<Integer,List<String>> userMap = new HashMap<>();
//    List<String> userList = new ArrayList<>();

    @ApiOperation(value = "获取商家详情信息；参数——商家ID")
    @GetMapping("/getShopInfoById")
    ResultVo getShopInfo(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家ID不能为空");
        }
        F01ShopDO f01ShopDO = f01ShopService.get(id);
        if(f01ShopDO !=null){
            //获取店铺内友情链接
            Map<String, Object> map = new HashMap<>();
            map.put("sourceId",id);
            map.put("isDelete",1);
            List<F18LinksDO> f18LinksDOS = f18LinksService.list(map);
            f01ShopDO.setLinksList(f18LinksDOS);
            List<String> imgList= new ArrayList<>();
            if(f01ShopDO.getAlipayUrl()!=null && f01ShopDO.getAlipayUrl().length()>0){
                String[] split = f01ShopDO.getAlipayUrl().split(",");
                imgList = Arrays.asList(split);
            }
            f01ShopDO.setImgList(imgList);
        }
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("shopId",id);
        //商家资质照片
        List<F02ShopPicDO> list = f02ShopPicDao.list(map);
        List<F02ShopPicDO> collect = list.stream().filter(item -> 2 != item.getType()).collect(Collectors.toList());
        f01ShopDO.setPicList(collect);
        return ResultVOUtils.success(f01ShopDO);
    }

    /**
     * @api {GET} /pc/shop/getPeopleNumber 001店铺内实时在线人数
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取店铺内实时在线人数
     * @apiParam {int} type 请求类型 1：进入店铺 2：退出店铺 3：循环调用
     * @apiParam {int} shopId 店铺Id
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0,           请求成功
     *   "message": "success",
     *   "data": 1            店铺内实时在线人数
     * }
     */
    @GetMapping("/getPeopleNumber")
    ResultVo getPeopleNumber(Integer type,Integer shopId,String userId){
        synchronized(this){
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            //HttpServletRequest request = requestAttributes.getRequest();
            // 获取真实的ip地址
           // String ipAddr = IpAddressUtil.getIpAddr(request);
            //logger.info("IP : " + ipAddr);
            if(3 != type){
                logger.info("进入/退出店铺: {} {}",type,shopId);
            }
            Integer integer = peopleNumberMap.get(shopId);
            if(integer == null || integer <=0){
                integer = 0;
            }
            //String ip = peopleIpMap.get(ipAddr);
            if(type == 1){ //进入店铺
                logger.info("进入店铺userId,{}",userId);
                    if(userId!=null && peopleIpMap.get(userId) == null){
                        integer += 1;
                        peopleNumberMap.put(shopId,integer);
                        peopleIpMap.put(userId,userId);

                    }

            }else if(type == 2){ //退出店铺
                logger.info("退出店铺userId,{}",userId);
                if(integer !=null && integer !=0 && userId !=null){
                    integer -= 1;
                    peopleNumberMap.put(shopId,integer);
                    peopleIpMap.remove(userId);
                }
            }
            Integer integer1 = peopleNumberMap.get(shopId);
            //websocket实时通知前端客户 (响铃+弹框)
//        if(shopId !=null){
//            GreetingController greetingController = new GreetingController();
//
//            greetingController.sendOneMessage(String.valueOf(shopId),String .valueOf(integer1));
//            logger.info("socket推送消息： 店铺id"+shopId+" "+"店内人数："+integer1 );
//        }
            if(integer1==null || integer1<0){
                integer1 = 0;
            }

            return ResultVOUtils.success(integer1);
        }
    }


    /**
     * @api {GET} /pc/shop/getYqcPeopleNumber 100平台内实时在线人数
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取店铺内实时在线人数
     * @apiParam {int} type 请求类型 1：进入店铺 2：退出店铺 3：循环调用
     * @apiParam {int} shopId 店铺Id
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0,           请求成功
     *   "message": "success",
     *   "data": 1            店铺内实时在线人数
     * }
     */
    @GetMapping("/getYqcPeopleNumber")
    ResultVo getYqcPeopleNumber(Integer type,String userId){
        synchronized(this){
            if(3 != type){
                logger.info("进入/退出平台: {}",type);
            }
            if(type == 1){ //进入店铺
                logger.info("进入平台userId,{}",userId);
                totalPeopleNum++;
                logger.info("进入平台后的人数：{}",totalPeopleNum);
            }else if(type == 2){ //退出店铺
                logger.info("退出平台userId,{}",userId);
                totalPeopleNum--;
                logger.info("退出平台后的人数：{}",totalPeopleNum);
            }
            return ResultVOUtils.success(totalPeopleNum);
        }
    }

    @ApiOperation(value = "更新商家店铺浏览量；参数——商家ID")
    @GetMapping("/updateShopBrowseVolume")
    ResultVo updateShopBrowseVolume(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家ID不能为空");
        }
        F01ShopDO f01ShopDO = f01ShopService.getShop(id);
        return ResultVOUtils.success(f01ShopDO);
    }


    @ApiOperation(value = "根据店铺查询商品（房间）;参数——商家ID")
    @GetMapping("/getGoodsListByShopId")
    ResultVo<PageSimpleVO<C01GoodsDO>> getGoodsListByShopId(@Valid C01GoodsAppListForm form , @Valid EntyPage page ) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        List<C01GoodsDO> c01GoodsList = c01GoodsService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> c01GoodsSimpleVO = new PageSimpleVO<>();
        c01GoodsSimpleVO.setTotalNumber(page.getTotalNumber());
        c01GoodsSimpleVO.setList(c01GoodsList);

        return ResultVOUtils.success(c01GoodsSimpleVO);
    }

    @ApiOperation(value = "获取商家上架商品总数;参数——商家ID")
    @GetMapping("/getGoodsCountByShopId")
    ResultVo getGoodsCountByShopId(@Valid C01GoodsAppListForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        int count = c01GoodsService.count(map);

        return ResultVOUtils.success(count);
    }


    @ApiOperation(value = "收藏商家;参数——商家ID,用户ID")
    @PostMapping("/collectionShop")
    //@AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody @Valid B05CollectionSaveForm dto ) {

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

    @ApiOperation(value = "获取商家被收藏数量;参数——商家ID")
    @GetMapping("/getCollectionShopCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getShopCount(@Valid B05CollectionSearchCountForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }
    @ApiOperation(value = "获取商家是否被用户收藏数量——数量为0 则为未收藏;参数——商家ID,用户ID")
    @GetMapping("/getIsCollectionShop")
    //@AuthRuleAnnotation()
    ResultVo getIsCollectionShop(@Valid B05CollectionSearchIsForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "取消收藏商家;参数——商家ID,用户ID")
    @PostMapping("/cancelCollectionShop")
    //@AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody @Valid B05CollectionSaveForm dto ) {

        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num=b05CollectionService.delete(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"取消收藏商品失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "获取店铺红包列表;参数——街区ID,商家ID")
    @PostMapping("/getRedPacketList")
    ResultVo getRedPacketList(@RequestBody @Valid E04GrantRecordSearchForm form) {

        QueryMap map =new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("endFlag",StatusConstant.EndFlag.un_end.getCode());
        List<E05RedenvelopesReceiveRecordDO> shopRedPacket = e04RedenvelopesGrantRecordClientService.getShopRedPacket(map);
        if(shopRedPacket==null||shopRedPacket.isEmpty()){
            return  ResultVOUtils.success();
        }
        if(form.getAppType() != null){
            return  ResultVOUtils.success(shopRedPacket);
        }
        String test1 = JSON.toJSONString(shopRedPacket);
        String encrypt = null;
        try {
            String test =new String(test1.getBytes(),"UTF-8");
            encrypt = AESUtil.encrypt(test);
            return ResultVOUtils.success(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtils.success(shopRedPacket);

    }

    int flag = 1;
    @ApiOperation(value = "抢红包；参数——用户ID,红包记录ID（红包列表返回的ID）")
    @PostMapping("/receiveRedPacket")
    ResultVo receiveRedPacket(@RequestBody @Valid E05ReceiveRecordUpdateForm form ) {
        com.youqiancheng.form.app.ReceiveRecordSearchForm form1 = new com.youqiancheng.form.app.ReceiveRecordSearchForm();
        BeanUtils.copyProperties(form,form1);
       // ReceiveRecordNumVO vo= e05RedenvelopesReceiveRecordClientService.getNum(form1);
        synchronized(this){
            if(flag == 1){
                try{
                    flag = 0;
                    com.youqiancheng.vo.app.ReceiveRecordNumVO vo = userGetRedEnvelopesAbility.getReceiveRedPacketNum(form1);
                    if(vo.getUnReceiveNum()==0){
                        flag = 1;
                        return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"您领取红包个数已达到上限");
                    }
                    BigDecimal bigDecimal;
                    if(vo.getIsFree() == 1 && vo.getTotalNum()>0 && vo.getUnReceiveNum()>0){
                        bigDecimal = e05RedenvelopesReceiveRecordClientService.receiveRedPacket(form);
                        flag = 1;
                        return ResultVOUtils.success(bigDecimal);
                    }else if(vo.getIsFree() == 2 ){
                        bigDecimal = e05RedenvelopesReceiveRecordClientService.receiveRedPacket(form);
                        flag = 1;
                        return ResultVOUtils.success(bigDecimal);
                    }else{
                        flag = 1;
                        return ResultVOUtils.success(0);
                    }
                }catch (Exception e){
                    flag = 1;
                    return  ResultVOUtils.success(0);
                }
            }else{
                return ResultVOUtils.success(0);
            }
        }
    }
    @ApiOperation(value = "获取街区信息；参数——街区ID")
    @PostMapping("/getStreetInfo")
    ResultVo getStreetInfo(Long id ) {

        E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e1RedenvelopesStreetClientService.get(id);
        return ResultVOUtils.success(e01RedenvelopesStreetDO);

    }


    @ApiOperation(value = "可以抢红包个数；参数——街区ID，商家ID")
    @GetMapping("/getReceiveRedPacketNum")
    ResultVo getReceiveRedPacketNum(@Valid ReceiveRecordSearchForm form ) {
        com.youqiancheng.form.app.ReceiveRecordSearchForm form1=new com.youqiancheng.form.app.ReceiveRecordSearchForm();
        BeanUtils.copyProperties(form,form1);
        com.youqiancheng.vo.app.ReceiveRecordNumVO vo = userGetRedEnvelopesAbility.getReceiveRedPacketNum(form1);
        return ResultVOUtils.success(vo);
    }

    @ApiOperation(value = "获取红包图片")
    @GetMapping("/getRedPacketUrl")
    ResultVo getRedPacketUrl(){
        E03RedenvelopesRuleDO dto= e05RedenvelopesReceiveRecordClientService.getRedPacketUrl();
        return ResultVOUtils.success(dto);
    }
}
