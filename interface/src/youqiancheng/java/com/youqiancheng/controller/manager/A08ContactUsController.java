package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A08ContactUsDO;
import com.youqiancheng.service.manager.service.A08ContactUsService;
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
@Api(tags = {"总管理后台-系统设置-联系我们接口"})
@RestController
@RequestMapping(value = "admin/system/a08ContactUs")
public class A08ContactUsController  {
    @Autowired
    private A08ContactUsService a08ContactUsService;

//    @ApiOperation(value = "获取联系我们列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A08ContactUsDO>> listByPage(@Valid EntyPage page) {
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A08ContactUsDO> a08ContactUsList = a08ContactUsService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A08ContactUsDO> a08ContactUsSimpleVO = new PageSimpleVO<>();
//            a08ContactUsSimpleVO.setTotalNumber(page.getTotalNumber());
//            a08ContactUsSimpleVO.setList(a08ContactUsList);
//
//        return ResultVOUtils.success(a08ContactUsSimpleVO);
//    }

    @ApiOperation(value = "获取联系我们列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A08ContactUsDO> list = a08ContactUsService.list(map);
        return ResultVOUtils.success(list);
    }
//
//    @ApiOperation(value = "根据ID获取联系我们记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A08ContactUsDO a08ContactUs = a08ContactUsService.get(id);
//        return ResultVOUtils.success(a08ContactUs);
//    }

    @ApiOperation(value = "保存单条联系我们记录")
    @PostMapping()
    ResultVo insert(@RequestBody A08ContactUsDO a08ContactUs) {
        a08ContactUs.setUpdateTime(LocalDateTime.now());
        a08ContactUs.setCreateTime(LocalDateTime.now());
        a08ContactUs.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a08ContactUsService.insert(a08ContactUs);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A08ContactUsDO> a08ContactUss) {
//        int num=a08ContactUsService.insertBatch(a08ContactUss);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改联系我们记录")
    @PutMapping
    ResultVo update(@RequestBody A08ContactUsDO a08ContactUs) {
        int num= a08ContactUsService.update(a08ContactUs);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//
//    @ApiOperation(value = "停用联系我们记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a08ContactUsService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用联系我们记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a08ContactUsService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }



}
