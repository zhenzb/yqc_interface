package com.youqiancheng.controller.manager.shopmanagement;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.DictTypeConstants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.shop.F01ShopUpdateManageDO;
import com.youqiancheng.form.manager.shop.ShopDeleteForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.shop.ShopQueryForm;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.shop.system.F08ShopUserDao;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.service.app.service.B01UserAppService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.service.manager.service.InitDataService;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.util.EntCoordSyncJob;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
　* @Description: 商家管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_shop")
@Api(tags = "总管理后台-商家管理")
public class ShopManagementController {
    private static final Log log = LogFactory.getLog(ShopManagementController.class);
    @Autowired
    private F01ShopClientService f01ShopService;
    @Resource
    private ShopManagementService shopManagementService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private B01UserAppService b01UserService;
    @Resource
    private F08ShopUserDao shopUserDao;

    @Resource
    private C01GoodsDao c01GoodsDao;
    /**
    　* @Description: 获取商家列表
    　* @author shalongteng
    　* @date 2020/4/8 9:11
    　*/
    @ApiOperation(value = "获取商家列表", notes = "获取商家列表")
    @GetMapping("listShop")
    public ResultVo<PageSimpleVO<F01ShopDO>> listShop(@Valid ShopQueryForm shopQueryForm   ,
                                                         @Valid EntyPage page) {


        Map<String, Object> map = new HashMap<>();
        map.put("shopQueryForm", shopQueryForm);
        map.put("page", page);
        List<F01ShopDO> authAdminList = shopManagementService.listShopHDPage(map);
        for (F01ShopDO f01ShopDO : authAdminList) {
            f01ShopDO.setAddress(f01ShopDO.getProvince()+f01ShopDO.getCity()+f01ShopDO.getArea()+f01ShopDO.getAddress());
        }
        //封装到分页
        PageSimpleVO<F01ShopDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }
    /**
    　* @Description: 批量删除商家
    　* @author shalongteng
    　* @date 2020/4/13 14:24
    　*/
    @ApiOperation(value = "批量删除商家", notes = "批量删除商家")
    @PostMapping("deleteShop")
    public ResultVo deleteShop(@RequestBody List<ShopDeleteForm>  shopDeleteFormList) {
        if(shopDeleteFormList == null || shopDeleteFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            for(ShopDeleteForm shopDeleteForm:shopDeleteFormList){
                F01ShopDO info = shopManagementService.getInfo(shopDeleteForm.getId());
                B01UserDO b01UserDO = b01UserService.get(info.getUserId());
                b01UserDO.setShopId(0);
                b01UserDO.setIsShop(1);
                b01UserService.update(b01UserDO);
                Map<String,Object> map = new HashMap<>();
                map.put("shopId",shopDeleteForm.getId());
                shopUserDao.deleteUserShop(map);
                //删除该商户下的所有商品
                Map<String,Object> map1 = new HashMap<>();
                map1.put("shop_id",shopDeleteForm.getId());
                c01GoodsDao.deleteByMap(map1);
            }
            Integer integer = shopManagementService.deleteShop(shopDeleteFormList);
            if(integer >= 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
    }
    /**
    　* @Description: 批量通过拒绝
    　* @author shalongteng
    　* @date 2020/4/13 14:41
    　*/
    @ApiOperation(value = "批量通过拒绝", notes = "批量通过拒绝")
    @PostMapping("batchPassRefuse")
    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = shopManagementService.batchPassRefuse(shopPassRefuseFormList);
            //
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }
    /**
    　* @Description: 查看
    　* @author shalongteng
    　* @date 2020/4/13 14:41
    　*/
    @ApiOperation(value = "查看：参数：商家ID")
    @PostMapping("getShopInfo")
    public ResultVo getShopInfo(@ApiParam(name = "id", value = "商家id", required = true) @RequestParam("id")  Long id) {
        F01ShopDO info = shopManagementService.getInfo(id);
        return ResultVOUtils.success(info);
    }


    /**
     　* @Description: 保存商家
     　* @author shalongteng
     　* @date 2020/4/13 14:41
     　*/
    @ApiOperation(value = "保存商家")
    @PostMapping("saveShopInfo")
    public ResultVo saveShopInfo(@RequestBody F01ShopUpdateManageDO dto) {
        int update = shopManagementService.update(dto);
        return ResultVOUtils.success(update);
    }
    @ApiOperation(value = "返回商家类型")
    @GetMapping("/getShopType")
    ResultVo getShopType() {
        List<A16SysDictDO> sysDictByType = initDataService.getSysDictByType(DictTypeConstants.SHOP_TYPE);
        return ResultVOUtils.success(sysDictByType);
    }

    @ApiOperation(value = "根据商家类型返回商家主营项目")
    @GetMapping("/getMainProjectByType")
    ResultVo getShopProjectByType(String type ){
        if(StringUtils.isBlank(type)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商家类型不能为空");
        }
        List<F03MainProjectDO> list = f01ShopService.getShopProjectByType(type);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据地理位置获取经纬度")
    @PostMapping("/getCoordinate")
    @AuthRuleAnnotation()
    ResultVo getCoordinate(String address) {
        if(StringUtils.isBlank(address)){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"地址不能为空");
        }
        String coordinate = EntCoordSyncJob.getCoordinate(address);
        return ResultVOUtils.success(coordinate);
    }
}
