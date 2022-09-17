package com.youqiancheng.controller.manager.publicitymanagement;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.C10PublicityUpdateDO;
import com.youqiancheng.form.manager.publicity.PublicityQueryForm;
import com.youqiancheng.form.manager.shop.ShopPassRefuseForm;
import com.youqiancheng.form.shop.otther.ShopGoodsPageForm;
import com.youqiancheng.mybatis.dao.C01GoodsDao;
import com.youqiancheng.mybatis.dao.C02GoodsPicDao;
import com.youqiancheng.mybatis.model.C01GoodsDO;
import com.youqiancheng.mybatis.model.C02GoodsPicDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.service.manager.service.publicitymanagement.PublicityService;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
　* @Description: 宣传管理
　* @author shalongteng
　* @date 2020/4/17 18:41
　*/
@RestController
@RequestMapping("admin/_publicity/publicity")
@Api(tags = "总管理后台-宣传管理")
public class PublicityController {
    private static final Log log = LogFactory.getLog(PublicityController.class);

    @Resource
    private PublicityService publicityService;
    @Resource
    private ShopOrderService shopOrderService;
    @Resource
    private GoodsListService goodsListService;
    @Resource
    private C02GoodsPicDao c02GoodsPicDao;
    @Resource
    private C01GoodsDao c01GoodsDao;

    /**
    　* @Description: 获取宣传列表
    　* @author shalongteng
    　* @date 2020/4/8 9:11
    　*/
    @ApiOperation(value = "获取宣传列表", notes = "获取宣传列表")
    @GetMapping("listOrder")
    public ResultVo<PageSimpleVO<C10PublicityDO>> listShop(@Valid PublicityQueryForm publicityQueryForm   ,
                                                           @Valid EntyPage page) {

        EntyPage page1 = new EntyPage();
        page1.setPageSize(10000);
        page1.setCurrentPage(1);
        Map<String, Object> map = new HashMap<>();
        map.put("shopPublicityPageForm", publicityQueryForm);
        map.put("page", page1);
        List<C10PublicityDO> authAdminList = publicityService.listPublicityHDPage(map);
        //查询商品中的社交圈
        ShopGoodsPageForm shopGoodsPageForm= new ShopGoodsPageForm();
        shopGoodsPageForm.setTypes(3);
        if( 0!=publicityQueryForm.getExamineStatus()){
            shopGoodsPageForm.setGoodsStatus(publicityQueryForm.getExamineStatus());
        }
        map.put("shopGoodsPageForm", shopGoodsPageForm);
        map.put("page", page1);
        List<C01GoodsDO> c01GoodsDOList = goodsListService.listGoodsHDPage(map);
        if(c01GoodsDOList.size()>0){
            for(C01GoodsDO c01GoodsDO:c01GoodsDOList){
                C10PublicityDO c10PublicityDO = new C10PublicityDO();
                Map<String,Object> goodsPicMap = new HashMap<>();
                goodsPicMap.put("goodsId",c01GoodsDO.getId());
                goodsPicMap.put("deleteFlag",1);
                List<C02GoodsPicDO> list = c02GoodsPicDao.list(goodsPicMap);
                List<String> collect = list.stream().map(C02GoodsPicDO::getPicUrl).collect(Collectors.toList());
                String[] strings = new String[collect.size()];
                c10PublicityDO.setPicArr(collect.toArray(strings));
                c10PublicityDO.setId(c01GoodsDO.getId());
                c10PublicityDO.setShopName(c01GoodsDO.getShopName());
                c10PublicityDO.setExamineStatus(c01GoodsDO.getExamineStatus());
                c10PublicityDO.setCreateTime(c01GoodsDO.getCreateTime());
                c10PublicityDO.setTitle(c01GoodsDO.getIntroduction());
                c10PublicityDO.setType(Integer.valueOf(c01GoodsDO.getType()));
                authAdminList.add(c10PublicityDO);
            }
        }
        //按添加时间 倒叙排序
        authAdminList.sort(Comparator.comparing(C10PublicityDO::getCreateTime).reversed());
        List<C10PublicityDO> pageResult = new ArrayList<>();
        if(authAdminList.size()>0){
            if((page.getPageSize()*(page.getCurrentPage()-1))<=authAdminList.size()){
                List<C10PublicityDO> c10PublicityDOS = authAdminList.subList(page.getPageSize() * (page.getCurrentPage() - 1),
                        ((page.getPageSize() * page.getCurrentPage()) > authAdminList.size() ? authAdminList.size() : (page.getPageSize() * page.getCurrentPage())));
                pageResult.addAll(c10PublicityDOS);
            }
        }
        //封装到分页
        PageSimpleVO<C10PublicityDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page1.getTotalNumber());
        authAdminPageSimpleVO.setList(pageResult);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }

    /**
     　* @Description: 获取宣传列表
     　* @author shalongteng
     　* @date 2020/4/8 9:11
     　*/
    @ApiOperation(value = "获取宣传详情", notes = "获取宣传详情")
    @GetMapping("publicityDetail")
    public ResultVo<C10PublicityDO> publicityDetail(@Valid PublicityQueryForm publicityQueryForm   ,
                                                           @Valid EntyPage page) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopPublicityPageForm", publicityQueryForm);
        map.put("page", page);
        C10PublicityDO authAdminList = publicityService.get(publicityQueryForm.getGoodsId());
        if(authAdminList == null){
            C01GoodsDO c01GoodsDO = c01GoodsDao.get(publicityQueryForm.getGoodsId());
            if(c01GoodsDO ==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"非法请求！");
            }
            C10PublicityDO c10PublicityDO = new C10PublicityDO();
            Map<String,Object> goodsPicMap = new HashMap<>();
            goodsPicMap.put("goodsId",c01GoodsDO.getId());
            goodsPicMap.put("deleteFlag",1);
            List<C02GoodsPicDO> list = c02GoodsPicDao.list(goodsPicMap);
            List<String> collect = list.stream().map(C02GoodsPicDO::getPicUrl).collect(Collectors.toList());
            String[] strings = new String[collect.size()];
            c10PublicityDO.setPicArr(collect.toArray(strings));
            c10PublicityDO.setId(c01GoodsDO.getId());
            c10PublicityDO.setShopName(c01GoodsDO.getShopName());
            c10PublicityDO.setExamineStatus(c01GoodsDO.getExamineStatus());
            c10PublicityDO.setCreateTime(c01GoodsDO.getCreateTime());
            c10PublicityDO.setTitle(c01GoodsDO.getIntroduction());
            c10PublicityDO.setType(Integer.valueOf(c01GoodsDO.getType()));
            return ResultVOUtils.success(c10PublicityDO);
        }
        return ResultVOUtils.success(authAdminList);
    }
    /**
    　* @Description: 删除宣传
    　* @author shalongteng
    　* @date 2020/4/18 18:28
    　*/
    @ApiOperation(value = "批量删除宣传", notes = "批量删除宣传")
    @GetMapping("deletePublicity")
    public ResultVo orderDetail(@ApiParam(name = "id", value = "主键id", required = true)@RequestParam(value = "id")Integer id) {
        Integer delete = publicityService.delete(id);
        if(delete >= 1){
            return ResultVOUtils.success();
        }else {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }
    }

    /**
     　* @Description: 批量通过拒绝/上架下架/删除
     　* @author shalongteng
     　* @date 2020/4/18 14:41
     　*/
    @ApiOperation(value = "批量通过拒绝/上架下架/删除", notes = "批量通过拒绝/上架下架/删除")
    @PostMapping("batchPassRefuse")
    public ResultVo batchPassRefuse(@RequestBody List<ShopPassRefuseForm> shopPassRefuseFormList) {
        if(shopPassRefuseFormList == null || shopPassRefuseFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {

            Integer integer = publicityService.batchPassRefuse(shopPassRefuseFormList);
            if(integer == 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.UPDATE_FAIL);
    }
    /**
     　* @Description: 编辑
     　* @author shalongteng
     　* @date 2020/4/18 18:28
     　*/
    @ApiOperation(value = "宣传修改")
    @PostMapping("updatePublicity")
    public ResultVo updatePublicity(@RequestBody C10PublicityUpdateDO dto) {
        C10PublicityDO c10PublicityDO = publicityService.get(dto.getId());
        if(c10PublicityDO == null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"宣传信息不存在");
        }
        BeanUtils.copyProperties(dto,c10PublicityDO);
        int update = publicityService.update(c10PublicityDO);
        return ResultVOUtils.success();
    }

}
