package com.youqiancheng.controller.manager.shopmanagement;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.shop.MainProjectDeleteForm;
import com.youqiancheng.form.manager.shop.MainProjectEditForm;
import com.youqiancheng.form.manager.shop.MainProjectSaveForm;
import com.youqiancheng.form.manager.shop.MainProjectSearchForm;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
　* @Description: 商家分类管理
　* @author shalongteng
　* @date 2020/4/7 18:41
　*/
@RestController
@RequestMapping("admin/_shop/shopClassify")
@Api(tags = "总管理后台-商家管理-商家分类")
public class ShopClassifyController {
    private static final Log log = LogFactory.getLog(ShopClassifyController.class);

    @Resource
    private ShopManagementService shopManagementService;


    /**
    　* @Description: 获取商家分类列表
    　* @author shalongteng
    　* @date 2020/4/9 9:06
    　*/
    @ApiOperation(value = "获取商家分类列表", notes = "获取商家分类列表")
    @GetMapping("listMainProject")
    public ResultVo<F03MainProjectDO> listMainProject(@Valid MainProjectSearchForm form,@Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<F03MainProjectDO> mainProjectDOList = shopManagementService.listMainProjectHDPage(map);
        //封装到分页
        PageSimpleVO<F03MainProjectDO> a10CarouselPicSimpleVO = new PageSimpleVO<>();
        a10CarouselPicSimpleVO.setTotalNumber(page.getTotalNumber());
        a10CarouselPicSimpleVO.setList(mainProjectDOList);
        return ResultVOUtils.success(a10CarouselPicSimpleVO);
    }

    /**
    　* @Description: 添加商家分类
    　* @author shalongteng
    　* @date 2020/4/9 9:28
    　*/
    @ApiOperation(value = "添加商家分类", notes = "添加商家分类")
    @PostMapping("addMainProject")
    public ResultVo addMainProject(@RequestBody MainProjectSaveForm mainProjectSaveForm) {
        if(mainProjectSaveForm.getStatus().equals(1) || mainProjectSaveForm.getStatus().equals(2)){
            shopManagementService.addMainProject(mainProjectSaveForm);
        }else {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        return ResultVOUtils.success();
    }
    /**
    　* @Description: 编辑商家分类
    　* @author shalongteng
    　* @date 2020/4/13 13:30
    　*/
    @ApiOperation(value = "编辑商家分类", notes = "编辑商家分类")
    @PostMapping("editMainProject")
    public ResultVo editMainProject(@RequestBody MainProjectEditForm mainProjectEditForm) {
        if(mainProjectEditForm.getStatus().equals(1) || mainProjectEditForm.getStatus().equals(2)){
            shopManagementService.editMainProject(mainProjectEditForm);
        }else {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }

        return ResultVOUtils.success();
    }
    /**
    　* @Description: 批量删除商家分类
    　* @author shalongteng
    　* @date 2020/4/13 15:19
    　*/
    @ApiOperation(value = "批量删除商家分类", notes = "批量删除商家分类")
    @PostMapping("deleteMainProject")
    public ResultVo deleteMainProject(@RequestBody List<MainProjectDeleteForm>  mainProjectDeleteFormList) {
        if(mainProjectDeleteFormList == null || mainProjectDeleteFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = shopManagementService.deleteMainProject(mainProjectDeleteFormList);
            if(integer >= 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
    }

    @ApiOperation(value = "批量停用")
    @PostMapping("/stopMainProject")
    ResultVo stop(@RequestBody List<Long> idList) {
        int num= shopManagementService.stop(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.STOP_FAIL);
        }
        return ResultVOUtils.success(num);
    }

    @ApiOperation(value = "批量启用")
    @PostMapping("/startMainProject")
    ResultVo start(@RequestBody List<Long> idList) {
        int num= shopManagementService.start(idList);
        if(num<=0){
        return ResultVOUtils.error(ResultEnum.START_FAIL);
        }
        return ResultVOUtils.success(num);
    }

}
