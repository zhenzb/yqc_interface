package com.youqiancheng.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.APIUtil;
import com.handongkeji.util.Constants;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ShopSendRedEnvelopesAbility;
import com.youqiancheng.ability.SignacontractAbility;
import com.youqiancheng.ability.UserAccountFlowAbility;
import com.youqiancheng.form.app.*;
import com.youqiancheng.form.shop.F06WithdrawalApplicationSaveForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.service.client.service.D03CustomerServiceClientService;
import com.youqiancheng.service.client.service.F05ShopAccountService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.app.D02OrderItemVO;
import com.youqiancheng.vo.app.OrderVO;
import com.youqiancheng.vo.client.D03CustomerVo;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.IfNode;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"手机端-我的接口"})
@RestController
@RequestMapping(value = "app/my")
public class MyAppController {

    protected static final Log logger = LogFactory.getLog(MyAppController.class);
    @Autowired
    private A08ContactUsAppService a08ContactUsService;
    @Autowired
    private E01RedenvelopesStreetAppService e01RedenvelopesStreetService;
    @Autowired
    private A14UserKnowAppService a14UserKnowService;
    @Autowired
    private B02UserAccountAppService b02UserAccountService;
    @Autowired
    private B03UserAccountFlowAppService b03UserAccountFlowAppService;
    @Autowired
    private B05CollectionAppService b05CollectionService;
    @Autowired
    private B06UserAddressAppService b06UserAddressService;
    @Autowired
    private B07AuthenticationAppService b07AuthenticationService;
    @Autowired
    private D01OrderAppService d01OrderService;
    @Autowired
    private D02OrderItemAppService d02OrderItemAppService;
    @Autowired
    private D04GoodsEvaluateAppService d04GoodsEvaluateService;
    @Autowired
    private B01UserAppService b01UserAppService;
    @Autowired
    private B12PromotionAccountDao b12PromotionAccountDao;
    @Autowired
    private D01OrderDao d01OrderDaO;
    @Autowired
    private F04ShopExamineSetAppService f04ShopExamineSetService;
    @Autowired
    private C04GoodsExamineSetAppService c04GoodsExamineSetService;
    @Autowired
    private E04RedenvelopesGrantRecordAppService e04RedenvelopesGrantRecordAppService;
    @Autowired
    private  F08ShopAppService f08ShopAppService;
    @Autowired
    private B07AuthenticationDao b07AuthenticationDao;
    @Autowired
    private F05ShopAccountService f05ShopAccountService;
    @Autowired
    private ShopSendRedEnvelopesAbility shopSendRedEnvelopesAbility;

    @Autowired
    private SignacontractAbility signacontractAbility;

    @Autowired
    private D03CustomerServiceClientService d03CustomerServiceService;
    @Autowired
    private D03CustomerServiceDao d03CustomerServiceDao;
    @Autowired
    private UserAccountFlowAbility userAccountFlowAbility;

    @ApiOperation(value = "我要上街——获取红包街列表;参数——一级分类ID")
    @GetMapping("/getRedStreetList")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<E01RedenvelopesStreetDO>> getRedStreetList(@Valid E01RedenvelopesStreetSearchForm form, @Valid EntyPage page) {
        page.setPageSize(100);
        QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
        //一级分类是按国家分的,一个国家可能有多条街,并且每个国家的街也有不同
        List<E01RedenvelopesStreetDO> e01RedenvelopesStreetList = e01RedenvelopesStreetService.listE01Redenve(map);
        //封装到分页
        PageSimpleVO<E01RedenvelopesStreetDO> e01RedenvelopesStreetSimpleVO = new PageSimpleVO<>();
       // e01RedenvelopesStreetSimpleVO.setTotalNumber(page.getTotalNumber());
        e01RedenvelopesStreetSimpleVO.setList(e01RedenvelopesStreetList);
        return ResultVOUtils.success(e01RedenvelopesStreetSimpleVO);
    }

    @ApiOperation(value = "保存红包发放记录——我要上街")
    @PostMapping("/saveGrantInfo")
    @AuthRuleAnnotation()
    ResultVo getStreetList(@Valid E04GrantRecordSearchForm form) {
       //在上街之前先判断下这个商家是否被冻结
        QueryMap map = new QueryMap();
        map.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        map.put("shopId",form.getShopId());
        F08ShopUserDO f08ShopUserDO = f08ShopAppService.getf08ShopUserDO(map);
        if(f08ShopUserDO==null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.DATA_NOT_EXIST.getMessage());
        }
        //判断商家用户是否被冻结,假如冻结直接提示用户
        if(f08ShopUserDO.getStatus()==StatusConstant.disable.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.ONFREEZE_FAIL.getMessage());
        }
        //long id = e04RedenvelopesGrantRecordAppService.insert(form);
        long id = shopSendRedEnvelopesAbility.saveShopSendRedEnvelopesRecord(form);
        //封装到分页
        return ResultVOUtils.success(id);
    }


    @ApiOperation(value = "用户获取订单列表；参数——用户ID,订单状态，是否评论")
    @GetMapping("/getOrderListByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<D01OrderDO>> getOrderListByUserId(@Valid D02OrderItemSearchForm form, @Valid EntyPage page) {

        //待支付订单——(1:待支付;
        if (form.getOrderStatus() == StatusConstant.OrderStatus.un_pay.getCode()) {
            //检验过滤待支付订单——将超时未支付订单改为已取消
            d01OrderService.cancelOrderByTime(form.getUserId());
            QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
            List<D06PayOrderDO> d06PayOrderDOS = d01OrderService.listPayOrder(map);
            PageSimpleVO<D06PayOrderDO> d01OrderSimpleVO = new PageSimpleVO<>();
            d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderSimpleVO.setList(d06PayOrderDOS);
            return ResultVOUtils.success(d01OrderSimpleVO);
        }//其他订单2:已支付/待发货;3:已发货/待收货
        else if (form.getOrderStatus() == StatusConstant.OrderStatus.pay.getCode() ||
                form.getOrderStatus() == StatusConstant.OrderStatus.send.getCode()) {
            QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
            List<D01OrderVO> list = d01OrderService.list(map);
            for(int i=0;i<list.size();i++){
                List<D02OrderItemDO> orderItem = list.get(i).getOrderItem();
                if(orderItem.size()==0){
                    list.remove(i);
                }
            }
            //封装到分页
            PageSimpleVO<D01OrderVO> d01OrderSimpleVO = new PageSimpleVO<>();
            d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
            d01OrderSimpleVO.setList(list);
            return ResultVOUtils.success(d01OrderSimpleVO);
        } else {
//            if(StatusConstant.IsEvaluate.un_evaluated.getCode()==form.getIsEvaluate()){
//                form.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
//            }
            if (StatusConstant.OrderStatus.end.getCode() == form.getOrderStatus()) {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemVO> d02OrderItemVOs = d02OrderItemAppService.listHDPage(map);
                //封装到分页
                PageSimpleVO<D02OrderItemVO> d01OrderSimpleVO = new PageSimpleVO<>();
                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemVOs);
                return ResultVOUtils.success(d01OrderSimpleVO);
            } else {
                QueryMap map = new QueryMap(form, page, StatusConstant.CreatFlag.delete.getCode());
                List<D02OrderItemVO> d02OrderItemVOs = d02OrderItemAppService.list5HDPage(map);
                //封装到分页
                PageSimpleVO<D02OrderItemVO> d01OrderSimpleVO = new PageSimpleVO<>();
                d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
                d01OrderSimpleVO.setList(d02OrderItemVOs);
                return ResultVOUtils.success(d01OrderSimpleVO);
            }

        }

    }

    @ApiOperation(value = "用户获取各个订单状态下的个数；参数——用户ID")
    @GetMapping("/getOrderStatusCountByUserId")
    @AuthRuleAnnotation()
    ResultVo<Map<String, Object>> getUserOrderStatusCountByUserId(Long id) {
        if (id == null) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "用户的编号不能为空");
        }
        //先根据用户的id获取这个用户,判断这个用户是否被删除
        B01UserDO b01UserDO = b01UserAppService.get(id);
        if (b01UserDO.getDeleteFlag() ==StatusConstant.DeleteFlag.delete.getCode()) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, "该用户已不存在");
        }
        QueryMap map = new QueryMap(b01UserDO.getDeleteFlag());
        map.put("userId",id);
        map.put("delete_flag",StatusConstant.CreatFlag.delete.getCode());
        Map<String, Object> mapCount = new HashMap<>();
        //再根据用户的id去查用户的支付订单,商家订单,明细扥,返回他们的个数;
        mapCount=d01OrderService.getUserOrderStatusCountByUserId(map);
        return ResultVOUtils.success(mapCount);
    }


    @ApiOperation(value = "全部订单列表；参数——用户ID")
    @GetMapping("/getALLOrderByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<OrderVO>> getALLOrderByUserId(@Valid OrderSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //检验过滤待支付订单——将超时未支付订单改为已取消
        d01OrderService.cancelOrderByTime(form.getUserId());
        //查询订单
        List<OrderVO> orderVOS = d01OrderService.orderList(map);
        //封装到分页
        PageSimpleVO<OrderVO> d01OrderSimpleVO = new PageSimpleVO<>();
        d01OrderSimpleVO.setTotalNumber(page.getTotalNumber());
        d01OrderSimpleVO.setList(orderVOS);
        return ResultVOUtils.success(d01OrderSimpleVO);
    }

    @ApiOperation(value = "取消订单")
    @GetMapping("/cancleOrder")
    @AuthRuleAnnotation()
    ResultVo cancleOrder(Long id) {

        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单编码不能为空或者0");
        }
        D06PayOrderDO payOrderById = d01OrderService.getPayOrderById(id);
        if(payOrderById==null){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单信息不存在");
        }
        if(payOrderById.getOrderStatus()!=StatusConstant.OrderStatus.un_pay.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"非待支付订单不可以取消");
        }
        payOrderById.setOrderStatus(StatusConstant.OrderStatus.cancel.getCode());
        return ResultVOUtils.success(d01OrderService.updatePayOrderById(payOrderById));
    }

//    @ApiOperation(value = "根据订单ID获取商家订单详情；参数——订单ID")
//    @GetMapping("/getOrderInfoById")
//    @AuthRuleAnnotation()
//    ResultVo getOrderInfoById( Long id ){
//        if(id==null||id==0){
//            return  ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"订单ID不能为空或者0");
//        }
//        D01OrderVO d01Order = d01OrderService.get(id);
//        return ResultVOUtils.success(d01Order);
//    }

    /**
     * @api {POST} /app/my/applyAuthentication 005实名认证接口
     * @apiGroup 006提现模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 提现申请列表接口
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-Type 固定值APP [必填]
     * @apiHeader {String} X-UserId 用户Id [必填]
     * @apiParam {Long} userId 用户Id [必填]
     * @apiParam {String} name 用户真实姓名 [必填]
     * @apiParam {String} cardNo 身份证号码 [必填]
     * @apiParam {String} url 身份证证明照 [必填]
     * @apiParam {String} backUrl 身份证反面照 [必填]
     * @apiParam {String} createPerson 创建人 [必填]
     * @apiParam {String} bankCardNum  银行卡号 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *     "code": 0,
     *     "message": "success",
     *     "data": {}
     * }
     */
    @ApiOperation(value = "申请实名认证；参数——认证实体")
    @PostMapping("/applyAuthentication")
    @AuthRuleAnnotation()
    ResultVo applyAuthentication(@RequestBody B07AuthenticationSaveForm dto ) {

        List<B07AuthenticationDO> userList= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()));
        if(userList.size()==0) {
        B07AuthenticationDO b07Authentication=new B07AuthenticationDO();
        BeanUtils.copyProperties(dto,b07Authentication);
        b07Authentication.setCreateTime(LocalDateTime.now());
        b07Authentication.setUpdateTime(LocalDateTime.now());
        b07Authentication.setUpdatePerson(dto.getCreatePerson());
        b07Authentication.setStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        b07Authentication.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        b07Authentication.setBankCardNum(dto.getBankCardNum());
        int num=b07AuthenticationService.insert(b07Authentication);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
            return ResultVOUtils.success(num);
        }else {
            return ResultVOUtils.error(Constants.$Failure,"该账号已经实名认证");
        }
    }

    @ApiOperation(value = "获取实名认证信息；参数——用户ID；结果——若存在则返回认证信息，不存在返回空")
    @GetMapping("/getAuthenticationByUserId")
    @AuthRuleAnnotation()
    ResultVo getAuthenticationByUserId( AuthenticationSearchForm dto ) {
//        QueryMap map=new QueryMap(dto,StatusConstant.CreatFlag.delete.getCode());
//        List<B07AuthenticationDO> list = b07AuthenticationService.list(map);
//        if(list==null||list.isEmpty()){
//            return ResultVOUtils.success();
//        }
        List<B07AuthenticationDO> list= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", dto.getUserId()));
        if(list==null||list.isEmpty()){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.success(list.get(0));
    }


    @ApiOperation(value = "修改实名认证；参数——认证实体")
    @PostMapping("/updateAuthentication")
    @AuthRuleAnnotation()
    ResultVo updateAuthentication(@RequestBody B07AuthenticationUpdateForm form ) {

        B07AuthenticationDO b07AuthenticationDO = b07AuthenticationService.get(form.getId());
        if(b07AuthenticationDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"认证信息不存在");
        }
        if(!StringUtils.isBlank(form.getBackUrl())){
            b07AuthenticationDO.setBackUrl(form.getBackUrl());
        }
        if(!StringUtils.isBlank(form.getUrl())){
            b07AuthenticationDO.setUrl(form.getUrl());
        }
        if(!StringUtils.isBlank(form.getCardNo())){
            b07AuthenticationDO.setCardNo(form.getCardNo());
        }
        if(!StringUtils.isBlank(form.getName())){
            b07AuthenticationDO.setName(form.getName());
        }
        b07AuthenticationDO.setUpdateTime(LocalDateTime.now());
        int num= b07AuthenticationService.update(b07AuthenticationDO);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "根据用户ID获取用户地址;参数 用户ID,分页参数（不传则查询所有）")
    @GetMapping("/getAddressByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getAddressByUserId(@Valid B06UserAddressSearchForm form , @Valid EntyPage page ) {
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B06UserAddressDO> b06UserAddressList = b06UserAddressService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B06UserAddressDO> b06UserAddressSimpleVO = new PageSimpleVO<>();
        b06UserAddressSimpleVO.setTotalNumber(page.getTotalNumber());
        b06UserAddressSimpleVO.setList(b06UserAddressList);
        return ResultVOUtils.success(b06UserAddressSimpleVO);
    }

    @ApiOperation(value = "保存用户地址表；参数——用户地址实体")
    @PostMapping("/saveAddress")
    @AuthRuleAnnotation()
    ResultVo saveAddress(@RequestBody B06UserAddressSaveForm dto ) {
        B06UserAddressDO b06UserAddress=new B06UserAddressDO();
        BeanUtils.copyProperties(dto,b06UserAddress);
        b06UserAddress.setCreateTime(LocalDateTime.now());
        b06UserAddress.setUpdateTime(LocalDateTime.now());
        b06UserAddress.setUpdatePerson(dto.getCreatePerson());
        b06UserAddress.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b06UserAddressService.insert(b06UserAddress);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改用户地址;参数——修改实体")
    @PostMapping("/updateAddress")
    @AuthRuleAnnotation()
    ResultVo updateAddress(@RequestBody B06UserAddressUpdateForm form ) {

        B06UserAddressDO b06UserAddressDO = b06UserAddressService.get(form.getId());
        if(b06UserAddressDO==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"地址信息不存在");
        }
        if(!StringUtils.isBlank(form.getAddress())){
            b06UserAddressDO.setAddress(form.getAddress());
        }
        if(!StringUtils.isBlank(form.getArea())){
            b06UserAddressDO.setArea(form.getArea());
        }
        if(!StringUtils.isBlank(form.getAreaCode())){
            b06UserAddressDO.setAreaCode(form.getAreaCode());
        }
        if(!StringUtils.isBlank(form.getCityCode())){
            b06UserAddressDO.setCityCode(form.getCityCode());
        }
        if(!StringUtils.isBlank(form.getCity())){
            b06UserAddressDO.setCity(form.getCity());
        }
        if(!StringUtils.isBlank(form.getProvince())){
            b06UserAddressDO.setProvince(form.getProvince());
        }
        if(!StringUtils.isBlank(form.getProvinceCode())){
            b06UserAddressDO.setProvinceCode(form.getProvinceCode());
        }
        if(!StringUtils.isBlank(form.getPhone())){
            b06UserAddressDO.setPhone(form.getPhone());
        }
        if(!StringUtils.isBlank(form.getReceiver())){
            b06UserAddressDO.setReceiver(form.getReceiver());
        }
        if( form.getIsDefault()!=0){
            b06UserAddressDO.setIsDefault(form.getIsDefault());
        }
        b06UserAddressDO.setUpdateTime(LocalDateTime.now());
        int num= b06UserAddressService.update(b06UserAddressDO);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "删除用户地址；参数——地址ID")
    @GetMapping("/deleteAddressById")
    @AuthRuleAnnotation()
    ResultVo deleteAddressById(Long id) {
        System.out.println(id);
        List<Long> list=new ArrayList<>();
        list.add(id);
        int num= b06UserAddressService.stop(list);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "获取收藏商家列表;参数——用户ID；分页参数")
    @GetMapping("/getCollectionShopList")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getCollectionShopList(@Valid B05CollectionSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        List<F01ShopDO> collectionShop = b05CollectionService.getCollectionShop(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionShop);
        return ResultVOUtils.success(b05CollectionSimpleVO);
    }

    @ApiOperation(value = "获取收藏动态列表;参数——用户ID；分页参数")
    @GetMapping("/getCollectionPublicityList")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getCollectionPublicityList(@Valid B05CollectionSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        List<C10PublicityDO> collectionPublicity = b05CollectionService.getCollectionPublicity(map);
        //封装到分页
        PageSimpleVO<C10PublicityDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionPublicity);

        return ResultVOUtils.success(b05CollectionSimpleVO);
    }



    @ApiOperation(value = "获取收藏商品列表;参数——用户ID；分页参数")
    @GetMapping("/getCollectionGoodsList")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B05CollectionDO>> getCollectionGoodsList(@Valid B05CollectionSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.goods.getCode());
        List<C01GoodsDO> collectionGoods = b05CollectionService.getCollectionGoods(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> b05CollectionSimpleVO = new PageSimpleVO<>();
        b05CollectionSimpleVO.setTotalNumber(page.getTotalNumber());
        b05CollectionSimpleVO.setList(collectionGoods);

        return ResultVOUtils.success(b05CollectionSimpleVO);
    }


    @ApiOperation(value = "获取用户列表;参数——用户ID；分页参数")
    @GetMapping("/getUserAccountByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B02UserAccountDO>> getUserAccountByUserId(@Valid B02UserAccountSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B02UserAccountDO> b02UserAccountList = b02UserAccountService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B02UserAccountDO> b02UserAccountSimpleVO = new PageSimpleVO<>();
        b02UserAccountSimpleVO.setTotalNumber(page.getTotalNumber());
        b02UserAccountSimpleVO.setList(b02UserAccountList);

        return ResultVOUtils.success(b02UserAccountSimpleVO);
    }
    
    @ApiOperation(value = "获取流水;参数——ID；分页参数")
    @GetMapping("/getAccountFlowByAccountId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B02UserAccountDO>> getAccountFlowByAccountId(@Valid B03UserAccountFlowSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B03UserAccountFlowDO> b03UserAccountFlowDOS = b03UserAccountFlowAppService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B03UserAccountFlowDO> b02UserAccountSimpleVO = new PageSimpleVO<>();
        b02UserAccountSimpleVO.setTotalNumber(page.getTotalNumber());
        b02UserAccountSimpleVO.setList(b03UserAccountFlowDOS);

        return ResultVOUtils.success(b02UserAccountSimpleVO);
    }

    @ApiOperation(value = "获取用户须知")
    @GetMapping("/getUserKnow")
    ResultVo getUserKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<A14UserKnowDO> list = a14UserKnowService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到用户须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }

    @ApiOperation(value = "获取联系我们")
    @GetMapping("/getContactUs")
    ResultVo getContactUs( ) {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<A08ContactUsDO> list = a08ContactUsService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到联系我们信息");
        }
        return ResultVOUtils.success(list.get(0));
    }


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
    @ApiOperation(value = "获取入住须知")
    @GetMapping("/getSettledNeedKnow")
    ResultVo getSettledNeedKnow() {
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<F04ShopExamineSetDO> list = f04ShopExamineSetService.list(map);
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到入驻须知信息");
        }
        return ResultVOUtils.success(list.get(0));
    }

    @Autowired
    private B01UserDao b01UserDao;
    @Autowired
    private B02UserAccountDao b02UserAccountDao;

    /**
     * @api {POST} /app/my/appCreatWithdrawalApplication 001用户发起提现申请接口
     * @apiGroup 006提现模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP-用户发起提现申请接口
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-Type 固定值APP [必填]
     * @apiHeader {String} X-UserId 用户id [必填]
     * @apiParam {int} id 用户id [必填]
     * @apiParam {double} withdrawalMoney 提现金额 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "用户发起椰云提现申请", notes = "用户发起椰云提现申请")
    @PostMapping(value = "/appCreatWithdrawalApplication")
    @AuthRuleAnnotation()
    @Transactional
    public ResultVo creatWithdrawalApplication(String id,BigDecimal withdrawalMoney){
        Long ids =0L;
        if(null !=id){
             ids = Long.valueOf(id);
        }else{
            return ResultVOUtils.error(ResultEnum.PARAM_NULL);
        }
        if(ids == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        //查询用户信息
       // F08ShopUserDO f08ShopUserDO= f08ShopAppService.getf08ShopUserDO(queryMap);
        B01UserDO b01UserDO = b01UserDao.get(ids);
        //查询用户账户信息
        List<B02UserAccountDO> userAccount = b02UserAccountDao.selectList(new EntityWrapper<B02UserAccountDO>().eq("user_id", id));
        //判断有没有用户
        if(b01UserDO==null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.DATA_NOT_EXIST.getMessage());
        }
        //判断用户是否被删除,假如删除直接提示用户
        if(b01UserDO.getDeleteFlag()==StatusConstant.disable.getCode()){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.ONFREEZE_FAIL.getMessage());
        }

        if(withdrawalMoney.compareTo(userAccount.get(0).getWithdrawalBalance())>0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"提现金额不能大于可提现金额");
        }

        //判断用户退款次数，大于等于三次冻结红包可提现金额三个月
       // B02UserAccountDO userAccountDO = checkReturnOrderNumber(id, userAccount.get(0));

        if(userAccount.get(0).getStatus() ==2){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款次数超过2次，可提现金额已被冻结三个月");
        }

        //计算实际提现到账金额后剩余金额
        BigDecimal bigDecimal =  DecimalUtil.subtractionBigMal(String.valueOf(userAccount.get(0).getWithdrawalBalance()),String.valueOf(withdrawalMoney),0,0);
        int i = bigDecimal.compareTo(BigDecimal.ZERO);

        if(i == -1){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"提现金额不能大于可提现金额");
        }
        //检查是否已实名认证和绑定银行卡
        List<B07AuthenticationDO> list= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", id));
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(10000,"请先完成实名认证");
        }
        B07AuthenticationDO b07AuthenticationDO1 = list.get(0);
        F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();
        entity.setAccountId(userAccount.get(0).getId());
        entity.setAccount(b07AuthenticationDO1.getBankCardNum());
        entity.setActualWithdrawalMoney(withdrawalMoney);//实际提现金额
        entity.setWithdrawalMoney(withdrawalMoney);
        //提现金额小于1000元自动审核通过
        if(withdrawalMoney.compareTo(new BigDecimal("100"))<1){
            entity.setExamineStatus(StatusConstant.ExamineStatus.adopt.getCode());

        }else{
            entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        }
        entity.setApplyTime(LocalDateTime.now());
        entity.setType(2);

        Boolean aBoolean = b02UserAccountService.saveWithdrawalApplication(entity, bigDecimal, userAccount.get(0));
        if(aBoolean){
            //查询用户是否为第一次提现，如果是第一次提现则把用户信息发送椰云众包公司进行签约
            List<F06WithdrawalApplicationDO> f06WithdrawalApplicationDOS = b02UserAccountService.selectUserWithdrawalNumber(userAccount.get(0).getId());
            if(f06WithdrawalApplicationDOS.size() == 1){
                B07AuthenticationDO b07AuthenticationDO = list.get(0);
                JSONObject jsonObject = signacontractAbility.signContractNew(b07AuthenticationDO);
//                if(jsonObject.getInt("code")!=0){
//                   return ResultVOUtils.error(jsonObject.getInt("code"),jsonObject.getString("msg"));
//                }
                logger.info(jsonObject.getInt("code")+"==="+jsonObject.getString("msg"));
            }
            //增加用户账号流水
            userAccountFlowAbility.addUserAccountFlow(userAccount.get(0),withdrawalMoney,TypeConstant.UserAccountType.buy_pay.getCode());
        }
        if(entity.getExamineStatus()==StatusConstant.ExamineStatus.adopt.getCode()){
            ResultVo resultVo = signacontractAbility.yeYunPassRefuse(entity, b07AuthenticationDO1.getName(), b07AuthenticationDO1.getBankCardNum(), b07AuthenticationDO1.getCardNo(), b07AuthenticationDO1.getCreatePerson());
        }
        return ResultVOUtils.success();
    }

    //查询用户自然一月内退款次数是否大于等于3次
    private  B02UserAccountDO checkReturnOrderNumber(String userId,B02UserAccountDO userAccount){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<D03CustomerVo> customerGroupByTime = d03CustomerServiceService.getCustomerGroupByTime(map);
        if(!customerGroupByTime.isEmpty()){
            if(customerGroupByTime.size()>3){
                for (int i=0;i<3;i++) {
                    if(customerGroupByTime.get(i).getNumber()>=3){
                        userAccount.setStatus(2);
                        b02UserAccountDao.update(userAccount,new EntityWrapper<B02UserAccountDO>().eq("user_id",userId));
                    }
                }
            }else{
                for (int i=0;i<customerGroupByTime.size();i++) {
                    if(customerGroupByTime.get(i).getNumber()>=3){
                        userAccount.setStatus(2);
                        userAccount.setUpdateTime(LocalDateTime.now());
                        b02UserAccountDao.update(userAccount,new EntityWrapper<B02UserAccountDO>().eq("user_id",userId));
                    }
                }
            }


        }
        return userAccount;
    }

    /**
     * @api {POST} /app/my/createInvitationWithdrawalApplication 003用户发起推广收益提现申请
     * @apiGroup 006提现模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP-用户发起推广收益提现申请
     * @apiHeader {String} X-Token 用户token [必填]
     * @apiHeader {String} X-Type 固定值APP [必填]
     * @apiHeader {String} X-UserId 用户id [必填]
     * @apiParam {int} id 用户id [必填]
     * @apiParam {double} withdrawalMoney 提现金额 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {}
     * }
     */
    @ApiOperation(value = "用户发起推广收益提现申请", notes = "用户发起推广收益提现申请")
    @PostMapping(value = "/createInvitationWithdrawalApplication")
    @AuthRuleAnnotation()
    public ResultVo createInvitationWithdrawalApplication(String id,String withdrawalMoney){
        Long ids = 0L;
        if(null !=id){
            ids = Long.valueOf(id);
        }
        if(ids == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        //查询用户信息
        B01UserDO b01UserDO = b01UserDao.get(ids);
        //查询用户推广收益账户
        List<B12PromotionAccountDO> promotionAccountDOList = b12PromotionAccountDao.selectList(new EntityWrapper<B12PromotionAccountDO>().eq("user_id", id));
        if(promotionAccountDOList.size() == 0){
                return ResultVOUtils.error(ResultEnum.ACCOUNT_NOT_EXISTENT,ResultEnum.ACCOUNT_NOT_EXISTENT.getMessage());
        }
        B12PromotionAccountDO b12PromotionAccountDO = promotionAccountDOList.get(0);
        BigDecimal bigDecimal = DecimalUtil.subTionBigMal(b12PromotionAccountDO.getAccountBalance(), new BigDecimal(withdrawalMoney), 2, null);
        int i = bigDecimal.compareTo(BigDecimal.ZERO);
        if(i==-1){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"提现金额不能大于可提现金额");
        }

        //检查是否已实名认证和绑定银行卡
        List<B07AuthenticationDO> list= b07AuthenticationDao.selectList(new EntityWrapper<B07AuthenticationDO>().eq("user_id", id));
        if(list==null||list.isEmpty()){
            return ResultVOUtils.error(10000,"请先完成实名认证");
        }

        F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();

        entity.setAccountId(b12PromotionAccountDO.getId());
        entity.setActualWithdrawalMoney(new BigDecimal(withdrawalMoney));//实际提现金额
        entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        entity.setApplyTime(LocalDateTime.now());
        entity.setType(3);
        Boolean aBoolean = b02UserAccountService.saveInvitationWithdrawalApplication(entity, bigDecimal, new BigDecimal(withdrawalMoney), b12PromotionAccountDO);
        if(aBoolean){
            //查询用户是否为第一次提现，如果是第一次提现则把用户信息发送椰云众包公司进行签约
            List<F06WithdrawalApplicationDO> f06WithdrawalApplicationDOS = b02UserAccountService.selectUserWithdrawalNumber(b12PromotionAccountDO.getId());
            if(f06WithdrawalApplicationDOS.size() == 1){
                B07AuthenticationDO b07AuthenticationDO = list.get(0);
                JSONObject jsonObject = signacontractAbility.signContractNew(b07AuthenticationDO);
                if(jsonObject.getInt("code")!=0){
                    return ResultVOUtils.error(jsonObject.getInt("code"),jsonObject.getString("msg"));
                }
            }
        }
        return ResultVOUtils.success();
    }

    @GetMapping(value = "/getB12")
    public ResultVo getB12(String id){
        if(id == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        B12PromotionAccountDO b12PromotionAccountDO = new B12PromotionAccountDO();
        b12PromotionAccountDO.setUserId(Long.valueOf(id));
        B12PromotionAccountDO b12PromotionAccountDO1 = b12PromotionAccountDao.selectOne(b12PromotionAccountDO);
        return ResultVOUtils.success(b12PromotionAccountDO1);
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("-0.12");
        System.out.println(bigDecimal.compareTo(BigDecimal.ZERO));
    }
}
