package com.youqiancheng.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.*;
import com.youqiancheng.mybatis.dao.B04ShoppingCartDao;
import com.youqiancheng.mybatis.model.B04ShoppingCartDO;
import com.youqiancheng.mybatis.model.B06UserAddressDO;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.service.app.service.B04ShoppingCartAppService;
import com.youqiancheng.service.app.service.B06UserAddressAppService;
import com.youqiancheng.service.app.service.D01OrderAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.ShoppingCartVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Api(tags = {"手机端-购物车接口"})
@RestController
@RequestMapping(value = "app/b04ShoppingCart")
public class ShoppingCartAppController {
    @Autowired
    private B04ShoppingCartAppService b04ShoppingCartService;
    @Autowired
    private B06UserAddressAppService b06UserAddressService;
    @Autowired
    private D01OrderAppService d01OrderService;

    @Autowired
    private B04ShoppingCartDao b04ShoppingCartDao;
    @ApiOperation(value = "获取购物车列表；参数——用户ID,一级分类ID")
    @GetMapping("/list")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B04ShoppingCartDO>> listByPage(@Valid B04ShoppingCartSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<ShoppingCartVO> shoppingCartVOS = b04ShoppingCartService.listHDPage(map);
        //封装到分页
        PageSimpleVO<ShoppingCartVO> b04ShoppingCartSimpleVO = new PageSimpleVO<>();
            b04ShoppingCartSimpleVO.setTotalNumber(page.getTotalNumber());
            b04ShoppingCartSimpleVO.setList(shoppingCartVOS);

        return ResultVOUtils.success(b04ShoppingCartSimpleVO);
    }


    @ApiOperation(value = "修改购物车记录;参数——记录主键ID,数量，单价，总价")
    @PostMapping("/update")
    @AuthRuleAnnotation()
    ResultVo update(@RequestBody B04ShoppingCartUpdateDO form) {
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


    @ApiOperation(value = "删除购物车记录:参数——购物车记录ID")
    @GetMapping("/delete")
    @AuthRuleAnnotation()
    ResultVo delete(Long id) {
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"参数不能为空");
        }
        System.out.print("-------------------------------------------"+id);
        List<Long> idList=new ArrayList<>();
        idList.add(id);
        int num= b04ShoppingCartService.delete(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"购物车删除失败");
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "根据用户ID获取用户默认地址——若无默认则获取最新添加地址,如果为空则表示该用户没有地址")
    @GetMapping("/getAddressByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getAddressByUserId(@Valid B06UserAddressSearchForm form,  @Valid EntyPage page  ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<B06UserAddressDO> b06UserAddressList = b06UserAddressService.listHDPage(map);
        if(b06UserAddressList==null||b06UserAddressList.isEmpty()){
            return ResultVOUtils.success();
        }
        //封装到分页
        return ResultVOUtils.success(b06UserAddressList.get(0));
    }

    @ApiOperation(value = "根据用户ID获取用户地址;参数 用户ID,分页参数（不传则查询所有）")
    @GetMapping("/getAddressListByUserId")
    @AuthRuleAnnotation()
    ResultVo<PageSimpleVO<B06UserAddressDO>> getAddressListByUserId(@Valid B06UserAddressSearchForm form , @Valid EntyPage page ) {

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
    ResultVo saveAddress(@RequestBody B06UserAddressSaveForm dto) {
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
    ResultVo updateAddress(@RequestBody B06UserAddressUpdateForm form) {
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

    @ApiOperation(value = "保存业务订单表；参数——订单信息(包含订单明细)")
    @PostMapping("/saveOrder")
    @AuthRuleAnnotation()
    ResultVo saveOrder(@RequestBody D06PayOrderSearchForm form) {
        D06PayOrderDO save = d01OrderService.save(form);
        return ResultVOUtils.success(save);
    }
    @ApiOperation(value = "支付订单")
    @PostMapping("/payOrder")
    @AuthRuleAnnotation()
    ResultVo payOrder(@RequestBody PayOrderPayForm form) {
        d01OrderService.payOrder(form);
        return ResultVOUtils.success();
    }

    @ApiOperation(value = "添加购物车里的商品；参数——添加购物车的实体")
    @PostMapping("/addCartGoods")
    @AuthRuleAnnotation()
    ResultVo addCartGoods(@RequestBody B04ShoppingCartAddAndSubSaveForm form) {
        int i = b04ShoppingCartService.addCartGoods(form);
        return ResultVOUtils.success(i);
    }

    @ApiOperation(value = "统计购物车里数量；参数——用户的userId")
    @PostMapping("/cartGoodsNum")
    @AuthRuleAnnotation()
    ResultVo cartGoodsNum(Long userId) {
        if (userId == null) {
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        //查询购物车里的条数
//       Integer integer= b04ShoppingCartDao.selectCount(
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
