package com.youqiancheng.controller.manager.withdrawmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.Goods.GoodsExamineSaveEditForm;
import com.youqiancheng.form.manager.withdraw.WithdrawalSetForm;
import com.youqiancheng.mybatis.model.C04GoodsExamineSetDO;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.service.manager.service.withdrawmanagement.WithdrawDepositService;
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
@RequestMapping("admin/_wihtdraw/audit")
@Api(tags = "总管理后台-提现管理-审核设置")
public class WithdrawAuditController {
    private static final Log log = LogFactory.getLog(WithdrawAuditController.class);

    @Resource
    private WithdrawDepositService withdrawDepositService;
    /**
    　* @Description: 添加/修改审核设置
    　* @author shalongteng
    　* @date 2020/4/18 15:54
    　*/
    @ApiOperation(value = "添加/修改审核设置", notes = "添加/修改审核设置")
    @PostMapping("addOrEditAudit")
    public ResultVo addOrEditAudit(@RequestBody WithdrawalSetForm withdrawalSetForm) {
        if(withdrawalSetForm.getType().equals(StatusConstant.enable.getCode()) ||
                withdrawalSetForm.getType().equals(StatusConstant.disable.getCode())){
            withdrawDepositService.addOrEditAudit(withdrawalSetForm);
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
    public ResultVo<F09WithdrawalSetDO> getAudit() {
        F09WithdrawalSetDO audit = withdrawDepositService.getAudit();

        return ResultVOUtils.success(audit);
    }
}
