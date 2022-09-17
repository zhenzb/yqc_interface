package com.youqiancheng.controller.app;


import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.PromotionIncomeAbility;
import com.youqiancheng.mybatis.model.F17PromotionIncomeDO;
import com.youqiancheng.service.app.service.F17PromotionIncomeAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Author tyf
 * Date  2021-03-06
 */
@Api(tags = {"手机端-推广收益金额表接口"})
@RestController
@RequestMapping(value = "app/promotionIncome")
public class F17PromotionIncomeAppController {
    @Autowired
    private F17PromotionIncomeAppService f17PromotionIncomeService;
    @Autowired
    private PromotionIncomeAbility promotionIncomeAbility;


    /**
     * @api {GET} /app/promotionIncome/getPromotionIncomePage 004获取我的邀请推广收益
     * @apiGroup 003用户邀请模块相关接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 获取我的邀请推广收益
     * @apiParam {long} userId 用户Id[必填]
     * @apiParam {int} currentPage 当前页数 [必填]
     * @apiParam {int} pageSize  每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *   "code": 0, 操作标识成 0:成功   1:失败
     *   "message": "success", 操作说明
     *   "data": {
     *          "currentPage": null,
     *         "totalNumber": 2,
     *         "list": [
     *             {
     *                 "id": 2,
     *                 "availableWithdrawMoney": 17100, 店铺总交易额
     *                 "alipayTaxes": 102.6, 支付宝税款金额
     *                 "yqcMoney": 239.4, 有钱城平台税前金额
     *                 "userPromotionExpenses": 225.04, 有钱城税后金额
     *                 "userAfterTax": 22.5, 用户获得推广费金额
     *                 "userActualAmount": 20.7, 用户最终获得推广费金额
     *                 "shopId": 0, 店铺Id
     *                 "userId": 582, 用户Id
     *                 "createTime": "2021-03-07 12:33:21", 添加时间
     *                 "updateTime": "2021-03-07 12:33:21", 修改时间
     *                 "isShow": 1,
     *                 "redundancy": null
     *             },
     *             {
     *                 "id": 1,
     *                 "availableWithdrawMoney": 0,
     *                 "alipayTaxes": 0,
     *                 "yqcMoney": 0,
     *                 "userPromotionExpenses": 0,
     *                 "userAfterTax": 0,
     *                 "userActualAmount": 0,
     *                 "shopId": 276,
     *                 "userId": 582,
     *                 "createTime": "2021-03-07 12:33:21",
     *                 "updateTime": "2021-03-07 12:33:21",
     *                 "isShow": 1,
     *                 "redundancy": null
     *             }
     *         ],
     *         "map": null
     *     }
     * }
     */
    @ApiOperation(value = "获取推广收益金额表列表（分页+过滤参数）")
    @GetMapping(value = "getPromotionIncomePage")
    ResultVo<PageSimpleVO<F17PromotionIncomeDO>> listByPage(@Valid EntyPage page, BindingResult bindingResult,Long userId) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        //计算推广费用
        promotionIncomeAbility.addPromotionIncome(userId);
        //查询推广费用
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        map.put("userId",userId);
        List<F17PromotionIncomeDO> f17PromotionIncomeList = f17PromotionIncomeService.listHDPage(map);
        //封装到分页
        PageSimpleVO<F17PromotionIncomeDO> f17PromotionIncomeSimpleVO = new PageSimpleVO<>();
            f17PromotionIncomeSimpleVO.setTotalNumber(page.getTotalNumber());
            f17PromotionIncomeSimpleVO.setList(f17PromotionIncomeList);

        return ResultVOUtils.success(f17PromotionIncomeSimpleVO);
    }

    @ApiOperation(value = "获取推广收益金额表列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list(BindingResult bindingResult) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<F17PromotionIncomeDO> list = f17PromotionIncomeService.list(map);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据ID获取推广收益金额表记录")
    @GetMapping("{id}")
     ResultVo get(@PathVariable("id") Long id ){
        F17PromotionIncomeDO f17PromotionIncome = f17PromotionIncomeService.get(id);
        return ResultVOUtils.success(f17PromotionIncome);
    }

    @ApiOperation(value = "保存单条推广收益金额表记录")
    @PostMapping()
    ResultVo insert(@RequestBody F17PromotionIncomeDO f17PromotionIncome) {
        int num=f17PromotionIncomeService.insert(f17PromotionIncome);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<F17PromotionIncomeDO> f17PromotionIncomes) {
        int num=f17PromotionIncomeService.insertBatch(f17PromotionIncomes);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改推广收益金额表记录")
    @PutMapping
    ResultVo update(@RequestBody F17PromotionIncomeDO f17PromotionIncome) {
        int num= f17PromotionIncomeService.update(f17PromotionIncome);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用推广收益金额表记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= f17PromotionIncomeService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用推广收益金额表记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= f17PromotionIncomeService.start(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }



}
