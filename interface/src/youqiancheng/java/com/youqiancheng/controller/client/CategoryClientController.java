package com.youqiancheng.controller.client;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.dao.F16ShopProfitFlowsDao;
import com.youqiancheng.mybatis.model.A09NoticeDO;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import com.youqiancheng.service.client.service.C03CategoryClientService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Api(tags = {"PC端-分类"})
@RestController
@RequestMapping(value = "pc/goodsCategory")
public class CategoryClientController {
    @Autowired
    private C03CategoryClientService c03CategoryClientService;

    @Autowired
    F16ShopProfitFlowsDao f16ShopProfitFlowsDao;
    /**
     　* @Description: 获取商品分类信息
     　* @author yutf
     　* @date 2020/4/2 13:54
     　*/
    @ApiOperation(value = "根据上级分类ID获取下一级分类信息；参数——父类ID")
    @ApiImplicitParam(name ="pid", value ="父类ID",required = true,type = "Long")
    @GetMapping("/getCategoryList")
    public ResultVo<C03CategoryDO> getCategoryList(Long pid) {
        if(pid==null||pid==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"上级分类参数不能为空或者0");
        }
        QueryMap map = new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("parentId",pid);
        map.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        map.put("status",1);
        List<C03CategoryDO> list = c03CategoryClientService.getByParentId(map);
        //封装到分页
//        PageSimpleVO<C03CategoryDO> authAdminPageSimpleVO = new PageSimpleVO<>();
//        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
//        authAdminPageSimpleVO.setList(list);
        return ResultVOUtils.success(list);
    }
    /**
     　* @Description: 获取商品分类信息
     　* @author yutf
     　* @date 2020/4/2 13:54
     　*/
    @ApiOperation(value = "获取商品一级分类信息（一级商品分类即为国家）")
    @GetMapping("/getFistLevelCategory")
    public ResultVo<C03CategoryDO> getFistLevelCategory() {
        List<C03CategoryDO> list = c03CategoryClientService.listFirst();
        return ResultVOUtils.success(list);
    }

}
