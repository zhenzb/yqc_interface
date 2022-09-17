package com.youqiancheng.controller.manager.system;

import com.handongkeji.dto.RouterDTO;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionDeleteForm;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionEditForm;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionSaveForm;
import com.youqiancheng.mybatis.model.A03Permission;
import com.youqiancheng.service.manager.service.system.A01AdminService;
import com.youqiancheng.service.manager.service.system.A03PermissionService;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
　* @Description:菜单权限相关
　* @author shalongteng
　* @date 2020/4/1 11:33
　*/
@Api(tags = "总管理后台-系统管理-系统菜单")
@RestController
@RequestMapping("admin/system/permission")
public class A03PermissionController {
    @Resource
    private A03PermissionService a03PermissionService;
    @Resource
    private A01AdminService a01AdminService;

    /**
     　* @Description: 根据父菜单id，获取他所有子菜单id
     　* @author shalongteng
     　* @date 2020/4/2 10:50
     　*/
    @ApiOperation(value = "根据角色id，获取他所有菜单id", notes = "根据角色id，获取他所有菜单id")
    @GetMapping("getIdListByRolId")
    public ResultVo getIdListByRolId(@ApiParam(name = "RoleId", value = "角色id", required = true) @RequestParam Long RoleId) {

        // 登录成功后获取权限
        List<Long> ids =  a01AdminService.getIdListByRolId(RoleId);

        //权限信息
        return ResultVOUtils.success(ids);
    }
    /**
     　* @Description: 获取当前用户权限列表
     　* @author shalongteng
     　* @date 2020/3/30 14:41
     　*/
    @ApiOperation(value = "获取当前用户菜单列表", notes = "获取当前用户菜单列表")
    @GetMapping(value = "getPermisssList")
    public ResultVo<RouterDTO> getPermisssList(@ApiParam(name = "id", value = "管理员id", required = true)@RequestParam(value = "id") Integer id) {

        // 登录成功后获取权限
        List<RouterDTO> routerDTOList =  a01AdminService.RouterByAdminId(id.longValue());

        //权限信息
        return ResultVOUtils.success(routerDTOList);
    }

    /**
    　* @Description: 菜单列表
    　* @author shalongteng
    　* @date 2020/4/1 11:36
    　*/
    @ApiOperation(value = "菜单列表", notes = "菜单列表")
    @GetMapping("index")
    public ResultVo index() {
        // 查询所有的菜单
        List<RouterDTO> routerDTOList =  a03PermissionService.RouterList();
        //权限信息
        return ResultVOUtils.success(routerDTOList);
    }


    /**
     　* @Description: 获取菜单树形列表信息
     　* @author shalongteng
     　* @date 2020/4/1 11:36
     　*/
    @GetMapping("/treeList")
    @ApiOperation(value = "获取菜单树形列表信息")
    ResultVo  treeList() {
        List<Tree<A03Permission>> children = a03PermissionService.getTree().getChildren();
        return ResultVOUtils.success(children);
    }

    /**
    　* @Description:新增菜单权限
    　* @author shalongteng
    　* @date 2020/4/1 13:39
    　*/
    @PostMapping("save")
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    public ResultVo save(@RequestBody @Valid A03PermissionSaveForm a03PermissionSaveForm   ) {

        // 如果不传递 pid ，则使用 默认值
        if (a03PermissionSaveForm.getParentId() == null) {
            a03PermissionSaveForm.setParentId(0L);
        }
        A03Permission a03Permission = new A03Permission();
        BeanUtils.copyProperties(a03PermissionSaveForm, a03Permission);

        boolean b = a03PermissionService.insertAuthPermission(a03Permission);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }
    /**
    　* @Description:编辑菜单
    　* @author shalongteng
    　* @date 2020/4/1 14:40
    　*/
    @PostMapping("edit")
    @ApiOperation(value = "编辑菜单", notes = "编辑菜单")
    public ResultVo edit(@RequestBody @Valid A03PermissionEditForm a03PermissionEditForm   ) {



        if (a03PermissionEditForm.getMenuId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        A03Permission a03Permission = new A03Permission();
        BeanUtils.copyProperties(a03PermissionEditForm, a03Permission);

        boolean b = a03PermissionService.updateAuthPermission(a03Permission);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
    　* @Description: 删除菜单
    　* @author shalongteng
    　* @date 2020/4/1 15:19
    　*/
    @PostMapping("delete")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public ResultVo delete(@RequestBody A03PermissionDeleteForm a03PermissionDeleteForm) {

        if (a03PermissionDeleteForm.getMenuId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //如果还有当前菜单的角色 提示先删除对应角色
        List<Long> roleId =  a03PermissionService.findRoleByPermissionId(a03PermissionDeleteForm.getMenuId());
        if (!roleId.isEmpty()) {
            return ResultVOUtils.error(ResultEnum.PRE_ROLE);
        }
        //菜单要级联删除
        boolean b = a03PermissionService.deleteById(a03PermissionDeleteForm.getMenuId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }


}
