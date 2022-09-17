package com.youqiancheng.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.util.Constants;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.F01ShopUpdateDO;
import com.youqiancheng.form.shop.ResetPasswordForm;
import com.youqiancheng.mybatis.dao.F05ShopAccountDao;
import com.youqiancheng.mybatis.dao.F06WithdrawalApplicationDao;
import com.youqiancheng.mybatis.dao.F18LinksDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.client.service.F18LinksAppService;
import com.youqiancheng.service.shop.ShopStoreService;
import com.youqiancheng.service.shop.system.ShopUserService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:50
 * @Version: V1.0
 */
@Api(tags = "商家管理--商家账户--店铺管理")
@RestController
@RequestMapping("shop/storeManagement")
public class ShopStoreManagementController {
    @Resource
    private ShopStoreService shopStoreService;
    @Resource
    private ShopUserService shopUserService;
    @Autowired
    private B01UserAppService b01UserService;
    @Autowired
    private F06WithdrawalApplicationDao f06WithdrawalApplicationDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Autowired
    private F18LinksAppService f18LinksService;

    @ApiOperation(value = "返回商家信息")
    @GetMapping(value = "/getShopInfo")
    public ResultVo  getShopInfo(){

        Map<String, Object> resultMap = new HashMap<>();
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        F01ShopDO f01ShopDO = shopStoreService.get(shopUser.getShopId());
        Map<String, Object> map1 = new HashMap<>();
        map1.put("shop_id",shopUser.getShopId());
        List<B01UserDO> list = b01UserService.list(map1);
        if(f01ShopDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "商家数据为空");
        }
        if(list.size()>0){
            resultMap.put("weChatOpenId",list.get(0).getWechatOpenid());
        }else{
            resultMap.put("weChatOpenId","");
        }
        //查询商家公司名称
        //查询商家账户id
        EntityWrapper<F05ShopAccountDO> f06 = new EntityWrapper<>();
        f06.eq("shop_id",f01ShopDO.getId());
        List<F05ShopAccountDO> f05ShopAccountDOS = f05ShopAccountDao.selectList(f06);
        EntityWrapper<F06WithdrawalApplicationDO> ew = new EntityWrapper<>();
        ew.eq("account_id",f05ShopAccountDOS.get(0).getId());
        ew.orderBy("id",false);
        List<F06WithdrawalApplicationDO> f06WithdrawalApplicationDOS = f06WithdrawalApplicationDao.selectList(ew);
        if(f06WithdrawalApplicationDOS.size()!=0){
            f01ShopDO.setAlipayUrl(f06WithdrawalApplicationDOS.get(0).getAccount());
        }
        //获取店铺内友情链接
        Map<String, Object> map = new HashMap<>();
        map.put("sourceId",shopUser.getShopId());
        map.put("isDelete",1);
        List<F18LinksDO> f18LinksDOS = f18LinksService.list(map);
        f01ShopDO.setLinksList(f18LinksDOS);
        List<String> imgList= new ArrayList<>();
        if(f01ShopDO.getAlipayUrl()!=null && f01ShopDO.getAlipayUrl().length()>0){
            String[] split = f01ShopDO.getAlipayUrl().split(",");
            imgList = Arrays.asList(split);
        }
        f01ShopDO.setImgList(imgList);
        resultMap.put("f01ShopDO",f01ShopDO);
        return ResultVOUtils.success(resultMap);
    }


    @ApiOperation(value = "修改商家信息")
    @PostMapping(value = "/updateShopInfo")
    public ResultVo updateShopInfo(@Valid F01ShopUpdateDO form ){
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        form.setId(shopUser.getShopId());
        F01ShopDO f01ShopDO1 = shopStoreService.get(form.getId());
        if(f01ShopDO1==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "商家数据为空");
        }
        f01ShopDO1.setHide(form.getHide());
        f01ShopDO1.setContacts(form.getContacts());
        f01ShopDO1.setName(form.getName());
        f01ShopDO1.setShopDesc(form.getShopDesc());
        f01ShopDO1.setLogo(form.getLogo());
        f01ShopDO1.setPhone(form.getPhone());
        f01ShopDO1.setUpdateTime(LocalDateTime.now());
        f01ShopDO1.setAlipayUrl(form.getAlipayUrl());
        f01ShopDO1.setWechatUrl(form.getWechatUrl());

        shopStoreService.update(f01ShopDO1);
        return ResultVOUtils.success(f01ShopDO1);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/resetPwd")
    public ResultVo resetPwd(@Valid ResetPasswordForm form ){

        shopUserService.ResetPasswordSetting(form);
        return ResultVOUtils.success();
    }
}


