package com.youqiancheng.controller.manager.goodsmanagement;

import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.Goods.*;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.manager.service.goodsmanagement.GoodsListService;
import com.youqiancheng.service.shop.ShopEvaluateService;
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
import java.util.*;

/**
　* @Description: 评论评价
　* @author shalongteng
　* @date 2020/4/17 18:41
　*/
@RestController
@RequestMapping("admin/_goods/comment")
@Api(tags = "总管理后台-商品管理-评论评价")
public class GoodsEvaluateController {
    private static final Log log = LogFactory.getLog(GoodsEvaluateController.class);

    @Resource
    private GoodsListService goodsListService;
    @Resource
    private ShopEvaluateService shopEvaluateService;
    /**
     　* @Description: 获取评论列表
     　* @author shalongteng
     　* @date 2020/4/18 9:11
     　*/
    @ApiOperation(value = "获取评论列表", notes = "获取评论列表")
    @GetMapping("listComment")
    public ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> listShop(@Valid CommentQueryForm commentQueryForm   ,
                                                               @Valid EntyPage page) {


        Map<String, Object> map = new HashMap<>();
        page.setPageSize(10000);
        page.setCurrentPage(1);
        map.put("commentQueryForm", commentQueryForm);
        map.put("page", page);
        List<D04GoodsEvaluateDO> authAdminList = goodsListService.listCommentHDPage(map);

        //获取宣传评价
        map.put("deleteFlag",1);
        map.put("goodsName",commentQueryForm.getGoodsName());
        List<C05CommentDO> c05CommentDOS =shopEvaluateService.listCommentHDPage(map);
        short hasPic = 0;
        for(C05CommentDO c05:c05CommentDOS){
            D04GoodsEvaluateDO d04GoodsEvaluateDO = new D04GoodsEvaluateDO();
            d04GoodsEvaluateDO.setContent(c05.getContent());
            d04GoodsEvaluateDO.setCreatePerson(c05.getCreatePerson());
            d04GoodsEvaluateDO.setCreateTime(c05.getCreateTime());
            d04GoodsEvaluateDO.setGoodsId(c05.getGoodsId());
            d04GoodsEvaluateDO.setGoodsName(c05.getGoodsName());
            d04GoodsEvaluateDO.setHasPic(hasPic);
            d04GoodsEvaluateDO.setIcon(c05.getGoodsIcon());
            d04GoodsEvaluateDO.setId(c05.getId());
            d04GoodsEvaluateDO.setUpdatePerson(c05.getUpdatePerson());
            d04GoodsEvaluateDO.setUpdateTime(c05.getUpdateTime());
            d04GoodsEvaluateDO.setUserId(c05.getUserId());
            authAdminList.add(d04GoodsEvaluateDO);
        }
        //按添加时间 倒叙排序
        authAdminList.sort(Comparator.comparing(D04GoodsEvaluateDO::getCreateTime).reversed());
        List<D04GoodsEvaluateDO> pageResult = new ArrayList<>();
        if(authAdminList.size()>0){
            if((page.getPageSize()*(page.getCurrentPage()-1))<=authAdminList.size()){
                List<D04GoodsEvaluateDO> c10PublicityDOS = authAdminList.subList(page.getPageSize() * (page.getCurrentPage() - 1),
                        ((page.getPageSize() * page.getCurrentPage()) > authAdminList.size() ? authAdminList.size() : (page.getPageSize() * page.getCurrentPage())));
                pageResult.addAll(c10PublicityDOS);
            }
        }

        //封装到分页
        PageSimpleVO<D04GoodsEvaluateDO> authAdminPageSimpleVO = new PageSimpleVO<>();
        authAdminPageSimpleVO.setTotalNumber(page.getTotalNumber());
        authAdminPageSimpleVO.setList(pageResult);

        return ResultVOUtils.success(authAdminPageSimpleVO);
    }



    /**
     　* @Description: 批量删除评论
     　* @author shalongteng
     　* @date 2020/4/16 18:24
     　*/
    @ApiOperation(value = "批量删除评论", notes = "批量删除评论")
    @PostMapping("deleteComment")
    public ResultVo deleteGoods(@RequestBody List<GoodsDeleteForm>  goodsDeleteFormList) {
        if(goodsDeleteFormList == null || goodsDeleteFormList.size() == 0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL);
        }else {
            Integer integer = goodsListService.deleteComment(goodsDeleteFormList);
            if(integer >= 1){
                return ResultVOUtils.success();
            }
        }

        return ResultVOUtils.error(ResultEnum.DELETE_FAIL);
    }


}
