package com.youqiancheng.controller.shop.system;

import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.system.user.*;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.mybatis.model.F11RoleDO;
import com.youqiancheng.service.shop.system.ShopRoleService;
import com.youqiancheng.service.shop.system.ShopUserService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/13 9:30
 * @Version: V1.0
 */
@RestController
@RequestMapping("shop/system/user")
@Api(tags = "商家管理--系统管理--管理员用户")
public class ShopSystemUserController {
    @Resource
    private ShopUserService shopUserService;
    @Resource
    private ShopRoleService shopRoleService;

    /**
     　* @Description: 获取所有的角色
     　*/
    @ApiOperation(value = "获取所有的角色", notes = "获取所有的角色")
    @GetMapping("roleList")
    public ResultVo roleList() {
        List<F11RoleDO> roleList = shopRoleService.listShopUserRole();
        return ResultVOUtils.success(roleList);
    }
    /**
     　* @Description: 获取当前用户所有的角色
     　*/
    @ApiOperation(value = "获取当前用户所有的角色", notes = "获取当前用户所有的角色")
    @GetMapping("getRoleListByUserId")
    public ResultVo getRoleListByUserId(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam Long userId) {
        List<F11RoleDO> roleList = shopRoleService.getRoleListByUserId(userId);
        return ResultVOUtils.success(roleList);
    }


    /**
     　* @Description: 新增用户
     　*/
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping(value = "save")
    public ResultVo index(@RequestBody @Valid ShopUserSaveForm shopUserSaveForm) {
        // 检查是否存在相同名称的管理员
        List<F08ShopUserDO> shopUserList = shopUserService.findByUserName(shopUserSaveForm.getUserName());
        if (!CollectionUtils.isEmpty(shopUserList)) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前管理员已存在");
        }
        F08ShopUserDO shopUser = new F08ShopUserDO();
        BeanUtils.copyProperties(shopUserSaveForm, shopUser);
        shopUser.setPwd(shopUserSaveForm.getPassword());
        F08ShopUserDO shopUser1 = SecurityUtils.getShopUser();
        if (shopUser1 == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        shopUser.setShopId(shopUser1.getShopId());
        shopUser.setShopName(shopUser1.getShopName());
        shopUser.setPwd(PasswordUtils.authAdminPwd(shopUser.getPwd()));
        shopUser.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        return ResultVOUtils.success(shopUserService.insertShopUser(shopUser,shopUserSaveForm));
    }

    /**
     　* @Description: 编辑用户
     　*/
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    @PostMapping(value = "edit")
    public ResultVo edit(@RequestBody @Valid ShopUserEditForm shopUserEditForm){
        F08ShopUserDO shopUser = new F08ShopUserDO();
        BeanUtils.copyProperties(shopUserEditForm, shopUser);
        if (!StringUtils.isEmpty(shopUser.getPwd())) {
            shopUser.setPwd(PasswordUtils.authAdminPwd(shopUser.getPwd()));
        }
        boolean b  = shopUserService.updateUser(shopUser);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

    /**
     　* @Description: 获取管理员列表
     　*/
    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @GetMapping("listUserHDPage")
    public ResultVo<PageSimpleVO<F08ShopUserDO>> getUserList(@Valid ShopUserQueryForm shopUserQueryForm, @Valid EntyPage page ) {

        Map<String, Object> map = new HashMap<>();
        map.put("shopUserQueryForm", shopUserQueryForm);
        map.put("page", page);
        List<F08ShopUserDO> shopUserList = shopUserService.listUserHDPage(map);
        //封装到分页
        PageSimpleVO<F08ShopUserDO> shopUserPageSimpleVO = new PageSimpleVO<>();
        shopUserPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopUserPageSimpleVO.setList(shopUserList);
        return ResultVOUtils.success(shopUserPageSimpleVO);
    }
    /**
     　* @Description: 修改管理员状态（停用启用接口）
     　* TODO 批量修改如果有一条错误，则全部错误
     　*/
    @ApiOperation(value = "修改管理员状态（停用启用接口）", notes = "修改管理员状态（停用启用接口）")
    @PostMapping("changeStatus")
    public ResultVo changeStatus(@RequestBody List<ShopChangeForm> shopChangeFormList) {
        shopUserService.changeStatus(shopChangeFormList);
        return ResultVOUtils.success();
    }

    /**
     　* @Description: 删除用户
     　*/
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("delete")
    public ResultVo delete(@RequestBody ShopUserDeleteForm shopUserDeleteForm) {
        if (shopUserDeleteForm == null || ArrayUtils.isEmpty(shopUserDeleteForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "参数为空！");
        }
        for (Long id:shopUserDeleteForm.getIds()){
            boolean b = shopUserService.deleteUserById(id);
            if (!b) {
                return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
            }
        }
        return ResultVOUtils.success();
    }
}


