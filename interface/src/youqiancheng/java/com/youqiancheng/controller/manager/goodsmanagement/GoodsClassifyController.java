package com.youqiancheng.controller.manager.goodsmanagement;

import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionDeleteForm;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionEditForm;
import com.youqiancheng.form.manager.AuthPermission.A03PermissionSaveForm;
import com.youqiancheng.form.manager.Goods.CategoryDeleteForm;
import com.youqiancheng.form.manager.Goods.CategoryEditForm;
import com.youqiancheng.form.manager.Goods.CategorySaveForm;
import com.youqiancheng.form.manager.shop.MainProjectDeleteForm;
import com.youqiancheng.form.manager.shop.MainProjectEditForm;
import com.youqiancheng.form.manager.shop.MainProjectSaveForm;
import com.youqiancheng.mybatis.model.A03Permission;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.util.Tree;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 商品分类管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_goods/goodsClassify")
@Api(tags = "总管理后台-商品管理-商品分类")
public class GoodsClassifyController {
    private static final Log log = LogFactory.getLog(GoodsClassifyController.class);

    @Resource
    private GoodsListService goodsListService;


    /**
     　* @Description: 获取商品分类树形列表信息
     　* @author shalongteng
     　* @date 2020/4/1 11:36
     　*/
    @GetMapping("/treeList")
    @ApiOperation(value = "获取商品分类树形列表信息")
    public ResultVo  treeList() {
        List<Tree<C03CategoryDO>> children = goodsListService.getTree().getChildren();
        return ResultVOUtils.success(children);
    }

    /**
     　* @Description:新增商品分类
     　* @author shalongteng
     　* @date 2020/4/16 13:39
     　*/
    @PostMapping("save")
    @ApiOperation(value = "新增商品分类", notes = "新增商品分类")
    public ResultVo save(@RequestBody @Valid CategorySaveForm categorySaveForm   ) {


        // 如果不传递 pid ，则使用 默认值
        if (categorySaveForm.getParentId() == null) {
            categorySaveForm.setParentId(0);
        }
        C03CategoryDO c03CategoryDO = new C03CategoryDO();
        BeanUtils.copyProperties(categorySaveForm, c03CategoryDO);

        boolean b = goodsListService.insertC03CategoryDO(c03CategoryDO);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }
    /**
     　* @Description:编辑商品分类
     　* @author shalongteng
     　* @date 2020/4/1 14:40
     　*/
    @PostMapping("edit")
    @ApiOperation(value = "编辑商品分类", notes = "编辑商品分类")
    public ResultVo edit(@RequestBody @Valid CategoryEditForm categoryEditForm   ) {



        if (categoryEditForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        C03CategoryDO c03CategoryDO = new C03CategoryDO();
        BeanUtils.copyProperties(categoryEditForm, c03CategoryDO);

        boolean b = goodsListService.updateC03CategoryDO(c03CategoryDO);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
     　* @Description: 删除商品分类
     　* @author shalongteng
     　* @date 2020/4/1 15:19
     　*/
    @PostMapping("delete")
    @ApiOperation(value = "删除商品分类", notes = "删除商品分类")
    public ResultVo delete(@RequestBody CategoryDeleteForm categoryDeleteForm) {

        if (categoryDeleteForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //商品分类要级联删除
        boolean b = goodsListService.deleteById(categoryDeleteForm.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }
    /**
     　* @Description: 禁用商品分类
     　* @author shalongteng
     　* @date 2020/4/1 15:19
     　*/
    @PostMapping("forbidden")
    @ApiOperation(value = "禁用商品分类", notes = "禁用商品分类")
    public ResultVo forbidden(@RequestBody CategoryDeleteForm categoryDeleteForm) {

        if (categoryDeleteForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //商品分类要级联禁用
        boolean b = goodsListService.forbiddenById(categoryDeleteForm.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }
    /**
     　* @Description: 禁用商品分类
     　* @author shalongteng
     　* @date 2020/4/1 15:19
     　*/
    @PostMapping("start")
    @ApiOperation(value = "启用商品分类", notes = "禁用商品分类")
    public ResultVo start(@RequestBody CategoryDeleteForm categoryDeleteForm) {

        if (categoryDeleteForm.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
        //商品分类要级联禁用
        boolean b = goodsListService.start(categoryDeleteForm.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }
}
