package com.youqiancheng.controller.shop;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ShopAbility;
import com.youqiancheng.form.shop.F06WithdrawalApplicationSaveForm;
import com.youqiancheng.form.shop.otther.ShopCommonForm;
import com.youqiancheng.form.shop.otther.ShopWithdrawalApplicationPageForm;
import com.youqiancheng.mybatis.dao.F07ShopAccountFlowDao;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.mybatis.model.F06WithdrawalApplicationDO;
import com.youqiancheng.mybatis.model.F07ShopAccountFlowDO;
import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.service.shop.ShopAccountService;
import com.youqiancheng.service.shop.ShopWithdrawalApplicationService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import com.youqiancheng.vo.shop.WithdrawalParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:47
 * @Version: V1.0
 */
@Api(tags = "商家管理--商家账户--账户管理")
@RestController
@RequestMapping("shop/accountManagement")
public class ShopAccountManagementController {
    @Resource
    private ShopAccountService shopAccountService;
    @Resource
    private ShopWithdrawalApplicationService shopWithdrawalApplicationService;
    @Resource
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Resource
    private ShopAbility shopAbility;

    @ApiOperation(value = "获取商家账户信息", notes = "获取商家账户信息")
    @PostMapping(value = "/getShopAccountByUserId")
    public ResultVo getShopAccountByUserId(){
        return ResultVOUtils.success(shopAccountService.getShopAccountByUserId());
    }
    @ApiOperation(value = "提现列表", notes = "提现列表")
    @GetMapping(value = "/pageWithdrawalApplication")
    public ResultVo<PageSimpleVO<F06WithdrawalApplicationDO>> pagePublicity(@Valid ShopWithdrawalApplicationPageForm form, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<F06WithdrawalApplicationDO> withdrawalApplication = shopWithdrawalApplicationService.listHDPage(map);
        //封装到分页
        PageSimpleVO<F06WithdrawalApplicationDO> shopWithdrawalApplicationPageSimpleVO = new PageSimpleVO<>();
        shopWithdrawalApplicationPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopWithdrawalApplicationPageSimpleVO.setList(withdrawalApplication);
        return ResultVOUtils.success(shopWithdrawalApplicationPageSimpleVO);
    }

    @ApiOperation(value = "商家账户流水列表", notes = "商家账户流水列表")
    @GetMapping(value = "/pageAccountFlow")
    public ResultVo<PageSimpleVO<F07ShopAccountFlowDO>> pageAccountFlow(@Valid ShopWithdrawalApplicationPageForm form, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        map.put("accountId",form.getAccountId());
        if(form.getExamineStatus() == 1){
            //账户余额
            map.put("status",1);
        }else{
            //可提现金额
            map.put("status",9);
        }
        List<F07ShopAccountFlowDO> f07ShopAccountFlowDOS = f07ShopAccountFlowDao.listHDPage(map);
        //封装到分页
        PageSimpleVO<F07ShopAccountFlowDO> shopWithdrawalApplicationPageSimpleVO = new PageSimpleVO<>();
        shopWithdrawalApplicationPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopWithdrawalApplicationPageSimpleVO.setList(f07ShopAccountFlowDOS);
        return ResultVOUtils.success(shopWithdrawalApplicationPageSimpleVO);
    }


    @ApiOperation(value = "发起提现申请", notes = "发起提现申请")
    @PostMapping(value = "/creatWithdrawalApplication")
    public ResultVo creatWithdrawalApplication(@Valid @RequestBody F06WithdrawalApplicationSaveForm dto){

        if(dto == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        //计算实际提现到账金额
        BigDecimal bigDecimal =  DecimalUtil.subtractionBigMal(String.valueOf(dto.getWithdrawalMoney()),String.valueOf(dto.getServiceMoney()),0,0);
        F06WithdrawalApplicationDO entity=new F06WithdrawalApplicationDO();
        BeanUtils.copyProperties(dto,entity);
        entity.setActualWithdrawalMoney(bigDecimal);//实际提现金额
        entity.setExamineStatus(StatusConstant.ExamineStatus.un_examine.getCode());
        entity.setApplyTime(LocalDateTime.now());
        return ResultVOUtils.success(shopWithdrawalApplicationService.save(entity));
    }

    @ApiOperation(value = "删除提现记录", notes = "删除提现记录")
    @PostMapping(value = "/delWithdrawalApplication")
    public ResultVo delWithdrawalApplication(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        return ResultVOUtils.success(shopWithdrawalApplicationService.batchDelWithdrawalApplicationById(Arrays.asList(shopCommonForm.getIds())));
    }

    @ApiOperation(value = "获取提现参数——账户ID")
    @PostMapping(value = "/getWithdrawalParam")
    public ResultVo getWithdrawalParam(Long id){
        WithdrawalParamVO vo=shopAccountService.getWithdrawalParam(id);
        return ResultVOUtils.success(vo);
    }



    /**
     * @api {GET} /shop/accountManagement/getShopReadGrantRecord 001商家上街历史接口
     * @apiGroup 008 2022-04新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 上街历史接口
     * @apiHeader {String} token 用户token [必填] 登录接口获取
     * @apiParam {Long} shopId 店铺d [必填]
     * @apiParam {String} startTime 起始时间 [非必填] 示例：2022-04-17
     * @apiParam {String} endTime 结束时间 [非必填]    示例：2022-04-17
     * @apiParam {int} currentPage 当前页 [必填]
     * @apiParam {int} pageSize 每页条数 [必填]
     * @apiSuccessExample {json} 返回示例:
     * {
     *     "code": 0,
     *     "message": "success",
     *     "data": {
     *         "currentPage": 1,
     *         "totalNumber": 2,
     *         "list": [
     *             {
     *                 "id": 1375,
     *                 "streetId": 230,
     *                 "shopId": 338,
     *                 "createPerson": "admin",
     *                 "createTime": "2022-04-17 18:07:40",  --上街时间
     *                 "updatePerson": "admin",
     *                 "updateTime": "2022-04-17 18:07:40",
     *                 "deleteFlag": 1,
     *                 "endFlag": 1,
     *                 "payType": 0,
     *                 "payNo": null,
     *                 "money": 1,                --上街红包金额
     *                 "shopName": "内部测试",    --店铺名称
     *                 "streetName": "店铺街"      --街道名称
     *             }
     *         ],
     *         "map": {
     *             "totalAmt": 101               --本次查询的红包发放总金额
     *         }
     *     }
     * }
     */
    @ApiOperation(value = "商家后台查询上街记录", notes = "查询上街记录")
    @GetMapping("getShopReadGrantRecord")
    public ResultVo getShopReadGrantRecord(String shopId,String startTime,String endTime,@Valid EntyPage page){
        Map<String, Object> map = new HashMap<>();
        checkParams(shopId, startTime, endTime, page, map);
        PageSimpleVO<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDO = shopAbility.getE04RedenvelopesGrantRecordDOPageSimpleVO(page, map);
        return ResultVOUtils.success(e04RedenvelopesGrantRecordDO);
    }



    private void checkParams(String shopId, String startTime, String endTime, @Valid EntyPage page, Map<String, Object> map) {
        if(shopId ==null || "".equals(shopId)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"该店铺不存在");
        }
        map.put("shopId",shopId);
        map.put("page",page);
        map.put("endFlgs",1);
        if(startTime!=null && !"".equals(startTime)){
            startTime += " 00:00:00";
            map.put("createTime",startTime);
        }
        if(endTime!=null && !"".equals(endTime)){
            endTime += " 23:59:59";
            map.put("endTime",endTime);
        }
    }

}


