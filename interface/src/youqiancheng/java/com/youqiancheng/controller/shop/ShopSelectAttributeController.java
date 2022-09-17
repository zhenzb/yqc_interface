package com.youqiancheng.controller.shop;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.C08SelectAttributeSaveForm;
import com.youqiancheng.form.shop.C08SelectAttributeSearchForm;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import com.youqiancheng.service.shop.ShopSelectAttributeService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/20 11:07
 * @Version: V1.0
 */
@Api(tags = "商家管理--商品管理--商品选择属性")
@RestController
@RequestMapping("shop/selectAttribute")
public class ShopSelectAttributeController {
    @Resource
    private ShopSelectAttributeService shopSelectAttributeService;

    @ApiOperation(value = "商品选择属性列表 ", notes = "商品选择属性列表 ")
    @PostMapping(value = "/pageSelectAttribute")
    public ResultVo pageSelectAttribute(@Valid C08SelectAttributeSearchForm form, @Valid EntyPage page ){

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        List<C08SelectAttributeDO> attributeProjectlist = shopSelectAttributeService.listSelectAttributeHDPage(map);
        //封装到分页
        PageSimpleVO<C08SelectAttributeDO> shopSelectAttributePageSimpleVO = new PageSimpleVO<>();
        shopSelectAttributePageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopSelectAttributePageSimpleVO.setList(attributeProjectlist);
        return ResultVOUtils.success(shopSelectAttributePageSimpleVO);
    }

//    @ApiOperation(value = "商品选择属性列表（不分页）", notes = "商品选择属性列表（不分页）")
//    @PostMapping(value = "/listSelectAttribute")
//    public ResultVo listSelectAttribute(){
//        EntityWrapper<C08SelectAttributeDO> ew = new EntityWrapper<>();
//        ew.eq("delete_flag",StatusConstant.DeleteFlag.un_delete.getCode());
//        return ResultVOUtils.success(shopSelectAttributeService.listSelectAttribute(ew));
//    }

    @ApiOperation(value = "添加商品选择属性", notes = "添加商品选择属性")
    @PostMapping(value = "/addSelectAttribute")
    public ResultVo addSelectAttribute(@Valid   @RequestBody  List<C08SelectAttributeSaveForm> list){
        return ResultVOUtils.success(shopSelectAttributeService.save(list));
    }

    @ApiOperation(value = "实体回显——禁止调用")
    @PostMapping(value = "/displayEntity")
    public ResultVo displayEntity(@RequestBody  C08SelectAttributeSaveForm entity){
        System.out.println(entity.getGoodsId());
        return ResultVOUtils.success(entity);
    }

//    @ApiOperation(value = "删除商品选择属性", notes = "删除商品选择属性")
//    @PostMapping(value = "/delSelectAttribute")
//    public ResultVo delSelectAttribute(@Valid ShopCommonForm shopCommonForm){
//        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
//            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
//        }
//        List<C08SelectAttributeDO> selectAttributeList = new ArrayList<>();
//        for (long id:shopCommonForm.getIds()){
//            C08SelectAttributeDO selectAttribute = shopSelectAttributeService.getSelectAttributeById(id);
//            if (selectAttributeList != null){
//                selectAttribute.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
//                selectAttribute.setUpdateTime(LocalDateTime.now());
//                selectAttributeList.add(selectAttribute);
//            }
//        }
//        return ResultVOUtils.success(shopSelectAttributeService.batchUpdateSelectAttribute(selectAttributeList));
//    }
}

