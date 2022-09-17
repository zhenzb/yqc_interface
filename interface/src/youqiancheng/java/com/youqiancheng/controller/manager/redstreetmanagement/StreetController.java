package com.youqiancheng.controller.manager.redstreetmanagement;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.ability.ShopAbility;
import com.youqiancheng.form.manager.redstreet.RuleSaveEditForm;
import com.youqiancheng.form.manager.redstreet.StreetSaveForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.service.manager.service.redstreetmanagement.StreetService;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
　* @Description: 红包街管理
　* @author shalongteng
　* @date 2020/4/17 18:41
　*/
@RestController
@RequestMapping("admin/_redstreet/street")
@Api(tags = "总管理后台-红包街管理")
public class StreetController {
    private static final Log log = LogFactory.getLog(StreetController.class);

    @Resource
    private StreetService streetService;
    @Resource
    private F01ShopClientService f01ShopClientService;

    @Resource
    private ShopAbility shopAbility;
//
//    /**
//     　* @Description: 获取红包街分类树形列表信息
//     　* @author shalongteng
//     　* @date 2020/4/1 11:36
//     　*/
//    @GetMapping("/getStreetList")
//    @ApiOperation(value = "获取红包街分类列表信息")
//    public ResultVo  treeList() {
//        Map<String, List<E01RedenvelopesStreetDO>> treeMap = streetService.getTree();
//        return ResultVOUtils.success(treeMap);
//    }
    /**
     　* @Description: 获取国家list
     　* @author shalongteng
     　* @date 2020/4/1 11:36
     　*/
    @GetMapping("/getCountryList")
    @ApiOperation(value = "获取国家list")
    public ResultVo<List<C03CategoryDO>> getCountryList() {
        List<C03CategoryDO> c03CategoryDOList= streetService.getCountryList();
        return ResultVOUtils.success(c03CategoryDOList);
    }
    /**
     　* @Description: 根据国家id，查询红包街list
     　* @author shalongteng
     　* @date 2020/4/1 11:36
     　*/
    @GetMapping("/getStreetList")
    @ApiOperation(value = "根据国家id，查询红包街list")
    public ResultVo<List<E01RedenvelopesStreetDO>>  getStreetList(@ApiParam(name = "id", value = "id", required = true)
                                                                      @RequestParam Long id
            ,@Valid EntyPage page ) {
        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("categoryId",id);
        List<E01RedenvelopesStreetDO> streetDOList= streetService.getStreetList1(map);
        //封装到分页
        PageSimpleVO<E01RedenvelopesStreetDO> vo = new PageSimpleVO<>();
        vo.setTotalNumber(page.getTotalNumber());
        vo.setList(streetDOList);

        return ResultVOUtils.success(vo);
    }

    /**
     　* @Description: 添加红包街
     　* @author shalongteng
     　* @date 2020/4/18 15:54
     　*/
    @ApiOperation(value = "添加/编辑红包街", notes = "添加/编辑红包街")
    @PostMapping("addStreet")
    public ResultVo addOrEditAudit(@RequestBody StreetSaveForm streetSaveForm) {
        if(streetSaveForm.getStatus().equals(StatusConstant.enable.getCode()) ||
                streetSaveForm.getStatus().equals(StatusConstant.disable.getCode())){
            streetService.addStreet(streetSaveForm);
        }else {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        return ResultVOUtils.success();
    }

    /**
     　* @Description: 批量启用/禁用/删除
     　* @author shalongteng
     　* @date 2020/4/18 14:41
     　*/
    @ApiOperation(value = "批量启用/禁用/删除", notes = "批量启用/禁用/删除")
    @PostMapping("batchPassRefuse")
    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = streetService.batchPassRefuse(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }

    /**
     　* @Description: 添加/修改红包规则
     　* @author shalongteng
     　* @date 2020/4/13 15:54
     　*/
    @ApiOperation(value = "添加/修改红包规则", notes = "添加/修改红包规则")
    @PostMapping("addOrEditAudit")
    public ResultVo addOrEditAudit(@RequestBody RuleSaveEditForm ruleSaveEditForm) {
        streetService.addOrEditAudit(ruleSaveEditForm);
        return ResultVOUtils.success();
    }

    /**
     　* @Description:查询规则设置
     　* @author shalongteng
     　* @date 2020/4/14 15:04
     　*/
    @ApiOperation(value = "查询规则设置", notes = "查询规则设置")
    @PostMapping("getAudit")
    public ResultVo getAudit() {
        E03RedenvelopesRuleDO audit = streetService.getAudit();

        return ResultVOUtils.success(audit);
    }


    /**
     * @api {GET} /admin/_redstreet/street/getReadGrantRecord 002总后台商家上街历史接口
     * @apiGroup 008 2022-04新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 上街历史接口
     * @apiHeader {String} token 用户token [必填] 登录接口获取
     * @apiParam {String}  shopName 店铺名称 [非必填]
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
    @ApiOperation(value = "总后台查询上街记录", notes = "查询上街记录")
    @GetMapping("getReadGrantRecord")
    public ResultVo getReadGrantRecord(String shopName,String startTime,String endTime,@Valid EntyPage page){
        Map<String, Object> map = new HashMap<>();
        checkParams(shopName, startTime, endTime, page, map);
        PageSimpleVO<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecordDO = shopAbility.getE04RedenvelopesGrantRecordDOPageSimpleVO(page, map);
        return ResultVOUtils.success(e04RedenvelopesGrantRecordDO);
    }

    private void checkParams(String shopName, String startTime, String endTime, @Valid EntyPage page, Map<String, Object> map) {
        if(shopName !=null && !"".equals(shopName)){
            Map<String, Object> shopMap = new HashMap<>();
            shopMap.put("name",shopName);
            List<F01ShopDO> list = f01ShopClientService.list(shopMap);
            if(list.size()==0){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"该店铺不存在");
            }
            map.put("shopId",list.get(0).getId());
        }
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
