package com.youqiancheng.controller.client;


import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.Constants;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.model.F18LinksDO;
import com.youqiancheng.service.client.service.F18LinksAppService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Author tyf
 * Date  2021-11-20
 */
@Api(tags = {"手机端-接口"})
@RestController
@RequestMapping(value = "app/f18Links")
public class F18LinksAppController {
    @Autowired
    private F18LinksAppService f18LinksService;

    @ApiOperation(value = "获取列表（分页+过滤参数）")
    @GetMapping
    ResultVo<PageSimpleVO<F18LinksDO>> listByPage(@Valid EntyPage page, BindingResult bindingResult) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryMap map = new QueryMap(page, StatusConstant.CreatFlag.delete.getCode());
        List<F18LinksDO> f18LinksList = f18LinksService.listHDPage(map);
        //封装到分页
        PageSimpleVO<F18LinksDO> f18LinksSimpleVO = new PageSimpleVO<>();
        f18LinksSimpleVO.setTotalNumber(page.getTotalNumber());
        f18LinksSimpleVO.setList(f18LinksList);

        return ResultVOUtils.success(f18LinksSimpleVO);
    }

    @ApiOperation(value = "获取列表（无分页，只过滤参数）")
    @GetMapping("/list")
    ResultVo list(BindingResult bindingResult) {
        //参数错误
        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL, bindingResult.getFieldError().getDefaultMessage());
        }
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        List<F18LinksDO> list = f18LinksService.list(map);
        return ResultVOUtils.success(list);
    }

    @ApiOperation(value = "根据ID获取记录")
    @GetMapping("{id}")
    ResultVo get(@PathVariable("id") Long id) {
        F18LinksDO f18Links = f18LinksService.get(id);
        return ResultVOUtils.success(f18Links);
    }


    /**
     * @api {GET} /app/f18Links/delete 005删除商家网站链接
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 删除商家网站链接
     * @apiParam {Long} id 店铺Id
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 0,           请求成功
     * "message": "success",
     * "data": 1
     * }
     */
    @ApiOperation(value = "根据ID删除记录")
    @PostMapping("/delete")
    ResultVo deleteLink(Long id) {
        int delete = f18LinksService.delete(id);
        return ResultVOUtils.success(delete);
    }

    /**
     * @api {GET} /app/f18Links/saveLinks 004商家端添加网站链接
     * @apiGroup 007 2020新增接口
     * @apiVersion 0.0.1
     * @apiDescription APP- 商家端添加网站链接
     * @apiParam {String} linkName 网站名称
     * @apiParam {String} linkUrl 网址
     * @apiParam {Long} sourceId 店铺Id
     * @apiSuccessExample {json} 返回示例:
     * {
     * "code": 0,           请求成功
     * "message": "success",
     * "data": 1
     * }
     */
    @ApiOperation(value = "保存单条记录")
    @PostMapping(value = "/saveLinks")
    ResultVo insert(F18LinksDO f18Links) {
        if (f18Links == null) {
            throw new JsonException(Constants.$Null, "参数不能为空");
        }
        String[] nameArr = f18Links.getLinkName().split(",");
        String[] linkArr = f18Links.getLinkUrl().split(",");
        List<F18LinksDO> f18LinksList = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < nameArr.length; i++) {
            F18LinksDO f18Link = new F18LinksDO();
            f18Link.setCreateTime(LocalDateTime.now());
            f18Link.setLinkName(nameArr[i]);
            f18Link.setLinkUrl(linkArr[i]);
            f18Link.setSourceId(f18Links.getSourceId());
            f18Link.setIsDelete(1);
            f18LinksList.add(f18Link);
        }
        if (f18LinksList.size() > 0) {
            num = f18LinksService.insertBatch(f18LinksList);
        }
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @PostMapping("/insertBatch")
    ResultVo insertBatch(@RequestBody List<F18LinksDO> f18Linkss) {
        int num = f18LinksService.insertBatch(f18Linkss);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "修改记录")
    @PutMapping
    ResultVo update(@RequestBody F18LinksDO f18Links) {
        int num = f18LinksService.update(f18Links);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "停用记录")
    @PutMapping("/stop")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num = f18LinksService.stop(idList);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "启用记录")
    @PutMapping("/start")
    ResultVo start(@RequestBody List<Long> idList) {
        int num = f18LinksService.start(idList);
        if (num <= 0) {
            return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }


}
