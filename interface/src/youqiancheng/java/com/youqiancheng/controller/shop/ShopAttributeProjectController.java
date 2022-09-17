package com.youqiancheng.controller.shop;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.otther.ShopAttributeProjectForm;
import com.youqiancheng.form.shop.otther.ShopCommonForm;
import com.youqiancheng.mybatis.model.C06AttributeProjectDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.ShopAttributeProjectService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/18 17:38
 * @Version: V1.0
 */
@Api(tags = "商家管理--商品属性项")
@RestController
@RequestMapping("shop/attributeProject")
public class ShopAttributeProjectController {
    @Resource
    private ShopAttributeProjectService shopAttributeProjectService;

    @ApiOperation(value = "商品属性列表（分页）", notes = "商品属性列表（分页）")
    @PostMapping(value = "/pageAttributeProject")
    public ResultVo pageAttributeProject(@Valid EntyPage page ){

        Map<String, Object> map = new HashMap<>();
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        ShopAttributeProjectForm shopAttributeProjectForm = new ShopAttributeProjectForm();
        shopAttributeProjectForm.setShopId(shopUser.getShopId());
        map.put("shopAttributeProjectForm", shopAttributeProjectForm);
        map.put("page", page);
        List<C06AttributeProjectDO> attributeProjectlist = shopAttributeProjectService.listAttributeProjectHDPage(map);
        //封装到分页
        PageSimpleVO<C06AttributeProjectDO> shopAttributeProjectPageSimpleVO = new PageSimpleVO<>();
        shopAttributeProjectPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopAttributeProjectPageSimpleVO.setList(attributeProjectlist);
        return ResultVOUtils.success(shopAttributeProjectPageSimpleVO);
    }

    @ApiOperation(value = "商品属性列表（不分页）", notes = "商品属性列表（不分页）")
    @PostMapping(value = "/listAttributeProject")
    public ResultVo listAttributeProject(){
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        EntityWrapper<C06AttributeProjectDO> ew = new EntityWrapper<>();
        ew.eq("shop_id",shopUser.getShopId()).eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
        return ResultVOUtils.success(shopAttributeProjectService.listAttributeProject(ew));
    }

    @ApiOperation(value = "查看商品属性", notes = "查看商品属性")
    @PostMapping(value = "/getAttributeProject")
    public ResultVo getAttributeProject(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopAttributeProjectService.getAttributeProjectById(shopCommonForm.getIds()[0]));
    }

    @ApiOperation(value = "添加/编辑商品属性", notes = "添加/编辑商品属性")
    @PostMapping(value = "/saveOrUpdateAttributeProject")
    public ResultVo saveOrUpdateAttributeProject(@Valid C06AttributeProjectDO attributeProject){
        if(attributeProject == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopAttributeProjectService.saveOrUpdateAttributeProject(attributeProject));
    }

    @ApiOperation(value = "删除商品属性", notes = "删除商品属性")
    @PostMapping(value = "/delAttributeProject")
    public ResultVo delAttributeProject(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C06AttributeProjectDO> attributeProjectList = new ArrayList<>();
        for (long id:shopCommonForm.getIds()){
            C06AttributeProjectDO attributeProject = shopAttributeProjectService.getAttributeProjectById(id);
            if (attributeProject != null){
                attributeProject.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                attributeProject.setUpdateTime(LocalDateTime.now());
                attributeProjectList.add(attributeProject);
            }
        }
        return ResultVOUtils.success(shopAttributeProjectService.batchUpdateAttributeProject(attributeProjectList));
    }
}

