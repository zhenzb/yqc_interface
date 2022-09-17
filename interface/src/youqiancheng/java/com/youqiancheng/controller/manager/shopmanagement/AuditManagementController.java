package com.youqiancheng.controller.manager.shopmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.shop.*;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 审核管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_shop/audit")
@Api(tags = "总管理后台-商家管理-审核设置")
public class AuditManagementController {
    private static final Log log = LogFactory.getLog(AuditManagementController.class);

    @Resource
    private ShopManagementService shopManagementService;
    /**
    　* @Description: 添加/修改审核设置
    　* @author shalongteng
    　* @date 2020/4/13 15:54
    　*/
    @ApiOperation(value = "添加/修改审核设置", notes = "添加/修改审核设置")
    @PostMapping("addOrEditAudit")
    public ResultVo addOrEditAudit(@RequestBody ExamineSaveEditForm examineSaveEditForm) {
        if((examineSaveEditForm.getExamineFlag().equals(StatusConstant.enable.getCode()) || examineSaveEditForm.getExamineFlag().equals(StatusConstant.disable.getCode()))&&
                (examineSaveEditForm.getUploadFlag().equals(StatusConstant.enable.getCode()) || examineSaveEditForm.getUploadFlag().equals(StatusConstant.disable.getCode()))){
            shopManagementService.addOrEditAudit(examineSaveEditForm);
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
    public ResultVo getAudit() {
        F04ShopExamineSetDO audit = shopManagementService.getAudit();

        return ResultVOUtils.success(audit);
    }
}
