package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.A16SysDictSearchForm;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import com.youqiancheng.service.manager.service.A16SysDictService;
import com.youqiancheng.service.manager.service.InitDataService;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Api(tags = {"总管理后台-系统设置-字典表接口"})
@RestController
@RequestMapping(value = "admin/system/a16SysDict")
public class A16SysDictController {
    @Autowired
    private A16SysDictService a16SysDictService;
    @Autowired
    private InitDataService initDataService;

    @ApiOperation(value = "获取字典表列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<A16SysDictDO>> listByPage(@Valid A16SysDictSearchForm form , @Valid EntyPage page ){

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<A16SysDictDO> a16SysDictList = a16SysDictService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A16SysDictDO> a16SysDictSimpleVO = new PageSimpleVO<>();
            a16SysDictSimpleVO.setTotalNumber(page.getTotalNumber());
            a16SysDictSimpleVO.setList(a16SysDictList);

        return ResultVOUtils.success(a16SysDictSimpleVO);
    }

    @ApiOperation(value = "获取字典表列表")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A16SysDictDO> list = a16SysDictService.list(map);
        return ResultVOUtils.success(list);
    }
//
//    @ApiOperation(value = "根据ID获取字典表记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A16SysDictDO a16SysDict = a16SysDictService.get(id);
//        return ResultVOUtils.success(a16SysDict);
//    }
    @ApiOperation(value = "根据Type获取字典表记录")
    @GetMapping("/getInfoByType/{type}")
    ResultVo get(@PathVariable("type") String type ){
        List<A16SysDictDO> sysDictByType = initDataService.getSysDictByType(type);
        return ResultVOUtils.success(sysDictByType);
    }

    @ApiOperation(value = "保存单条字典表记录")
    @PostMapping()
    ResultVo insert(@RequestBody A16SysDictDO a16SysDict) {
        a16SysDict.setUpdateTime(LocalDateTime.now());
        a16SysDict.setCreateTime(LocalDateTime.now());
        a16SysDict.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a16SysDict.setStatus(StatusConstant.enable.getCode());
        int num=a16SysDictService.insert(a16SysDict);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        //刷新缓存
        initDataService.refreshData();
        return ResultVOUtils.success(num);
    }
//
//    @PostMapping("/insertBatch")
//    @ApiOperation(value = "保存多条字典表记录")
//    ResultVo insertBatch(@RequestBody List<A16SysDictDO> a16SysDicts) {
//        int num=a16SysDictService.insertBatch(a16SysDicts);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改字典表记录")
    @PutMapping
    ResultVo update(@RequestBody A16SysDictDO a16SysDict) {
        int num= a16SysDictService.update(a16SysDict);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        //刷新缓存
        initDataService.refreshData();
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用字典表记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= a16SysDictService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        //刷新缓存
        initDataService.refreshData();
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用字典表记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= a16SysDictService.start(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        //刷新缓存
        initDataService.refreshData();
        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "刷新缓存")
    @PutMapping("/refresh")
    ResultVo refresh() {
        initDataService.refreshData();
        return ResultVOUtils.success();
    }

//    @ApiOperation(value = "删除")
//    @PutMapping("/delete")
//    ResultVo delete() {
//        initDataService.refreshData();
//        return ResultVOUtils.success();
//    }

}
