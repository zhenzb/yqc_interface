package com.youqiancheng.controller.manager.usermanagment;

import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthAdmin.A01AdminQueryForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.user.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.manager.service.datastatistics.DataStatisticsService;
import com.youqiancheng.service.manager.service.usermanagement.UserManagementService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.manager.*;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 会员管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/user")
@Api(tags = "总管理后台-会员管理")
public class UserManagementController {

    @Resource
    private UserManagementService userManagementService;

    /**
    　* @Description: 获取用户列表
    　* @author shalongteng
    　* @date 2020/4/8 9:11
    　*/
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("listUserHDPage")
    public ResultVo<PageSimpleVO<B01UserDO>> getUserList(@Valid UserQueryForm userQueryForm,
                                                        @Valid EntyPage page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userQueryForm", userQueryForm);
        map.put("page", page);
        List<B01UserDO> authAdminList = userManagementService.listUserHDPage(map);

        //封装到分页
        PageSimpleVO<B01UserDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }
    /**
    　* @Description: 用户审核列表
    　* @author shalongteng
    　* @date 2020/4/14 15:12
    　*/
    @ApiOperation(value = "用户审核列表", notes = "用户审核列表")
    @GetMapping("listUserAuthHDPage")
    public ResultVo<PageSimpleVO<B07AuthenticationDO>> listUserAuthHDPage(@Valid UserAuthForm userAuthForm   ,
                                                                          @Valid EntyPage page) {


        Map<String, Object> map = new HashMap<>();
        map.put("userAuthForm", userAuthForm);
        map.put("page", page);
        List<B07AuthenticationDO> authAdminList = userManagementService.listUserAuthHDPage(map);

        //封装到分页
        PageSimpleVO<B07AuthenticationDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
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
            Integer integer = userManagementService.batchPassRefuse(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }

    /**
    　* @Description: 查看余额
    　* @author shalongteng
    　* @date 2020/4/8 11:16
    　*/
    @ApiOperation(value = "查看余额", notes = "查看余额")
    @GetMapping("checkBalance")
    public ResultVo<PageSimpleVO<B02UserAccountDO>> checkBalance(@Valid BalanceQueryForm balanceQueryForm   ,
                                                                 @Valid EntyPage page) {



        Map<String, Object> map = new HashMap<>();
        map.put("balanceQueryForm", balanceQueryForm);
        map.put("page", page);
        List<B02UserAccountDO> authAdminList = userManagementService.checkBalance(map);

        //封装到分页
        PageSimpleVO<B02UserAccountDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }
    /**
    　* @Description:余额明细
    　* @author shalongteng
    　* @date 2020/4/8 13:47
    　*/
    @ApiOperation(value = "余额明细", notes = "余额明细")
    @GetMapping("checkBalanceDetail")
    public ResultVo<PageSimpleVO<B03UserAccountFlowDO>> checkBalanceDetail(@Valid BalanceDetailQueryForm balanceDetailQueryForm   ,
                                                                           @Valid EntyPage page) {


        Map<String, Object> map = new HashMap<>();
        map.put("balanceDetailQueryForm", balanceDetailQueryForm);
        map.put("page", page);
        List<B03UserAccountFlowDO> authAdminList = userManagementService.checkBalanceDetail(map);

        //封装到分页
        PageSimpleVO<B03UserAccountFlowDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }


    /**
    　* @Description: 冻结用户
    　* @author shalongteng
    　* @date 2020/4/8 10:27
    　*/
    @ApiOperation(value = "冻结用户", notes = "冻结用户")
    @GetMapping("freeze")
    public ResultVo<PageSimpleVO<A01Admin>> freeze(@Valid UserFreezeForm userFreezeForm   ) {


        boolean flag = userManagementService.freeze(userFreezeForm);
        if(!flag){
            return ResultVOUtils.error(ResultEnum.FREEZE_FAIL);
        }
        return ResultVOUtils.success();
    }


}
