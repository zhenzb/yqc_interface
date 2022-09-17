package com.youqiancheng.controller.manager.system;

import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.PasswordUtils;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthAdmin.*;
import com.youqiancheng.mybatis.model.A01Admin;
import com.youqiancheng.mybatis.model.A02Role;
import com.youqiancheng.service.manager.service.system.A01AdminService;
import com.youqiancheng.service.manager.service.system.A02RoleService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 管理员相关业务控制器
　* @author shalongteng
　* @date 2020/3/30 10:58
  * todo userName:  admin 规定为超级管理员
　*/
@RestController
@RequestMapping("admin/system/user")
@Api(tags = "总管理后台-系统管理-管理员用户")
public class A01AdminController {
    @Resource
    private A01AdminService a01AdminService;
    @Resource
    private A02RoleService a02RoleService;

    /**
    　* @Description: 获取所有的角色
    　* @author shalongteng
    　* @date 2020/4/1 15:25
    　*/
    @ApiOperation(value = "获取所有的角色", notes = "获取所有的角色")
    @GetMapping("roleList")
    public ResultVo roleList() {
        List<A02Role> authRoleList = a02RoleService.listAuthAdminRole();

        return ResultVOUtils.success(authRoleList);
    }
    /**
    　* @Description: 获取当前用户所有的角色
    　* @author shalongteng
    　* @date 2020/4/2 14:36
    　*/
    @ApiOperation(value = "获取当前用户所有的角色", notes = "获取当前用户所有的角色")
    @GetMapping("getRoleListByAdminId")
    public ResultVo getRoleListByAdminId(@ApiParam(name = "adminId", value = "用户id", required = true) @RequestParam Long adminId) {
        List<A02Role> authRoleList = a02RoleService.getRoleListByAdminId(adminId);

        return ResultVOUtils.success(authRoleList);
    }


    /**
     　* @Description: 新增用户
     　* @author shalongteng
     　* @date 2020/3/28 15:21
     　*/
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping(value = "save")
    public ResultVo index(@RequestBody @Valid A01AdminSaveForm authAdminSaveForm) {


        // 检查是否存在相同名称的管理员
        A01Admin byUserName = a01AdminService.findByUserName(authAdminSaveForm.getUserName());
        if (byUserName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前管理员已存在");
        }

        A01Admin a01Admin = new A01Admin();
        BeanUtils.copyProperties(authAdminSaveForm, a01Admin);
        a01Admin.setPassword(PasswordUtils.authAdminPwd(a01Admin.getPassword()));
        int  id  = a01AdminService.insertAuthAdmin(a01Admin,authAdminSaveForm);

        return ResultVOUtils.success();
    }

    /**
    　* @Description: 编辑用户
    　* @author shalongteng
    　* @date 2020/4/1 15:32
    　*/
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    @PostMapping(value = "edit")
    public ResultVo edit(@RequestBody @Valid A01AdminEditForm a01AdminEditForm) {


        A01Admin a01Admin = new A01Admin();
        BeanUtils.copyProperties(a01AdminEditForm, a01Admin);
        if (a01Admin.getPassword() != null && !StringUtils.isEmpty(a01Admin.getPassword())) {
            a01Admin.setPassword(PasswordUtils.authAdminPwd(a01Admin.getPassword()));
        }
        boolean b  = a01AdminService.updateAdmin(a01Admin);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

    /**
    　* @Description: 获取管理员列表
    　* @author shalongteng
    　* @date 2020/3/30 13:48
    　*/
    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @GetMapping("listAdminHDPage")
    public ResultVo<PageSimpleVO<A01Admin>> getAdminList(@Valid A01AdminQueryForm authAdminQueryForm, @Valid EntyPage page ) {


        Map<String, Object> map = new HashMap<>();
        map.put("authAdminQueryForm", authAdminQueryForm);
        map.put("page", page);
        List<A01Admin> authAdminList = a01AdminService.listAdminHDPage(map);

        //封装到分页
        PageSimpleVO<A01Admin> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(authAdminList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }
    /**
    　* @Description: 修改管理员状态（停用启用接口）
    　* @author shalongteng
    　* @date 2020/3/30 15:03
    　* TODO 批量修改如果有一条错误，则全部错误
    　*/
    @ApiOperation(value = "修改管理员状态（停用启用接口）", notes = "修改管理员状态（停用启用接口）")
    @PostMapping("changeStatus")
    public ResultVo changeStatus(@RequestBody List<A01ChangeForm> a01ChangeFormList) {

        List<String> errorList = a01AdminService.changeStatus(a01ChangeFormList);
        return ResultVOUtils.success();
    }

    /**
    　* @Description: 删除用户
    　* @author shalongteng
    　* @date 2020/4/1 16:04
    　*/
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("delete")
    public ResultVo delete(@RequestBody A01AdminDeleteForm a01AdminDeleteForm) {

        if (a01AdminDeleteForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, "参数错误！");
        }

        boolean b = a01AdminService.deleteAdminById(a01AdminDeleteForm.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

}
