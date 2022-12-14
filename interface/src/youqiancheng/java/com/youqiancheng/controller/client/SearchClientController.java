package com.youqiancheng.controller.client;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.DictTypeConstants;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.client.C01GoodsAppSearchForm;
import com.youqiancheng.form.client.F01ShopAppSearchForm;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import com.youqiancheng.service.client.service.C01GoodsClientService;
import com.youqiancheng.service.client.service.F01ShopClientService;
import com.youqiancheng.service.manager.service.InitDataService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Api(tags = {"PC端-搜索页"})
@RestController
@RequestMapping(value = "pc/search")
public class SearchClientController {
    @Autowired
    private F01ShopClientService f01ShopService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private C01GoodsClientService c01GoodsService;

    @ApiOperation(value = "搜索——获取商品表列表；参数——名称，类型，排序，三级分类——非必填")
    @GetMapping("/goods")
    ResultVo<PageSimpleVO<C01GoodsDO>> search(@Valid C01GoodsAppSearchForm form , @Valid EntyPage page ) {

        if(form.getOrderKey()!=0){
            if(StringUtils.isBlank(form.getOrderType())){
                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"排序不能为空;降序desc或者升序asc");
            }else{
                if(form.getOrderType().equals("desc")){
                    form.setOrderType("desc");
                }else{
                    form.setOrderType("asc");
                }
            }
        }
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<C01GoodsDO> c01GoodsList = c01GoodsService.listHDPageWithOrder(map);
        //封装到分页
        PageSimpleVO<C01GoodsDO> c01GoodsSimpleVO = new PageSimpleVO<>();
        c01GoodsSimpleVO.setTotalNumber(page.getTotalNumber());
        c01GoodsSimpleVO.setList(c01GoodsList);

        return ResultVOUtils.success(c01GoodsSimpleVO);
    }

    @ApiOperation(value = "搜索——获取商家信息；参数——省市区类型")
    @GetMapping("/shop")
    ResultVo<PageSimpleVO<F01ShopDO>> search(@Valid F01ShopAppSearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        List<F01ShopDO> f01ShopList = f01ShopService.listHDPage(map);
        //封装到分页
        PageSimpleVO<F01ShopDO> f01ShopSimpleVO = new PageSimpleVO<>();
        f01ShopSimpleVO.setTotalNumber(page.getTotalNumber());
        f01ShopSimpleVO.setList(f01ShopList);

        return ResultVOUtils.success(f01ShopSimpleVO);
    }

    @ApiOperation(value = "返回商家类型")
    @GetMapping("/getShopType")
    ResultVo getShopType() {
        List<A16SysDictDO> sysDictByType = initDataService.getSysDictByType(DictTypeConstants.SHOP_TYPE);
        return ResultVOUtils.success(sysDictByType);
    }
}
