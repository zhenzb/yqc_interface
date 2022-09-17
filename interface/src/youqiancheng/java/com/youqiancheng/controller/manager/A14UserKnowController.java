package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A14UserKnowDO;
import com.youqiancheng.service.manager.service.A14UserKnowService;
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
@Api(tags = {"总管理后台-系统设置-用户须知接口"})
@RestController
@RequestMapping(value = "admin/system/a14UserKnow")
public class A14UserKnowController  {
    @Autowired
    private A14UserKnowService a14UserKnowService;

//    @ApiOperation(value = "获取用户须知列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A14UserKnowDO>> listByPage(@Valid EntyPage page) {
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A14UserKnowDO> a14UserKnowList = a14UserKnowService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A14UserKnowDO> a14UserKnowSimpleVO = new PageSimpleVO<>();
//            a14UserKnowSimpleVO.setTotalNumber(page.getTotalNumber());
//            a14UserKnowSimpleVO.setList(a14UserKnowList);
//
//        return ResultVOUtils.success(a14UserKnowSimpleVO);
//    }

    @ApiOperation(value = "获取用户须知列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A14UserKnowDO> list = a14UserKnowService.list(map);
        return ResultVOUtils.success(list);
    }

//    @ApiOperation(value = "根据ID获取用户须知记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A14UserKnowDO a14UserKnow = a14UserKnowService.get(id);
//        return ResultVOUtils.success(a14UserKnow);
//    }

    @ApiOperation(value = "保存单条用户须知记录")
    @PostMapping()
    ResultVo insert(@RequestBody A14UserKnowDO a14UserKnow) {
        a14UserKnow.setCreateTime(LocalDateTime.now());
        a14UserKnow.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a14UserKnowService.insert(a14UserKnow);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A14UserKnowDO> a14UserKnows) {
//        int num=a14UserKnowService.insertBatch(a14UserKnows);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改用户须知记录")
    @PutMapping
    ResultVo update(@RequestBody A14UserKnowDO a14UserKnow) {
        int num= a14UserKnowService.update(a14UserKnow);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


//    @ApiOperation(value = "停用用户须知记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a14UserKnowService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用用户须知记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a14UserKnowService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }



}
