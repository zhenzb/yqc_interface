package com.youqiancheng.controller.shop.system;


import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.system.userRole.ShopRoleDeleteForm;
import com.youqiancheng.form.shop.system.userRole.ShopRoleEditForm;
import com.youqiancheng.form.shop.system.userRole.ShopRoleQueryForm;
import com.youqiancheng.form.shop.system.userRole.ShopRoleSaveForm;
import com.youqiancheng.mybatis.model.F11RoleDO;
import com.youqiancheng.service.shop.system.ShopRoleService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 商家管理--角色相关
　*/
@RestController
@RequestMapping("shop/system/role")
@Api(tags = "商家管理--系统管理--角色管理")
public class ShopRoleController {
    @Resource
    private ShopRoleService shopRoleService;


    /**
    　* @Description: 角色列表
    　*/
    @ApiOperation(value = "角色列表", notes = "角色列表")
    @GetMapping("index")
    public ResultVo shopRoleList(@Valid ShopRoleQueryForm shopRoleQueryForm,
                          @Valid EntyPage page   ) {


        Map<String, Object> map = new HashMap<>();
        map.put("userRoleQueryForm", shopRoleQueryForm);
        map.put("page", page);
        List<F11RoleDO> shopRoleList = shopRoleService.listRolePage(map);
        //分页返回
        PageSimpleVO<F11RoleDO> shopRoleVOPageSimpleVO = new PageSimpleVO<>();
        shopRoleVOPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopRoleVOPageSimpleVO.setList(shopRoleList);
        return ResultVOUtils.success(shopRoleVOPageSimpleVO);
    }

    /**
    　* @Description: 新增角色
    　*/
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("save")
    public ResultVo save(@RequestBody @Valid ShopRoleSaveForm shopRoleSaveForm   ) {

        //当前角色已存在
        F11RoleDO byName = shopRoleService.findByName(shopRoleSaveForm.getName());
        if (byName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }
        //插入角色
        F11RoleDO f11Role = new F11RoleDO();
        BeanUtils.copyProperties(shopRoleSaveForm, f11Role);
        boolean b = shopRoleService.insertUserRole(f11Role);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

  /**
 　* @Description:删除角色
 　*/
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("delete")
    public ResultVo delete(@RequestBody ShopRoleDeleteForm shopRoleDeleteForm) {
        if (shopRoleDeleteForm == null || ArrayUtils.isEmpty(shopRoleDeleteForm.getIds())) {
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        for (Long id:shopRoleDeleteForm.getIds()){
            //如果还有当前角色的管理员 提示先删除对应管理员
            List<Long> userIds = shopRoleService.findUserByRoleId(id);
            if (!CollectionUtils.isEmpty(userIds)) {
                return ResultVOUtils.error(ResultEnum.PRE_ADMIN);
            }
            //删除该角色
            boolean b = shopRoleService.deleteRoleById(id);
            if (!b) {
                return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
            }
        }
        return ResultVOUtils.success();
    }


    /**
    　* @Description: 编辑角色
    　*/
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    @PostMapping("edit")
    public ResultVo edit(@RequestBody @Valid ShopRoleEditForm shopRoleEditForm   ) {

        // 检查是否存在当前 角色名称，若存在，角色名称不能重复
        F11RoleDO entity = shopRoleService.get(shopRoleEditForm.getId());
        if(entity==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        if(!entity.getName().equals(shopRoleEditForm.getName())){
            //当前角色已存在
            F11RoleDO byName = shopRoleService.findByName(shopRoleEditForm.getName());
            if (byName != null) {
                return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "角色名称不能重复");
            }
        }
        F11RoleDO f11Role = new F11RoleDO();
        BeanUtils.copyProperties(shopRoleEditForm, f11Role);
        boolean b = shopRoleService.updateUserRole(f11Role);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }
        return ResultVOUtils.success();
    }

}
