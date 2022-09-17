package com.youqiancheng.controller.app;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.app.service.*;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.C14ReplyCommentVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Api(tags = {"手机端-宣传接口"})
@RestController
@RequestMapping(value = "app/publicity")
public class PublicityAppController {
    @Autowired
    private C10PublicityAppService c10PublicityService;
    @Autowired
    private C12RewardRecordAppService c12RewardRecordService;
    @Autowired
    private B05CollectionAppService b05CollectionService;
    @Autowired
    private F01ShopAppService f01ShopAppService;
    @Autowired
    private C14ReplyCommentAppService c14ReplyCommentService;


    @ApiOperation(value = "获取打赏记录列表；参数——商家ID")
    @GetMapping("/list")
    ResultVo<PageSimpleVO<C12RewardRecordDO>> listByPage(@Valid C12RewardRecordSearchForm form, @Valid EntyPage page ) {
        //根据商家的id来获取
        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("status",StatusConstant.PayStatus.pay.getCode());
        List<C12RewardRecordDO> c12RewardRecordList = c12RewardRecordService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C12RewardRecordDO> c12RewardRecordSimpleVO = new PageSimpleVO<>();
        c12RewardRecordSimpleVO.setTotalNumber(page.getTotalNumber());
        c12RewardRecordSimpleVO.setList(c12RewardRecordList);

        return ResultVOUtils.success(c12RewardRecordSimpleVO);
    }



    @ApiOperation(value = "保存打赏记录;参数——打赏记录实体")
    @PostMapping("/save")
    @AuthRuleAnnotation()
    ResultVo insert(@RequestBody C12RewardRecordSaveForm dto) {
        C12RewardRecordDO c12RewardRecord=new C12RewardRecordDO();
        c12RewardRecord.setShopId(dto.getShopId());
        c12RewardRecord.setUserId(dto.getUserId());
        c12RewardRecord.setMoney(dto.getMoney());
        c12RewardRecord.setCreatePerson(dto.getUserName());
        c12RewardRecord.setCreateTime(LocalDateTime.now());
        c12RewardRecord.setUpdatePerson(dto.getUserName());
        c12RewardRecord.setUpdateTime(LocalDateTime.now());
        c12RewardRecord.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        c12RewardRecord.setStatus(StatusConstant.PayStatus.un_pay.getCode());
        int num=c12RewardRecordService.insert(c12RewardRecord);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL);
        }
        return ResultVOUtils.success(c12RewardRecord.getId());
    }
    @ApiOperation(value = "根据商家获取商家的宣传图文和音频信息列表——按照时间降序；参数——商家ID,分页参数")
    @GetMapping("/getPublicityByShopId")
    ResultVo<PageSimpleVO<C10PublicityDO>> getPublicityByShopId(@Valid C10PublicitySearchForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        List<C10PublicityDO> c10PublicityList = c10PublicityService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C10PublicityDO> c10PublicitySimpleVO = new PageSimpleVO<>();
            c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
            c10PublicitySimpleVO.setList(c10PublicityList);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }


    @ApiOperation(value = "根据商家获取商家的宣传视频信息；参数——商家ID")
    @GetMapping("/getVideoByShopId")
    ResultVo getVideoByShopId(@Valid C10PublicitySearchForm form  ) {

        QueryMap map = new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("type",StatusConstant.PublicityType.video.getCode());
        List<C10PublicityDO> list = c10PublicityService.list(map);
        if(CollectionUtils.isEmpty(list)){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.success(list.get(0));
    }

    @ApiOperation(value = "根据宣传ID获取宣传详情；参数——宣传ID")
    @GetMapping("/getInfoById")
    ResultVo getInfoById(Long id ){
        C10PublicityDO c10Publicity = c10PublicityService.get(id);
        return ResultVOUtils.success(c10Publicity);
    }


    @ApiOperation(value = "获取宣传被收藏数；参数——宣传ID")
    @GetMapping("/getCollectionGoodsCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getGoodsCount(@Valid B05CollectionSearchCountForm form  ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }


    @ApiOperation(value = "收藏宣传;参数——用户ID,宣传ID")
    @PostMapping("/collectionGoods")
    @AuthRuleAnnotation()
    ResultVo collectionGoods(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.publicity.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b05CollectionService.insert(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"收藏宣传失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "收藏商家;参数——用户ID,商家ID")
    @PostMapping("/collectionShop")
    @AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        b05Collection.setCreateTime(LocalDateTime.now());
//        b05Collection.setCreatePerson(dto.getUserName());
//        b05Collection.setUpdatePerson(dto.getUserName());
        b05Collection.setUpdateTime(LocalDateTime.now());
        b05Collection.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        int num=b05CollectionService.insert(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }



    @ApiOperation(value = "判断宣传是否被用户收藏——返回收藏数：等于0——未收藏;参数——用户ID,宣传ID")
    @GetMapping("/getGoodsIsCollection")
    @AuthRuleAnnotation()
    ResultVo  getGoodsIsCollection(@Valid B05CollectionSearchIsForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "获取商家是否被用户收藏——收藏数为0则未收藏;参数——商家ID,用户ID")
    @GetMapping("/getIsCollectionShop")
    @AuthRuleAnnotation()
    ResultVo  getIsCollectionShop(@Valid B05CollectionSearchIsForm form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.shop.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "取消收藏商家;参数——用户ID,商家ID")
    @PostMapping("/cancelCollectionShop")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.shop.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num=b05CollectionService.delete(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"取消收藏商家失败");
        }
        return ResultVOUtils.success(num);
    }
    @ApiOperation(value = "取消收藏宣传;参数——用户ID,宣传ID")
    @PostMapping("/cancelCollectionGoods")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionGoods(@RequestBody B05CollectionSaveForm dto) {
        if(dto.getUserId()==null||dto.getRelationId()==null||dto.getUserId()==0||dto.getRelationId()==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"用户ID或者收藏ID不能为空");
        }
        B05CollectionDO b05Collection= new B05CollectionDO();
        b05Collection.setRelationId(dto.getRelationId());
        b05Collection.setType(StatusConstant.CollectionType.publicity.getCode());
        b05Collection.setUserId(dto.getUserId());
        int num=b05CollectionService.delete(b05Collection);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"取消收藏宣传失败");
        }
        return ResultVOUtils.success(num);
    }


    @ApiOperation(value = "评论;参数——评论保存实体")
    @PostMapping("/comment")
    @AuthRuleAnnotation()
    ResultVo comment(@RequestBody CommentSaveForm form) {
        //这就是一次添加,但是前提是在这个用户存在,这个被评论的宣传内容也在,才能添加评论
        int num=c10PublicityService.comment(form);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"添加评论失败");
        }
        return ResultVOUtils.success(num);
    }

 /*   @ApiOperation(value = "全部评论;参数——宣传ID")
    @PostMapping("/allComment")
    ResultVo<PageSimpleVO<C05CommentDO>> allComment(Long goodsId, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("goodsId",goodsId);
        //根据商品的id获取它的全部评论(内容)
        List<C05CommentDO> c05CommentDOS = c10PublicityService.allComment(map);
        //封装到分页
        PageSimpleVO<C05CommentDO> c10PublicitySimpleVO = new PageSimpleVO<>();
        c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
        c10PublicitySimpleVO.setList(c05CommentDOS);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }*/


    @ApiOperation(value = "全部评论;参数——宣传ID")
    @PostMapping("/allComment")
    ResultVo<PageSimpleVO<C05CommentDO>> allComment(Long goodsId, @Valid EntyPage page ) {

        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("goodsId",goodsId);
        //根据商品的id获取它的全部评论(内容)
        List<C05CommentDO> c05CommentDOS = c10PublicityService.allComment(map);
        //封装到分页
        PageSimpleVO<C05CommentDO> c10PublicitySimpleVO = new PageSimpleVO<>();
        c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
        c10PublicitySimpleVO.setList(c05CommentDOS);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }




    @ApiOperation(value = "获取商家二维码图片；参数——商家ID")
    @GetMapping("/getQRcodeimage")
    ResultVo getQRcodeimage(Long shopId) {
        F01ShopDO qRcodeimage = f01ShopAppService.getQRcodeimage(shopId);
        return ResultVOUtils.success(qRcodeimage);
    }





    @ApiOperation(value = "获取回复宣传评论内容的评论表列表：参数：评论宣传店铺的id")
    @GetMapping("/getReplyComment")
    ResultVo<PageSimpleVO<C14ReplyCommentVO>> listByPage(Long id, @Valid EntyPage page) {

        if(id==null){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        QueryMap map = new QueryMap( page, StatusConstant.CreatFlag.delete.getCode());
        map.put("commentId",id);
        List<C14ReplyCommentVO> c14ReplyCommentList = c14ReplyCommentService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C14ReplyCommentVO> c14ReplyCommentSimpleVO = new PageSimpleVO<>();
        c14ReplyCommentSimpleVO.setTotalNumber(page.getTotalNumber());
        c14ReplyCommentSimpleVO.setList(c14ReplyCommentList);

        return ResultVOUtils.success(c14ReplyCommentSimpleVO);
    }

    @ApiOperation(value = "回复用户评论的评论;参数——回复用户评论---保存实体")
    @PostMapping("/replyUsercomment")
    @AuthRuleAnnotation()
    ResultVo comment(@RequestBody C14CommentSaveForm form) {
        //这就是一次添加,但是前提是在这个用户存在,这个被评论的宣传内容也在,才能添加评论
        int num=c14ReplyCommentService.replycomment(form);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"添加评论失败");
        }
        return ResultVOUtils.success(num);
    }

}
