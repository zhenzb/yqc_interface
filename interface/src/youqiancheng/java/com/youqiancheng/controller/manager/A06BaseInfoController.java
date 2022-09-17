package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import com.youqiancheng.service.manager.service.A06BaseInfoService;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"总管理后台-系统设置-基本信息接口"})
@RestController
@RequestMapping(value = "admin/system/a06BaseInfo")
public class A06BaseInfoController  {
    @Autowired
    private A06BaseInfoService a06BaseInfoService;

//    @ApiOperation(value = "获取本信息列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A06BaseInfoDO>> listByPage(@Valid EntyPage page) {
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A06BaseInfoDO> a06BaseInfoList = a06BaseInfoService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A06BaseInfoDO> a06BaseInfoSimpleVO = new PageSimpleVO<>();
//            a06BaseInfoSimpleVO.setTotalNumber(page.getTotalNumber());
//            a06BaseInfoSimpleVO.setList(a06BaseInfoList);
//
//        return ResultVOUtils.success(a06BaseInfoSimpleVO);
//    }

    @ApiOperation(value = "获取基本信息列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A06BaseInfoDO> list = a06BaseInfoService.list(map);
        return ResultVOUtils.success(list);
    }

//    @ApiOperation(value = "根据ID获取基本信息记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A06BaseInfoDO a06BaseInfo = a06BaseInfoService.get(id);
//        return ResultVOUtils.success(a06BaseInfo);
//    }

    @ApiOperation(value = "保存单条基本信息记录")
    @PostMapping()
    ResultVo insert(@RequestBody A06BaseInfoDO a06BaseInfo) {
        a06BaseInfo.setCreateTime(LocalDateTime.now());
        a06BaseInfo.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a06BaseInfoService.insert(a06BaseInfo);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }
//
//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A06BaseInfoDO> a06BaseInfos) {
//        int num=a06BaseInfoService.insertBatch(a06BaseInfos);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改基本信息记录")
    @PutMapping
    ResultVo update(@RequestBody A06BaseInfoDO a06BaseInfo) {
        int num= a06BaseInfoService.update(a06BaseInfo);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//
//    @ApiOperation(value = "停用基本信息记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a06BaseInfoService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用基本信息记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a06BaseInfoService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//


}
