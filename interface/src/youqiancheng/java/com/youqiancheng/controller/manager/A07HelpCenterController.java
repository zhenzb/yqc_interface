package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A07HelpCenterDO;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import com.youqiancheng.service.app.service.bootpage.A12ServiceAgreementAppService;
import com.youqiancheng.service.manager.service.A07HelpCenterService;
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
@Api(tags = {"总管理后台-系统设置-帮助中心接口"})
@RestController
@RequestMapping(value = "admin/system/a07HelpCenter")
public class A07HelpCenterController  {
    @Autowired
    private A07HelpCenterService a07HelpCenterService;
    @Autowired
    private A12ServiceAgreementAppService a12ServiceAgreementService;

//    @ApiOperation(value = "获取帮助中心列表（分页+过滤参数）")
//    @GetMapping
//    ResultVo<PageSimpleVO<A07HelpCenterDO>> listByPage(@Valid EntyPage page) {
//        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
//        List<A07HelpCenterDO> a07HelpCenterList = a07HelpCenterService.listHDPage(map);
//        //封装到分页
//        PageSimpleVO<A07HelpCenterDO> a07HelpCenterSimpleVO = new PageSimpleVO<>();
//            a07HelpCenterSimpleVO.setTotalNumber(page.getTotalNumber());
//            a07HelpCenterSimpleVO.setList(a07HelpCenterList);
//
//        return ResultVOUtils.success(a07HelpCenterSimpleVO);
//    }

    @ApiOperation(value = "获取帮助中心列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A07HelpCenterDO> list = a07HelpCenterService.list(map);
        return ResultVOUtils.success(list);
    }

//    @ApiOperation(value = "根据ID获取帮助中心记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A07HelpCenterDO a07HelpCenter = a07HelpCenterService.get(id);
//        return ResultVOUtils.success(a07HelpCenter);
//    }

    @ApiOperation(value = "保存单条帮助中心记录")
    @PostMapping()
    ResultVo insert(@RequestBody A07HelpCenterDO a07HelpCenter) {
        a07HelpCenter.setCreateTime(LocalDateTime.now());
        a07HelpCenter.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a07HelpCenterService.insert(a07HelpCenter);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }
//
//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A07HelpCenterDO> a07HelpCenters) {
//        int num=a07HelpCenterService.insertBatch(a07HelpCenters);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改帮助中心记录")
    @PutMapping
    ResultVo update(@RequestBody A07HelpCenterDO a07HelpCenter) {
        int num= a07HelpCenterService.update(a07HelpCenter);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


//
//    @ApiOperation(value = "停用帮助中心记录")
//    @PutMapping("/stop")
//    ResultVo stop(@RequestBody List<Long> idList) {
//        int num= a07HelpCenterService.stop(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//
//    @ApiOperation(value = "启用帮助中心记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a07HelpCenterService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//


}
