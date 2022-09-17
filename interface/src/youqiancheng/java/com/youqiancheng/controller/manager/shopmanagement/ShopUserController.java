package com.youqiancheng.controller.manager.shopmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.shop.F08ShopUserSearchForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
　* @Description: 商家账户管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_shop/shopUser")
@Api(tags = "总管理后台-商家管理-账号管理")
public class ShopUserController {
    private static final Log log = LogFactory.getLog(ShopUserController.class);

    @Resource
    private ShopManagementService shopManagementService;


    /**
    　* @Description: 获取商家账户列表
    　* @author shalongteng
    　* @date 2020/4/9 9:06
    　*/
    @ApiOperation(value = "获取商家账号列表", notes = "获取商家账号列表")
    @GetMapping("listShopUser")
    public ResultVo<F08ShopUserDO> listShopAccount(@Valid F08ShopUserSearchForm form, @Valid EntyPage page) {
        QueryMap map=new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<F08ShopUserDO> mainProjectDOList = shopManagementService.listShopAccountHDPage(map);
        //封装到分页
        PageSimpleVO<F08ShopUserDO> vo = new PageSimpleVO<>();
        vo.setTotalNumber(page.getTotalNumber());
        vo.setList(mainProjectDOList);

        return ResultVOUtils.success(vo);
    }

    /**
     　* @Description: 批量通过拒绝
     　* @author shalongteng
     　* @date 2020/4/15 16:41
     　*/
    @ApiOperation(value = "批量启用禁用", notes = "批量启用禁用")
    @PostMapping("batchPassRefuse")
    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = shopManagementService.batchPassRefuseShopUser(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }
//    /**
//    　* @Description: 编辑商家账号
//    　* @author shalongteng
//    　* @date 2020/4/15 13:42
//    　*/
//    @ApiOperation(value = "编辑/冻结商家账号", notes = "编辑/冻结商家账号")
//    @PostMapping("saveOrupdateShopUser")
//    public ResultVo saveOrupdateShopUser(@RequestBody ShopUsertSaveOrUpdateForm shopUsertSaveOrUpdateForm) {
//        if(shopUsertSaveOrUpdateForm.getStatus().equals(1) || shopUsertSaveOrUpdateForm.getStatus().equals(2)){
//            shopManagementService.saveOrupdateShopUser(shopUsertSaveOrUpdateForm);
//        }else {
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
//        }
//
//        return ResultVOUtils.success();
//    }
}
