package com.youqiancheng.controller.shop.system;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.dto.RouterDTO;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.system.userPermission.ShopPermissionDeleteForm;
import com.youqiancheng.form.shop.system.userPermission.ShopPermissionEditForm;
import com.youqiancheng.form.shop.system.userPermission.ShopPermissionSaveForm;
import com.youqiancheng.mybatis.model.F12PermissionDO;
import com.youqiancheng.service.shop.system.ShopPermissionService;
import com.youqiancheng.service.shop.system.ShopUserService;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
　* @Description:商家管理--菜单权限相关
　*/
@Api(tags = "商家管理--系统管理--系统菜单")
@RestController
@RequestMapping("shop/system/permission")
public class ShopPermissionController {
    @Resource
    private ShopPermissionService shopPermissionService;
    @Resource
    ShopUserService shopUserService;

    /**
     　* @Description: 根据父菜单id，获取他所有子菜单id
     　*/
    @ApiOperation(value = "根据角色id，获取他所有菜单id", notes = "根据角色id，获取他所有菜单id")
    @GetMapping("getIdListByRolId")
    public ResultVo getIdListByRolId(@ApiParam(name = "RoleId", value = "角色id", required = true) @RequestParam Long RoleId) {
        // 登录成功后获取权限
        List<Long> ids =  shopUserService.getIdListByRolId(RoleId);
        //权限信息
        return ResultVOUtils.success(ids);
    }
    /**
     　* @Description: 获取当前用户权限列表
     　*/
    @ApiOperation(value = "获取当前用户菜单列表", notes = "获取当前用户菜单列表")
    @GetMapping(value = "getPermisssList")
    public ResultVo<RouterDTO> getPermisssList(@ApiParam(name = "id", value = "管理员id", required = true)@RequestParam(value = "id") Integer id) {
        // 登录成功后获取权限
        List<RouterDTO> routerDTOList =  shopUserService.RouterByUserId(id.longValue());
        //权限信息
        return ResultVOUtils.success(routerDTOList);
    }

    /**
    　* @Description: 菜单列表
    　*/
    @ApiOperation(value = "菜单列表", notes = "菜单列表")
    @GetMapping("index")
    public ResultVo index() {
        // 查询所有的菜单
        List<RouterDTO> routerDTOList =  shopPermissionService.RouterList();
        //权限信息
        return ResultVOUtils.success(routerDTOList);
    }


    /**
     　* @Description: 获取菜单树形列表信息
     　*/
    @GetMapping("/treeList")
    @ApiOperation(value = "获取菜单树形列表信息")
    ResultVo  treeList() {
        List<Tree<F12PermissionDO>> children = shopPermissionService.getTree().getChildren();
        return ResultVOUtils.success(children);
    }

    /**
    　* @Description:新增菜单权限
    　*/
    @PostMapping("save")
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    public ResultVo save(@RequestBody @Valid ShopPermissionSaveForm shopPermissionSaveForm) {

        // 如果不传递 pid ，则使用 默认值
        if (shopPermissionSaveForm.getParentId() == null) {
            shopPermissionSaveForm.setParentId(0L);
        }
        F12PermissionDO f12Permission = new F12PermissionDO();
        BeanUtils.copyProperties(shopPermissionSaveForm, f12Permission);
        f12Permission.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        boolean b = shopPermissionService.insertShopPermission(f12Permission);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }
    /**
    　* @Description:编辑菜单
    　*/
    @PostMapping("edit")
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单")
    public ResultVo edit(@RequestBody @Valid ShopPermissionEditForm shopPermissionEditForm) {
        if (shopPermissionEditForm.getMenuId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        F12PermissionDO f12Permission = new F12PermissionDO();
        BeanUtils.copyProperties(shopPermissionEditForm, f12Permission);

        boolean b = shopPermissionService.updateShopPermission(f12Permission);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

    /**
    　* @Description: 删除菜单
    　*/
    @PostMapping("delete")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public ResultVo delete(@RequestBody ShopPermissionDeleteForm shopPermissionDeleteForm) {
        if (shopPermissionDeleteForm.getMenuId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //如果还有当前菜单的角色 提示先删除对应角色
        List<Long> roleId =  shopPermissionService.findShopRoleByPermissionId(shopPermissionDeleteForm.getMenuId());
        if (!CollectionUtils.isEmpty(roleId)) {
            return ResultVOUtils.error(ResultEnum.PRE_ROLE);
        }
        //菜单要级联删除
        boolean b = shopPermissionService.deleteById(shopPermissionDeleteForm.getMenuId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }


}
