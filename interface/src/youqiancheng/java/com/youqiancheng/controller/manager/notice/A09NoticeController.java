package com.youqiancheng.controller.manager.notice;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.A09NoticeDO;
import com.youqiancheng.service.manager.service.notice.A09NoticeService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
　* @Description: 公告相关
　* @author yutf
　* @date 2020/3/30 10:58
　*/
@RestController
@RequestMapping("admin/system/notice")
@Api(tags = "总管理后台-消息管理-公告管理")
public class A09NoticeController {
    @Resource
    private A09NoticeService a09NoticeService;


    /**
    　* @Description: 获取公告列表
    　* @author yutf
    　* @date 2020/4/2 13:54
    　*/
    @ApiOperation(value = "获取公告列表", notes = "获取公告列表")
    @GetMapping("getNoticeList")
    public ResultVo<PageSimpleVO<A09NoticeDO>> getNoticeList(@Valid EntyPage page) {
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        //map.put("page", page);
        List<A09NoticeDO> a09NoticeDOList = a09NoticeService.listNoticePage(map);
        //封装到分页
        PageSimpleVO<A09NoticeDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(a09NoticeDOList);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }

    /**
    　* @Description: 新增公告
    　* @author yutf
    　* @date 2020/4/2 14:48
    　*/
    @ApiOperation(value = "新增公告", notes = "新增公告")
    @GetMapping("save")
    public ResultVo save(@Valid A09NoticeDO a09NoticeDO) {
        a09NoticeDO.setCreateTime(LocalDateTime.now());
        a09NoticeDO.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        a09NoticeDO.setStatus(StatusConstant.ReleaseStatus.un_release.getCode());
        int num= a09NoticeService.save(a09NoticeDO);
       if(num<=0){
           return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
       }
        return ResultVOUtils.success(num);
    }

    /**
    　* @Description: 修改公告
    　* @author yutf
    　* @date 2020/4/2 14:48
    　*/
    @ApiOperation(value = "修改公告", notes = "修改公告")
    @GetMapping("update")
    public ResultVo update(@Valid A09NoticeDO a09NoticeDO) {
        int num= a09NoticeService.update(a09NoticeDO);
       if(num<=0){
           return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
       }
        return ResultVOUtils.success(num);
    }



    /**
    　* @Description: 删除公告
    　* @author yutf
    　* @date 2020/4/2 16:24
    　*/
    @ApiOperation(value = "删除公告", notes = "删除公告")
    @PostMapping("deleteNotice")
    public ResultVo deleteNotice(@RequestBody List<Long> idList) {

        int num= a09NoticeService.deleteBatch(idList);

        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
        }

        return ResultVOUtils.success();
    }


    @ApiOperation(value = "发布公告")
    @PutMapping("/publish")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= a09NoticeService.release(idList);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.PUBLIC_FAIL);
        }
        return ResultVOUtils.success(num);
    }
}
