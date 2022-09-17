package com.youqiancheng.controller.shop;

import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.shop.C09GoodsSkuSaveForm;
import com.youqiancheng.form.shop.C09GoodsSkuSearchForm;
import com.youqiancheng.form.shop.C09GoodsSkuUpdateForm;
import com.youqiancheng.mybatis.model.C09GoodsSkuDO;
import com.youqiancheng.service.shop.ShopGoodsSkuService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/18 16:26
 * @Version: V1.0
 */

@Api(tags = "商家管理--商品规则")
@RestController
@RequestMapping("shop/goodsSku")
public class ShopGoodsSkuController {

    @Resource
    private ShopGoodsSkuService shopGoodsSkuService;


    @ApiOperation(value = "商品规则列表", notes = "商品规则列表")
    @PostMapping(value = "/pageGoodsSku")
    public ResultVo pageGoodsSku(@Valid C09GoodsSkuSearchForm form , @Valid EntyPage page ){

        QueryMap map=new QueryMap(form,page,StatusConstant.CreatFlag.delete.getCode());
        List<C09GoodsSkuDO> goodsSkulist = shopGoodsSkuService.listGoodsSkuHDPage(map);
        //封装到分页
        PageSimpleVO<C09GoodsSkuDO> shopGoodsSkuPageSimpleVO = new PageSimpleVO<>();
        shopGoodsSkuPageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopGoodsSkuPageSimpleVO.setList(goodsSkulist);
        return ResultVOUtils.success(shopGoodsSkuPageSimpleVO);
    }
       @ApiOperation(value = "添加商品规则", notes = "添加商品规则")
    @PostMapping(value = "/addGoodsSku")
    public ResultVo addGoodsSku(@Valid @RequestBody List<C09GoodsSkuSaveForm> list){
        return ResultVOUtils.success(shopGoodsSkuService.save(list));
    }
    @ApiOperation(value = "实体回显——禁止使用")
    @PostMapping(value = "/displayEntity")
    public ResultVo displayEntity( @RequestBody C09GoodsSkuSaveForm entity){
        System.out.println(entity.getGoodsId());
        return ResultVOUtils.success(entity);
    }

    @ApiOperation(value = "删除商品规则", notes = "删除商品规则")
    @PostMapping(value = "/delGoodsSku")
    public ResultVo delGoodsSku(List<Long> ids){
        return ResultVOUtils.success(shopGoodsSkuService.delete(ids));
    }

    @ApiOperation(value = "编辑商品规则", notes = "删除商品规则")
    @PostMapping(value = "/editGoodsSku")
    public ResultVo editGoodsSku(C09GoodsSkuUpdateForm form){
        C09GoodsSkuDO dto=new C09GoodsSkuDO();
        dto.setId(form.getId());
        dto.setGoodsPrice(form.getGoodsPrice());
        dto.setNum(form.getNum());
        return ResultVOUtils.success(shopGoodsSkuService.edit(dto));
    }
}

