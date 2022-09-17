package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import com.youqiancheng.service.manager.service.A12ServiceAgreementService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"总管理后台-系统设置-服务协议接口"})
@RestController
@RequestMapping(value = "admin/system/a12ServiceAgreement")
public class A12ServiceAgreementController  {
    @Autowired
    private A12ServiceAgreementService a12ServiceAgreementService;

//    @ApiOperation(value = "获取服务协议列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A12ServiceAgreementDO>> listByPage(@Valid EntyPage page) {
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A12ServiceAgreementDO> a12ServiceAgreementList = a12ServiceAgreementService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A12ServiceAgreementDO> a12ServiceAgreementSimpleVO = new PageSimpleVO<>();
//            a12ServiceAgreementSimpleVO.setTotalNumber(page.getTotalNumber());
//            a12ServiceAgreementSimpleVO.setList(a12ServiceAgreementList);
//
//        return ResultVOUtils.success(a12ServiceAgreementSimpleVO);
//    }

    @ApiOperation(value = "获取服务协议列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A12ServiceAgreementDO> list = a12ServiceAgreementService.list(map);
        return ResultVOUtils.success(list);
    }

//    @ApiOperation(value = "根据ID获取服务协议记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A12ServiceAgreementDO a12ServiceAgreement = a12ServiceAgreementService.get(id);
//        return ResultVOUtils.success(a12ServiceAgreement);
//    }

    @ApiOperation(value = "保存单条服务协议记录")
    @PostMapping()
    ResultVo insert(@RequestBody A12ServiceAgreementDO a12ServiceAgreement) {
        a12ServiceAgreement.setUpdateTime(LocalDateTime.now());
        a12ServiceAgreement.setCreateTime(LocalDateTime.now());
        a12ServiceAgreement.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a12ServiceAgreementService.insert(a12ServiceAgreement);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A12ServiceAgreementDO> a12ServiceAgreements) {
//        int num=a12ServiceAgreementService.insertBatch(a12ServiceAgreements);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改服务协议记录")
    @PutMapping
    ResultVo update(@RequestBody A12ServiceAgreementDO a12ServiceAgreement) {
        int num= a12ServiceAgreementService.update(a12ServiceAgreement);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//
//    @ApiOperation(value = "停用服务协议记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a12ServiceAgreementService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用服务协议记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a12ServiceAgreementService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }



}
