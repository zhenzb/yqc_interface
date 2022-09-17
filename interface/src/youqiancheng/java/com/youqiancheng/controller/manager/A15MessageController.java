package com.youqiancheng.controller.manager;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.A15MessageSaveForm;
import com.youqiancheng.form.manager.A15MessageSearchForm;
import com.youqiancheng.form.manager.A15MessageUpadteForm;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.service.manager.service.A15MessageService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Api(tags = {"总管理后台-消息管理-消息接口"})
@RestController
@RequestMapping(value = "admin/system/a15Message")
public class A15MessageController {
    @Autowired
    private A15MessageService a15MessageService;

    @ApiOperation(value = "获取消息列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<A15MessageDO>> listByPage(@Valid A15MessageSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<A15MessageDO> a15MessageList = a15MessageService.listHDPage(map);
        //封装到分页
        PageSimpleVO<A15MessageDO> a15MessageSimpleVO = new PageSimpleVO<>();
            a15MessageSimpleVO.setTotalNumber(page.getTotalNumber());
            a15MessageSimpleVO.setList(a15MessageList);

        return ResultVOUtils.success(a15MessageSimpleVO);
    }

//    @ApiOperation(value = "获取消息列表（无分页，只过滤参数）")
//    @GetMapping("/list")
//    ResultVo list() {
//        Map<String,Object> map =new HashMap<>();
//        List<A15MessageDO> list = a15MessageService.list(map);
//        return ResultVOUtils.success(list);
//    }

//    @ApiOperation(value = "根据ID获取消息记录")
//    @GetMapping("{id}")
//    ResultVo get(@PathVariable("id") Long id ){
//        A15MessageDO a15Message = a15MessageService.get(id);
//        return ResultVOUtils.success(a15Message);
//    }

    @ApiOperation(value = "保存单条消息记录")
    @PostMapping()
    ResultVo insert(@RequestBody A15MessageSaveForm dto) {
        A15MessageDO a15Message=new A15MessageDO();
        BeanUtils.copyProperties(dto,a15Message);
        a15Message.setUpdateTime(LocalDateTime.now());
        a15Message.setCreateTime(LocalDateTime.now()  );
        a15Message.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a15Message.setStatus(StatusConstant.ReleaseStatus.un_release.getCode());
        //a15Message.setType(StatusConstant.MessageType.platform_msg.getCode());
        int num=a15MessageService.insert(a15Message);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

//    @PostMapping("/insertBatch")
//    ResultVo insertBatch(@RequestBody List<A15MessageDO> a15Messages) {
//        int num=a15MessageService.insertBatch(a15Messages);
//        if(num<=0){
//        return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
//        }
//        return ResultVOUtils.success(num);
//    }

    @ApiOperation(value = "修改消息记录")
    @PutMapping
    ResultVo update(@RequestBody A15MessageUpadteForm form) {
        A15MessageDO a15Message=new A15MessageDO();
        BeanUtils.copyProperties(form,a15Message);
        a15Message.setUpdateTime(LocalDateTime.now());
        int num= a15MessageService.update(a15Message);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "删除消息记录")
    @PutMapping("/delete")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= a15MessageService.stop(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
        }
        return ResultVOUtils.success(num);
    }
//
    @ApiOperation(value = "发布消息")
    @PutMapping("/publish")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= a15MessageService.release(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.PUBLIC_FAIL);
        }
        return ResultVOUtils.success(num);
    }



}
