package com.youqiancheng.controller.shop;

import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.manager.C05CommentReplyForm;
import com.youqiancheng.form.shop.EvaluateReplyForm;
import com.youqiancheng.form.shop.otther.ShopCommonForm;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;
import com.youqiancheng.mybatis.model.F08ShopUserDO;
import com.youqiancheng.service.shop.ShopEvaluateService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:23
 * @Version: V1.0
 */
@Api(tags = "商家管理--评论管理")
@RestController
@RequestMapping("shop/evaluateManagement")
public class ShopEvaluateManagementController {
    @Resource
    private ShopEvaluateService shopEvaluateService;


    @ApiOperation(value = "商品评价记录", notes = "商品评价记录")
    @GetMapping(value = "/pageGoodsEvaluate")
    public ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> pageGoodsEvaluate(String googdsName,@Valid EntyPage page ){


        QueryMap map=new QueryMap(page);

        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        map.put("shopId",shopUser.getShopId());
        map.put("goodsName",googdsName);
        List<D04GoodsEvaluateDO> goodsEvaluatelist = shopEvaluateService.listEvaluateHDPage(map);
        //封装到分页
        PageSimpleVO<D04GoodsEvaluateDO> shopEvaluatePageSimpleVO = new PageSimpleVO<>();
        shopEvaluatePageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopEvaluatePageSimpleVO.setList(goodsEvaluatelist);
        return ResultVOUtils.success(shopEvaluatePageSimpleVO);
    }

    @ApiOperation(value = "删除评价", notes = "删除评价")
    @PostMapping(value = "/delEvaluate")
    public ResultVo delEvaluate(@Valid ShopCommonForm shopCommonForm){
        if(shopCommonForm == null || ArrayUtils.isEmpty(shopCommonForm.getIds())){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<D04GoodsEvaluateDO> goodsEvaluates = new ArrayList<>();
        for (long id:shopCommonForm.getIds()){
            D04GoodsEvaluateDO goodsEvaluate = shopEvaluateService.getEvaluateById(id);
            if (goodsEvaluate != null){
                goodsEvaluate.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                goodsEvaluate.setUpdateTime(LocalDateTime.now());
                goodsEvaluates.add(goodsEvaluate);
            }
        }
        return ResultVOUtils.success(shopEvaluateService.batckUpdateEvaluate(goodsEvaluates));
    }



    @ApiOperation(value = "回复评价", notes = "回复评价")
    @PostMapping(value = "/replyEvaluate")
    public ResultVo replyEvaluate(@Valid EvaluateReplyForm form){
        D04GoodsEvaluateDO evaluateById = shopEvaluateService.getEvaluateById(form.getId());
        if(evaluateById==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到评价数据");
        }
        evaluateById.setReply(form.getReply());
        evaluateById.setReplyTime(LocalDateTime.now());
        int i = shopEvaluateService.updateEvaluateById(evaluateById);
        if(i<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"回复失败");
        }
        return ResultVOUtils.success();
    }



    @ApiOperation(value = "宣传评论", notes = "宣传评论")
    @GetMapping(value = "/listCommentHDPage")
    public ResultVo<PageSimpleVO<D04GoodsEvaluateDO>> listCommentHDPage(String goodsName,@Valid EntyPage page ){
        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL, "登录超时");
        }
        map.put("shopId",shopUser.getShopId());
        map.put("goodsName",goodsName);
        List<C05CommentDO> c05CommentDOS =shopEvaluateService.listCommentHDPage(map);
        //封装到分页
        PageSimpleVO<C05CommentDO> shopEvaluatePageSimpleVO = new PageSimpleVO<>();
        shopEvaluatePageSimpleVO.setTotalNumber(page.getTotalNumber());
        shopEvaluatePageSimpleVO.setList(c05CommentDOS);
        return ResultVOUtils.success(shopEvaluatePageSimpleVO);
    }


    @ApiOperation(value = "宣传回复", notes = "宣传回复")
    @PostMapping(value = "/replyComment")
    public ResultVo  replyComment(@Valid C05CommentReplyForm form ){

        C05CommentDO commentById = shopEvaluateService.getCommentById(form.getId());
        if(commentById==null){
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"未查询到评论数据");
        }
        commentById.setReply(form.getReply());
        int i = shopEvaluateService.updateCommentById(commentById);
        if(i<=0){
            return ResultVOUtils.error(ResultEnum.UPDATE_FAIL,"回复失败");
        }
        return ResultVOUtils.success();
    }



    @ApiOperation(value = "删除宣传评论", notes = "删除宣传评论")
    @PostMapping(value = "/delComment")
    public ResultVo delComment(String id){
        if(id == null){
            return ResultVOUtils.error(ResultEnum.PARAM_NULL,ResultEnum.PARAM_NULL.getMessage());
        }
        List<C05CommentDO> commentDo = new ArrayList<>();
        String[] split = id.split(",");
        for (String commentId:split){
            C05CommentDO commentBy = shopEvaluateService.getCommentById((Long.valueOf(commentId)));
            if (commentBy != null){
                commentBy.setDeleteFlag(StatusConstant.DeleteFlag.delete.getCode());
                commentBy.setUpdateTime(LocalDateTime.now());
                commentDo.add(commentBy);
            }
        }
        return ResultVOUtils.success(shopEvaluateService.batckUpdateComment(commentDo));
    }
}


