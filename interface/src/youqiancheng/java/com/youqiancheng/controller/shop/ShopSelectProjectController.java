package com.youqiancheng.controller.shop;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.C07SelectProjectSaveForm;
import com.youqiancheng.form.shop.C07SelectProjectSearchForm;
import com.youqiancheng.mybatis.model.C07SelectProjectDO;
import com.youqiancheng.service.shop.ShopSelectProjectService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/20 11:08
 * @Version: V1.0
 */
@Api(tags = "商家管理--商品管理--商品选择属性项")
@RestController
@RequestMapping("shop/selectProject")
public class ShopSelectProjectController {
    @Resource
    private ShopSelectProjectService shopSelectProjectService;

    @ApiOperation(value = "商品选择属性项列表")
    @PostMapping(value = "/pageSelectProject")
    public ResultVo pageSelectProject(@Valid C07SelectProjectSearchForm form, @Valid EntyPage page ){

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        List<C07SelectProjectDO> selectProjectlist = shopSelectProjectService.listSelectProjectHDPage(map);
        //封装到分页
        PageSimpleVO<C07SelectProjectDO> shopSelectProjectPageSimpleVO = new PageSimpleVO<>();
        shopSelectProjectPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopSelectProjectPageSimpleVO.setList(selectProjectlist);
        return ResultVOUtils.success(shopSelectProjectPageSimpleVO);
    }

    @ApiOperation(value = "商品选择属性项列表——带内容")
    @PostMapping(value = "/listSelectProject")
    public ResultVo listSelectProject(@Valid C07SelectProjectSearchForm form  ){

        QueryMap map=new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        return ResultVOUtils.success(shopSelectProjectService.listWithContent(map));
    }


    @ApiOperation(value = "保存商品选择属性项", notes = "保存商品选择属性项")
    @PostMapping(value = "/addlistSelectProject")
    public ResultVo addlistSelectProject(@Valid C07SelectProjectSaveForm form){

        return ResultVOUtils.success(shopSelectProjectService.save(form));
    }
//
//    @ApiOperation(value = "删除商品选择属性项", notes = "删除商品选择属性项")
//    @PostMapping(value = "/dellistSelectProject")
//    public ResultVo dellistSelectProject(@Valid ShopCommonForm shopCommonForm){
//        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        List<C07SelectProjectDO> selectProjectList = new ArrayList<>();
//        for (long id:shopCommonForm.getIds()){
//            C07SelectProjectDO selectProject = shopSelectProjectService.getSelectProjectById(id);
//            if (selectProject != null){
//                selectProject.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
//                selectProject.setUpdateTime(LocalDateTime.now());
//                selectProjectList.add(selectProject);
//            }
//        }
//        return ResultVOUtils.success(shopSelectProjectService.batchUpdateSelectProject(selectProjectList));
//    }
}


