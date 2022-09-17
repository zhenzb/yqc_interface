package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A13SysVersionDO;
import com.youqiancheng.service.manager.service.A13SysVersionService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"总管理后台-系统设置-版本更新接口"})
@RestController
@RequestMapping(value = "admin/system/a13SysVersion")
public class A13SysVersionController  {
    @Autowired
    private A13SysVersionService a13SysVersionService;

    @ApiOperation(value = "获取版本更新列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<A13SysVersionDO>> listByPage(@Valid EntyPage page) {
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        List<A13SysVersionDO> a13SysVersionList = a13SysVersionService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A13SysVersionDO> a13SysVersionSimpleVO = new PageSimpleVO<>();
            a13SysVersionSimpleVO.setTotalNumber(page.getTotalNumber());
            a13SysVersionSimpleVO.setList(a13SysVersionList);

        return ResultVOUtils.success(a13SysVersionSimpleVO);
    }

//    @ApiOperation(value = "获取版本更新列表（无分页，只过滤参数）")
//    @GetMapping("/list")
//    ResultVo list() {
//        Map<String,Object> map =new HashMap<>();
//        List<A13SysVersionDO> list = a13SysVersionService.list(map);
//        return ResultVOUtils.success(list);
//    }

    @ApiOperation(value = "根据ID获取版本更新记录")
    @GetMapping("{id}")
    ResultVo get(@PathVariable("id") Long id ){
        A13SysVersionDO a13SysVersion = a13SysVersionService.get(id);
        return ResultVOUtils.success(a13SysVersion);
    }

    @ApiOperation(value = "保存单条版本更新记录")
    @PostMapping()
    ResultVo insert(@RequestBody A13SysVersionDO a13SysVersion) {
        a13SysVersion.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a13SysVersionService.insert(a13SysVersion);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<A13SysVersionDO> a13SysVersions) {
        int num=a13SysVersionService.insertBatch(a13SysVersions);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改版本更新记录")
    @PutMapping
    ResultVo update(@RequestBody A13SysVersionDO a13SysVersion) {
        int num= a13SysVersionService.update(a13SysVersion);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "删除版本记录")
    @PostMapping("/delete")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= a13SysVersionService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.DELETE_FAIL,"删除失败");
        }
        return ResultVOUtils.success(num);
    }
//
//    @ApiOperation(value = "启用版本更新记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a13SysVersionService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }
//    @ApiOperation(value = "启用版本更新记录")
//    @PutMapping("/start")
//    ResultVo start(@RequestBody List<Long> idList) {
//        int num= a13SysVersionService.start(idList);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.START_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }


}
