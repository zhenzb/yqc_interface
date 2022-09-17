package com.youqiancheng.controller.manager.withdrawmanagement;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.SignacontractAbility;
import com.youqiancheng.controller.wechatpay.weixinpay.util.IpAddressUtil;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.manager.withdraw.WithdrawalApplicationPageForm;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.service.manager.service.withdrawmanagement.WithdrawDepositService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 提现管理
　* @author shalongteng
　* @date 2020/4/18 18:41
　*/
@RestController
@RequestMapping("admin/_wihtdraw/withdrawDeposit")
@Api(tags = "总管理后台-提现管理")
public class WithdrawDepositController {
    private static final Log log = LogFactory.getLog(WithdrawDepositController.class);

    @Resource
    private WithdrawDepositService withdrawDepositService;
    @Autowired
    private SignacontractAbility signacontractAbility;



    /**
     * @api {GET} /admin/_wihtdraw/withdrawDeposit/pageWithdrawalApplication 002提现申请列表接口
     * @apiGroup 006提现模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 提现申请列表接口
     * @apiHeader {String} token 用户token [必填]
     * @apiParam {int} examineStatus 审核状态 [非必填] 1:待审核 2：审核通过  3：审核拒绝
     * @apiParam {String} shopName 店铺名称 [非必填]
     * @apiParam {String} mobile 用户手机号 [非必填]
     * @apiParam {int} type 申请提现类型 [必填] 1:商家 2:用户
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize  每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *     "code": 0,
     *     "message": "success",
     *     "data": {
     *         "currentPage": null,
     *         "totalNumber": 1,
     *         "list": [
     *             {
     *                 "id": 246,
     *                 "accountId": 1022,
     *                 "withdrawalMoney": 0,
     *                 "serviceMoney": null,
     *                 "actualWithdrawalMoney": 5,
     *                 "originalServiceRatio": null,
     *                 "actualServiceRatio": null,
     *                 "currentRedenvelopes": null,
     *                 "type": 2,
     *                 "orderNo": null,
     *                 "account": null,
     *                 "transferNo": null,
     *                 "examineStatus": 1,
     *                 "status": 1,
     *                 "applyTime": "2021-05-09",
     *                 "examineTime": null,
     *                 "shopName": null,
     *                 "reason": null
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "提现列表", notes = "提现列表")
    @GetMapping(value = "/pageWithdrawalApplication")
    public ResultVo<PageSimpleVO<F06WithdrawalApplicationDO>> pagePublicity(@Valid WithdrawalApplicationPageForm shopWithdrawalApplicationPageForm   ,
                                                                            @Valid EntyPage page) {

        Map<String, Object> map = new HashMap<>();
        map.put("shopWithdrawalApplicationPageForm", shopWithdrawalApplicationPageForm);
        map.put("page", page);
        List<F06WithdrawalApplicationDO> withdrawalApplication = withdrawDepositService.listWithdrawalApplicationHDPage(map);
        //封装到分页
        PageSimpleVO<F06WithdrawalApplicationDO> shopWithdrawalApplicationPageSimpleVO = new PageSimpleVO<>();
        shopWithdrawalApplicationPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopWithdrawalApplicationPageSimpleVO.setList(withdrawalApplication);
        return ResultVOUtils.success(shopWithdrawalApplicationPageSimpleVO);
    }

//    /**
//     　* @Description: 批量通过/拒绝/删除
//     　* @author shalongteng
//     　* @date 2020/4/18 14:41
//     　*/
//    @ApiOperation(value = "批量通过/拒绝/删除", notes = "批量通过/拒绝/删除")
//    @PostMapping("batchPassRefuse")
//    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
//        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
//        }else {
//            Integer integer = withdrawDepositService.batchPassRefuse(shopPassRefuseFormList);
//            if(integer == 1){
//                return ResultVOUtils.success();
//            }
//        }
//
//        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
//    }
    /**
     　* @Description: 批量通过/拒绝/删除
     　* @author shalongteng
     　* @date 2020/4/18 14:41
     　*/
    /**
     * @api {POST} /admin/_wihtdraw/withdrawDeposit/batchPassRefuse 004提现申请审批接口 [JSON 格式传参]
     * @apiGroup 006提现模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 提现申请审核接口
     * @apiHeader {String} token 用户token [必填]
     * @apiParam {int} id 申请体现id [必填]
     * @apiParam {int} status 审核状态 [必填] 2：审核通过  3：审核拒绝
     * @apiParam {String} reason 审批理由 [非必填]
     * @apiParam {int} type 审批类型 [必填] 1:店铺提现，2:用户红包提现，3:推广收益提现
     * @apiSuccessExample {json} 返回示例:
     *  {
     *         "code": 0, 操作标识成 0:成功   1:失败
     *         "message": "success", 操作说明
     *         "data": {}
     *   }
     */
    @ApiOperation(value = "批量通过/拒绝/删除", notes = "批量通过/拒绝/删除")
    @PostMapping("batchPassRefuse")
    @Transactional
    public ResultVo batchPassRefuse(@RequestBody  ShopPassRefuseForm shopPassRefuseForm, HttpServletRequest request) {
        if(shopPassRefuseForm.getType() ==2){
            //用户零钱提现
            return signacontractAbility.yeYunbatchPassRefuse(shopPassRefuseForm);
        }else if(shopPassRefuseForm.getType() ==3){
            //用户推广收益提现
            return signacontractAbility.yeYunbatchPassRefuse(shopPassRefuseForm);
        }
        String ipAddr = IpAddressUtil.getIpAddr(request);
        return withdrawDepositService.batchPassRefuse(shopPassRefuseForm, ipAddr);

    }
}
