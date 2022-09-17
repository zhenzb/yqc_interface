package com.youqiancheng.controller.manager.withdrawmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.withdraw.WithdrawRuleForm;
import com.youqiancheng.form.manager.withdraw.WithdrawalSetForm;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import com.youqiancheng.mybatis.model.F10WithdrawalRuleDO;
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
import java.util.List;

/**
　* @Description: 审核管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_wihtdraw/rule")
@Api(tags = "总管理后台-提现管理-提现规则")
public class WithdrawRuleController {
    private static final Log log = LogFactory.getLog(WithdrawRuleController.class);

    @Resource
    private WithdrawDepositService withdrawDepositService;
    /**
    　* @Description: 添加/修改提现规则
    　* @author shalongteng
    　* @date 2020/4/20 15:54
    　*/
    @ApiOperation(value = "添加/修改提现规则", notes = "添加/修改提现规则")
    @PostMapping("addOrEditRule")
    public ResultVo addOrEditRule(@RequestBody WithdrawRuleForm withdrawRuleForm) {

        withdrawDepositService.addOrEditRule(withdrawRuleForm);

        return ResultVOUtils.success();
    }
    /**
    　* @Description:查询提现规则
    　* @author shalongteng
    　* @date 2020/4/24 15:04
    　*/
    @ApiOperation(value = "查询提现规则", notes = "查询提现规则")
    @PostMapping("getRule")
    public ResultVo<List<F10WithdrawalRuleDO>> getRule() {
        List<F10WithdrawalRuleDO> ruleList = withdrawDepositService.getRule();

        return ResultVOUtils.success(ruleList);
    }
}
