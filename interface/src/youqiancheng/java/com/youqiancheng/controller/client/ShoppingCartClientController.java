package com.youqiancheng.controller.client;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.AlipayAbility;
import com.youqiancheng.ability.UserAccountFlowAbility;
import com.youqiancheng.form.client.*;
import com.youqiancheng.mybatis.dao.B01UserDao;
import com.youqiancheng.mybatis.dao.B04ShoppingCartDao;
import com.youqiancheng.mybatis.dao.F17PromotionIncomeDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.B02UserAccountClientService;
import com.youqiancheng.service.client.service.B04ShoppingCartClientService;
import com.youqiancheng.service.client.service.B06UserAddressClientService;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.client.ShoppingCartVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Api(tags = {"PC端-购物车接口"})
@RestController
@RequestMapping(value = "pc/shoppingCart")
public class ShoppingCartClientController {
    @Autowired
    private B04ShoppingCartClientService b04ShoppingCartService;
    @Autowired
    private B06UserAddressClientService b06UserAddressService;

    @Autowired
    private D01OrderClientService d01OrderService;

    @Autowired
    private B04ShoppingCartDao b04ShoppingCartDao;
    @Autowired
    private AlipayAbility alipayAbility;

    @Autowired
    private UserAccountFlowAbility userAccountFlowAbility;
    @Autowired
    private B02UserAccountClientService b02UserAccountClientService;
    @Resource
    private B01UserDao b01UserDao;
    @Resource
    private F17PromotionIncomeDao f17PromotionIncomeDao;

    @ApiOperation(value = "获取购物车列表；参数——用户ID,分类ID")
    @GetMapping("/list")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B04ShoppingCartDO>> listByPage(@Valid B04ShoppingCartSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<ShoppingCartVO> shoppingCartVOS = b04ShoppingCartService.listHDPage(map);
        //封装到分页
        PageSimpleVO<ShoppingCartVO> b04ShoppingCartSimpleVO = new PageSimpleVO<>();
            b04ShoppingCartSimpleVO.setTotalNumber(page.getTotalNumber());
            b04ShoppingCartSimpleVO.setList(shoppingCartVOS);

        return ResultVOUtils.success(b04ShoppingCartSimpleVO);
    }


    @ApiOperation(value = "修改购物车记录;参数——购物车修改实体：购物车记录ID,数量，单价，总价")
    @PutMapping("/update")
   // @AuthRuleAnnotation()
    ResultVo update(@RequestBody @Valid B04ShoppingCartUpdateDO form ) {

        B04ShoppingCartDO b04ShoppingCartDO = b04ShoppingCartService.get(form.getId());
        b04ShoppingCartDO.setCommodityNumber(form.getCommodityNumber());
        b04ShoppingCartDO.setPrice(form.getPrice());
        b04ShoppingCartDO.setTotalPrice(form.getTotalPrice());
        b04ShoppingCartDO.setUpdateTime(LocalDateTime.now());
        int num= b04ShoppingCartService.update(b04ShoppingCartDO);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "删除购物车记录；参数——购物车记录ID")
    @GetMapping("/delete")
    //@AuthRuleAnnotation()
    ResultVo delete( Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"购物车ID不能为空");
        }
        List<Long> idList=new ArrayList<>();
        idList.add(id);
        int num= b04ShoppingCartService.delete(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"购物车删除失败");
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "保存业务订单表；参数——订单信息(包含订单明细)")
    @PostMapping("/saveOrder")
   // @AuthRuleAnnotation()
    ResultVo saveOrder(@RequestBody @Valid D06PayOrderSearchForm form ) {

        D06PayOrderDO save = d01OrderService.save(form);
        return ResultVOUtils.success(save);
    }

    //线下消费，线上支付
    /**
     * @api {GET} /pc/shoppingCart/saveFacePay 002线下消费，线上支付接口
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 线下消费，线上支付接口
     * @apiParam {double} orderPrice 支付金额
     * @apiParam {long} shopId 店铺Id
     * @apiParam {String} shopName 店铺名称
     * @apiParam {Long} userId 用户Id
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0,           请求成功
     *   "message": "success",
     *   "data": "唤醒支付宝地址"
     * }
     */
    @PostMapping("/saveFacePay")
    @AuthRuleAnnotation()
    ResultVo saveFacePay(@RequestBody @Valid D06PayOrderSearchForm form ) {

        D06PayOrderDO save = d01OrderService.saveFacePay(form);
        try {
            ResultVo resultVo = alipayAbility.buildAlipayUrl(save.getOrderNo(), save.getOrderPrice());
            return resultVo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtils.error(99,"支付失败");
    }


    /**
     * @api {GET} /pc/shoppingCart/saveRewardFlow 003保存打赏宣传店铺店主流量值
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 保存打赏宣传店铺店主流量值
     * @apiParam {double} orderPrice 打赏金额
     * @apiParam {long} shopId 店铺Id
     * @apiParam {String} shopName 店铺名称
     * @apiParam {Long} userId 用户Id
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0,           请求成功
     *   "message": "success",
     *   "data": ""
     * }
     */
    @PostMapping("/saveRewardFlow")
    @AuthRuleAnnotation()
    @Transactional
    ResultVo saveRewardFlow(@RequestBody @Valid D06PayOrderSearchForm form ){
        //校验入参
        if(form.getUserId() == null ||form.getShopId() == null ||form.getOrderPrice() == null){
            return ResultVOUtils.error(99,"参数错误");
        }
        //校验打赏者的流量值是否满足打赏的流量值
        Map<String,Object> map = new HashMap<>();
        map.put("userId",form.getUserId());
        List<B02UserAccountDO> b02UserAccountDOList = b02UserAccountClientService.list(map);
        if(b02UserAccountDOList ==null || b02UserAccountDOList.size()==0){
            return ResultVOUtils.error(99,"用户不存在");
        }
        B02UserAccountDO b02UserAccountDO = b02UserAccountDOList.get(0);
        BigDecimal resultBalance = new BigDecimal("0");
        if(form.getMoneyType() == 0){
             resultBalance = b02UserAccountDO.getWithdrawalBalance().subtract(form.getOrderPrice());
            if(resultBalance.compareTo(new BigDecimal("0"))<0){
                return ResultVOUtils.error(99,"流量值不足，赶快去赚取更多流量值吧");
            }
            //减去打赏者的流量值=====增加店主的流量值
            b02UserAccountDO.setWithdrawalBalance(resultBalance);
            b02UserAccountClientService.update(b02UserAccountDO);
        }else{
            //查询可视化收益金额
            Map<String,Object> f17Map = new HashMap<String,Object>();
            f17Map.put("userId",form.getUserId());
            List<F17PromotionIncomeDO> list1 = f17PromotionIncomeDao.list(f17Map);
            if(list1.size()!=0){
                F17PromotionIncomeDO f17PromotionIncomeDO = list1.get(0);
                BigDecimal userActualAmount = f17PromotionIncomeDO.getUserActualAmount().subtract(form.getOrderPrice());
                if(userActualAmount.compareTo(new BigDecimal("0"))<0){
                    return ResultVOUtils.error(99,"流量值不足，赶快去赚取更多流量值吧");
                }
                //减去打赏者的流量值=====增加店主的流量值
                f17PromotionIncomeDO.setUserActualAmount(userActualAmount);
                f17PromotionIncomeDao.updateById(f17PromotionIncomeDO);
            }else{
                return ResultVOUtils.error(99,"流量值不足，赶快去赚取更多流量值吧");
            }

        }
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("shopId",form.getShopId());
        List<B01UserDO> list = b01UserDao.list(userMap);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("userId",list.get(0).getId());
        List<B02UserAccountDO> list1 = b02UserAccountClientService.list(map2);
        B02UserAccountDO b02UserAccountDO1 = list1.get(0);
        BigDecimal add = b02UserAccountDO1.getAccountBalance().add(form.getOrderPrice());
        b02UserAccountDO1.setAccountBalance(add);
        b02UserAccountClientService.update(b02UserAccountDO1);
        //增加打赏者消费流水
        userAccountFlowAbility.addUserAccountFlow2(b02UserAccountDO,form.getOrderPrice(),TypeConstant.UserAccountType.reward_flow_sub.getCode());
        //增加店主打赏收入流水
        userAccountFlowAbility.addUserAccountFlow2(b02UserAccountDO1,form.getOrderPrice(),TypeConstant.UserAccountType.reward_flow_add.getCode());
        return ResultVOUtils.success();
        }



//
//    @ApiOperation(value = "保存订单记录；参数——订单保存实体")
//    @PostMapping("/saveOrder")
//    @AuthRuleAnnotation()
//    ResultVo saveOrder(@RequestBody D01OrderSaveForm d01Order) {
//        d01OrderService.save(d01Order);
//        return ResultVOUtils.success();
//    }

    /**************************用户地址 **************************************/

    @ApiOperation(value = "根据用户ID获取用户地址列表；参数——用户ID,分页参数")
    @GetMapping("/getUserAddressListByUserId")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getUserAddressListByUserId(@Valid B06UserAddressSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B06UserAddressDO> b06UserAddressList = b06UserAddressService.listHDPage(map);
        //封装到分页
        PageSimpleVO<B06UserAddressDO> b06UserAddressSimpleVO = new PageSimpleVO<>();
        b06UserAddressSimpleVO.setTotalNumber(page.getTotalNumber());
        b06UserAddressSimpleVO.setList(b06UserAddressList);

        return ResultVOUtils.success(b06UserAddressSimpleVO);
    }

    @ApiOperation(value = "保存用户地址表信息；参数——用户地址保存实体")
    @PostMapping("/saveUserAddress")
    //@AuthRuleAnnotation()
    ResultVo saveUserAddress(@RequestBody @Valid  B06UserAddressSaveForm dto ) {

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


    @ApiOperation(value = "修改用户地址信息；参数——用户地址修改实体")
    @PutMapping("/updateUserAddress")
    //@AuthRuleAnnotation()
    ResultVo updateUserAddress(@RequestBody @Valid B06UserAddressUpdateForm form ) {

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


    @ApiOperation(value = "删除用户地址信息：参数——地址ID")
    @GetMapping("/deleteUserAddress")
    ResultVo deleteUserAddress(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"地址ID不能为空");
        }
        List<Long> list=new ArrayList<>();
        list.add(id);
        int num= b06UserAddressService.stop(list);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "根据用户ID获取用户默认地址——若无默认则获取最新添加地址,如果为空则表示该用户没有地址")
    @GetMapping("/getDefaultUserAddressByUserId")
    //@AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getDefaultUserAddressByUserId(@Valid B06UserAddressSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B06UserAddressDO> b06UserAddressList = b06UserAddressService.listHDPage(map);
        if(b06UserAddressList==null||b06UserAddressList.isEmpty()){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.success(b06UserAddressList.get(0));
    }

    @ApiOperation(value = "统计购物车里数量；参数——用户的userId")
    @PostMapping("/cartGoodsNum")
    @AuthRuleAnnotation()
    ResultVo cartGoodsNum(Long userId) {
        if (userId == null) {
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        //查询购物车里的条数
//        Integer integer= b04ShoppingCartDao.selectCount(
//                new EntityWrapper<B04ShoppingCartDO>()
//                        .eq("user_id",userId)
//                        .eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode())
//        );
        int goodsNumber = 0;
        List<B04ShoppingCartDO> b04ShoppingCartDOS = b04ShoppingCartDao.selectList(
                new EntityWrapper<B04ShoppingCartDO>()
                        .eq("user_id", userId)
                        .eq("delete_flag", StatusConstant.DeleteFlag.un_delete.getCode()));
        for (B04ShoppingCartDO b04:b04ShoppingCartDOS) {
            goodsNumber += b04.getCommodityNumber();
        }
        return  ResultVOUtils.success(goodsNumber);
    }


}
