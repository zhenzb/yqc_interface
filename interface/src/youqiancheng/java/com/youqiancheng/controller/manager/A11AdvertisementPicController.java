package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.A11AdvertisementPicSearchForm;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import com.youqiancheng.service.manager.service.A11AdvertisementPicService;
import com.youqiancheng.service.manager.service.InitDataService;
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
@Api(tags = {"总管理后台-广告图管理"})
@RestController
@RequestMapping(value = "admin/system/a11AdvertisementPic")
public class A11AdvertisementPicController  {
    @Autowired
    private A11AdvertisementPicService a11AdvertisementPicService;
    @Autowired
    private InitDataService initDataService;

    @ApiOperation(value = "获取广告图列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<A11AdvertisementPicDO>> listByPage(@Valid A11AdvertisementPicSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<A11AdvertisementPicDO> a11AdvertisementPicList = a11AdvertisementPicService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A11AdvertisementPicDO> a11AdvertisementPicSimpleVO = new PageSimpleVO<>();
            a11AdvertisementPicSimpleVO.setTotalNumber(page.getTotalNumber());
            a11AdvertisementPicSimpleVO.setList(a11AdvertisementPicList);
        //获取证照类型字典
        return ResultVOUtils.success(a11AdvertisementPicSimpleVO);
    }

    @ApiOperation(value = "获取广告图列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list() {
        Map<String,Object> map =new HashMap<>();
        List<A11AdvertisementPicDO> list = a11AdvertisementPicService.list(map);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据ID获取广告图记录")
    @GetMapping("{id}")
    ResultVo get(@PathVariable("id") Long id ){
        A11AdvertisementPicDO a11AdvertisementPic = a11AdvertisementPicService.get(id);
        return ResultVOUtils.success(a11AdvertisementPic);
    }

    @ApiOperation(value = "保存单条广告图记录")
    @PostMapping()
    ResultVo insert(@RequestBody A11AdvertisementPicDO a11AdvertisementPic) {
        a11AdvertisementPic.setUpdateTime(LocalDateTime.now());
        a11AdvertisementPic.setCreateTime(LocalDateTime.now());
        a11AdvertisementPic.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=a11AdvertisementPicService.insert(a11AdvertisementPic);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<A11AdvertisementPicDO> a11AdvertisementPics) {
        int num=a11AdvertisementPicService.insertBatch(a11AdvertisementPics);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改广告图记录")
    @PutMapping
    ResultVo update(@RequestBody A11AdvertisementPicDO a11AdvertisementPic) {
        a11AdvertisementPic.setUpdateTime(LocalDateTime.now());
        int num= a11AdvertisementPicService.update(a11AdvertisementPic);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用广告图记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= a11AdvertisementPicService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用广告图记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= a11AdvertisementPicService.start(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "删除广告图记录")
    @PostMapping("/delete")
    ResultVo delete(@RequestBody List<Long> idList) {
        int num= a11AdvertisementPicService.delete(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
        }
        return ResultVOUtils.success(num);
    }

}
