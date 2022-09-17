package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.A10CarouselPicSearchForm;
import com.youqiancheng.mybatis.model.A10CarouselPicDO;
import com.youqiancheng.service.manager.service.A10CarouselPicService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = {"总管理后台-轮播图管理"})
@RestController
@RequestMapping(value = "admin/system/a10CarouselPic")
public class A10CarouselPicController  {
    @Autowired
    private A10CarouselPicService a10CarouselPicService;

    @ApiOperation(value = "获取轮播图列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<A10CarouselPicDO>> listByPage(@Valid A10CarouselPicSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<A10CarouselPicDO> a10CarouselPicList = a10CarouselPicService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A10CarouselPicDO> a10CarouselPicSimpleVO = new PageSimpleVO<>();
            a10CarouselPicSimpleVO.setTotalNumber(page.getTotalNumber());
            a10CarouselPicSimpleVO.setList(a10CarouselPicList);

        return ResultVOUtils.success(a10CarouselPicSimpleVO);
    }

    @ApiOperation(value = "获取轮播图列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A10CarouselPicDO> list = a10CarouselPicService.list(map);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据ID获取轮播图记录")
    @GetMapping("{id}")
    ResultVo get(@PathVariable("id") Long id ){
        A10CarouselPicDO a10CarouselPic = a10CarouselPicService.get(id);
        return ResultVOUtils.success(a10CarouselPic);
    }

    @ApiOperation(value = "保存单条轮播图记录")
    @PostMapping()
    ResultVo insert(@RequestBody A10CarouselPicDO a10CarouselPic) {
        a10CarouselPic.setUpdateTime(LocalDateTime.now());
        a10CarouselPic.setCreateTime(LocalDateTime.now());
        a10CarouselPic.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a10CarouselPicService.insert(a10CarouselPic);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<A10CarouselPicDO> a10CarouselPics) {
        int num=a10CarouselPicService.insertBatch(a10CarouselPics);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改轮播图记录")
    @PutMapping
    ResultVo update(@RequestBody A10CarouselPicDO a10CarouselPic) {
        int num= a10CarouselPicService.update(a10CarouselPic);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用轮播图记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= a10CarouselPicService.stop(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用轮播图记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= a10CarouselPicService.start(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "删除")
    @PutMapping("/delete")
    ResultVo delete(@RequestBody List<Long> idList) {
        int num= a10CarouselPicService.delete(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


}
