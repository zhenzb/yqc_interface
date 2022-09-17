package com.youqiancheng.controller.manager.goodsmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.Goods.GoodsExamineSaveEditForm;
import com.youqiancheng.form.manager.shop.ExamineSaveEditForm;
import com.youqiancheng.mybatis.model.C04GoodsExamineSetDO;
import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
　* @Description: 审核管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_goods/audit")
@Api(tags = "总管理后台-商品管理-审核设置")
public class AuditController {
    private static final Log log = LogFactory.getLog(AuditController.class);

    @Resource
    private GoodsListService goodsListService;
    /**
    　* @Description: 添加/修改审核设置
    　* @author shalongteng
    　* @date 2020/4/13 15:54
    　*/
    @ApiOperation(value = "添加/修改审核设置", notes = "添加/修改审核设置")
    @PostMapping("addOrEditAudit")
    public ResultVo addOrEditAudit(@RequestBody GoodsExamineSaveEditForm examineSaveEditForm) {
        if(examineSaveEditForm.getExamineFlag().equals(StatusConstant.enable.getCode()) ||
                examineSaveEditForm.getExamineFlag().equals(StatusConstant.disable.getCode())){
            goodsListService.addOrEditAudit(examineSaveEditForm);
        }else {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        return ResultVOUtils.success();
    }
    /**
    　* @Description:查询审核设置
    　* @author shalongteng
    　* @date 2020/4/14 15:04
    　*/
    @ApiOperation(value = "查询审核设置", notes = "查询审核设置")
    @PostMapping("getAudit")
    public ResultVo<C04GoodsExamineSetDO> getAudit() {
        C04GoodsExamineSetDO audit = goodsListService.getAudit();

        return ResultVOUtils.success(audit);
    }
}
