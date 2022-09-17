//package com.youqiancheng.controller.manager;
//
//import com.handongkeji.constants.StatusConstant;
//import com.handongkeji.util.EntyPage;
//import com.handongkeji.util.manager.ResultVOUtils;
//import com.youqiancheng.mybatis.model.A20PushInfoDO;
//import com.youqiancheng.service.manager.service.A20PushInfoService;
//import com.youqiancheng.util.QueryMap;
//import com.youqiancheng.vo.PageSimpleVO;
//import com.youqiancheng.vo.result.ResultEnum;
//import com.youqiancheng.vo.result.ResultVo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * Author tyf
// * Date  2020-06-17
// */
//@Api(tags = {"手机端-A20_推送记录接口"})
//@RestController
//@RequestMapping(value = "app/a20PushInfo")
//public class A20PushInfoController {
//    @Autowired
//    private A20PushInfoService a20PushInfoService;
//
//    @ApiOperation(value = "获取A20_推送记录列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A20PushInfoDO>> listByPage(@Valid EntyPage page, BindingResult bindingResult) {
//        //参数错误
//        if (bindingResult.hasErrors()) {
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
//        }
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A20PushInfoDO> a20PushInfoList = a20PushInfoService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A20PushInfoDO> a20PushInfoSimpleVO = new PageSimpleVO<>();
//            a20PushInfoSimpleVO.setTotalNumber(page.getTotalNumber());
//            a20PushInfoSimpleVO.setList(a20PushInfoList);
//
//        return ResultVOUtils.success(a20PushInfoSimpleVO);
//    }
//
//    @ApiOperation(value = "获取A20_推送记录列表（无分页，只过滤参数）")
//    @GetMapping("/list")
//    ResultVo list(BindingResult bindingResult) {
//        //参数错误
//        if (bindingResult.hasErrors()) {
//            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
//        }
//        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
//        List<A20PushInfoDO> list = a20PushInfoService.list(map);
//        return ResultVOUtils.success(list);
//    }
//
//    @ApiOperation(value = "根据ID获取A20_推送记录记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A20PushInfoDO a20PushInfo = a20PushInfoService.get(id);
//        return ResultVOUtils.success(a20PushInfo);
//    }
//
//    @ApiOperation(value = "保存单条A20_推送记录记录")
//    @PostMapping()
//    ResultVo insert(@RequestBody A20PushInfoDO a20PushInfo) {
//        int num=a20PushInfoService.insert(a20PushInfo);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A20PushInfoDO> a20PushInfos) {
//        int num=a20PushInfoService.insertBatch(a20PushInfos);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "修改A20_推送记录记录")
//    @PutMapping
//    ResultVo update(@RequestBody A20PushInfoDO a20PushInfo) {
//        int num= a20PushInfoService.update(a20PushInfo);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//
//    @ApiOperation(value = "停用A20_推送记录记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a20PushInfoService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用A20_推送记录记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a20PushInfoService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//
//
//}
