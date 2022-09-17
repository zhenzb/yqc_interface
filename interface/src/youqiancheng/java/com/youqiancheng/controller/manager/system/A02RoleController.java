package com.youqiancheng.controller.manager.system;


import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthRole.A02RoleDeleteForm;
import com.youqiancheng.form.manager.AuthRole.A02RoleEditForm;
import com.youqiancheng.form.manager.AuthRole.A02RoleQueryForm;
import com.youqiancheng.form.manager.AuthRole.A02RoleSaveForm;
import com.youqiancheng.mybatis.model.A02Role;
import com.youqiancheng.service.manager.service.system.A02RoleService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 角色相关
　* @author shalongteng
　* @date 2020/3/31 15:21
　*/
@RestController
@RequestMapping("admin/system/role")
@Api(tags = "总管理后台-系统管理-角色管理")
public class A02RoleController {
    @Resource
    private A02RoleService a02RoleService;


    /**
    　* @Description: 角色列表
    　* @author shalongteng
    　* @date 2020/3/31 15:59
    　*/
    @ApiOperation(value = "角色列表", notes = "角色列表")
    @GetMapping("index")
    public ResultVo index(@Valid A02RoleQueryForm a02RoleQueryForm,
                          @Valid EntyPage page) {

        Map<String, Object> map = new HashMap<>();
        map.put("authRoleQueryForm", a02RoleQueryForm);
        map.put("page", page);
        List<A02Role> authRoleList = a02RoleService.listRolePage(map);
        //分页返回
        PageSimpleVO<A02Role> authRoleVOPageSimpleVO = new PageSimpleVO<>();
        authRoleVOPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authRoleVOPageSimpleVO.setList(authRoleList);

        return ResultVOUtils.success(authRoleVOPageSimpleVO);
    }

    /**
    　* @Description: 新增角色
    　* @author shalongteng
    　* @date 2020/3/31 15:58
    　*/
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("save")
    public ResultVo save(@RequestBody @Valid A02RoleSaveForm a02RoleSaveForm) {

        //当前角色已存在
        A02Role byName = a02RoleService.findByName(a02RoleSaveForm.getName());
        if (byName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }
        //插入角色
        A02Role a02Role = new A02Role();
        BeanUtils.copyProperties(a02RoleSaveForm, a02Role);
        boolean b = a02RoleService.insertAuthRole(a02Role);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

  /**
 　* @Description:删除角色
 　* @author shalongteng
 　* @date 2020/4/1 9:42
 　*/
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("delete")
    public ResultVo delete(@RequestBody A02RoleDeleteForm a02RoleDeleteForm) {
        if (a02RoleDeleteForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        //如果还有当前角色的管理员 提示先删除对应管理员
        List<Long> adminId = a02RoleService.findAdminByRoleId(a02RoleDeleteForm.getId());
        if (!adminId.isEmpty()) {
            return ResultVOUtils.error(ResultEnum.PRE_ADMIN);
        }
        //删除该角色
        boolean b = a02RoleService.deleteRoleById(a02RoleDeleteForm.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }


    /**
    　* @Description: 编辑角色
    　* @author shalongteng
    　* @date 2020/4/1 10:22
    　*/
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    @PostMapping("edit")
    public ResultVo edit(@RequestBody @Valid A02RoleEditForm a02RoleEditForm) {
        // 检查是否存在当前 角色名称，若存在，角色名称不能重复
        A02Role entity = a02RoleService.get(a02RoleEditForm.getId());
        if(entity==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        if(!entity.getName().equals(a02RoleEditForm.getName())){
            //当前角色已存在
            A02Role byName = a02RoleService.findByName(a02RoleEditForm.getName());
            if (byName != null) {
                return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "角色名称不能重复");
            }
        }

        A02Role a02Role = new A02Role();
        BeanUtils.copyProperties(a02RoleEditForm, a02Role);

        boolean b = a02RoleService.updateAuthRole(a02Role);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

}
