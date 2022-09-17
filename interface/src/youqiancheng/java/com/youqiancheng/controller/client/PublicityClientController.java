package com.youqiancheng.controller.client;

import com.handongkeji.config.auth.AuthRuleAnnotation;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.form.app.C14CommentSaveForm;
import com.youqiancheng.form.client.*;
import com.youqiancheng.form.shop.otther.ShopGoodsPageForm;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.*;
import com.youqiancheng.service.shop.ShopGoodsService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.PageSimpleVO;
import com.youqiancheng.vo.app.C14ReplyCommentVO;
import com.youqiancheng.vo.client.C05CommentClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Slf4j
@Api(tags = {"PC端-宣传"})
@RestController
@RequestMapping(value = "pc/publicity")
public class PublicityClientController {
    @Autowired
    private C10PublicityClientService c10PublicityService;
    @Resource
    private ShopGoodsService shopGoodsService;

    @Autowired
    private C12RewardRecordClientService c12RewardRecordService;
    @Autowired
    private B05CollectionClientService b05CollectionService;

    @Autowired
    private F01ShopClientService f01ShopClientService;

    @Autowired
    private C14ReplyCommentClientService c14ReplyCommentClientService;
    List<C10PublicityDO> c10PublicityList =new ArrayList<>();
    int totalNumber = 0;
    @ApiOperation(value = "根据商家获取商家的宣传图文信息列表——音频2，图文3；参数——商家ID")
    @GetMapping("/getPublicityByShopId")
    ResultVo<PageSimpleVO<C10PublicityDO>> getPublicityByShopId(@Valid C10PublicitySearchForm form , @Valid EntyPage page ) {
        if(page.getCurrentPage()==1){
            c10PublicityList.clear();
            totalNumber = 0;
            EntyPage pages = new EntyPage();
            pages.setCurrentPage(1);
            pages.setPageSize(1000);
            QueryMap map = new QueryMap(form,pages, StatusConstant.CreatFlag.delete.getCode());
            map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
            //map.put("type",StatusConstant.PublicityType.pic.getCode());
            c10PublicityList = c10PublicityService.listHDPage(map);
            totalNumber += pages.getTotalNumber();
            //获取商品表里的社交圈
            ShopGoodsPageForm shopGoodsPageForm = new ShopGoodsPageForm();
            shopGoodsPageForm.setShopId(form.getShopId());
            Map<String, Object> map1 = new HashMap<>();
            shopGoodsPageForm.setTypes(3);
            shopGoodsPageForm.setGoodsStatus(2);
            map1.put("shopGoodsPageForm", shopGoodsPageForm);
            map1.put("page", pages);
            List<C01GoodsDO> goodslist = shopGoodsService.listGoodsHDPage(map1);
            totalNumber += pages.getTotalNumber();
            if(goodslist.size()!=0){
                for(C01GoodsDO c01GoodsDO:goodslist){
                    QueryMap map2=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
                    map2.put("goodsId",c01GoodsDO.getId());
                    //查所有的评论以及回复评论的评论
                    try{
                        List<C05CommentClientVO> c05CommentDOS = c10PublicityService.allCommentAndReplyComment(map2);
                    }catch (Exception e){
                        log.info("该社会圈暂无评论》》》");
                    }
                    C10PublicityDO c10PublicityDO = new C10PublicityDO();
                    c10PublicityDO.setId(c01GoodsDO.getId());
                    c10PublicityDO.setBrowseVolume(c01GoodsDO.getBrowseVolume());
                    c10PublicityDO.setCommentCount(page.getTotalNumber());
                    c10PublicityDO.setContent(c01GoodsDO.getIntroduction());
                    c10PublicityDO.setType(Integer.valueOf(c01GoodsDO.getType()));
                    c10PublicityDO.setCreateTime(c01GoodsDO.getCreateTime());
                    c10PublicityDO.setIcon(c01GoodsDO.getIcon());
                    c10PublicityList.add(c10PublicityDO);
                }
            }
            c10PublicityList.sort((item1,item2)->item2.getCreateTime().compareTo(item1.getCreateTime()));
        }

        //封装到分页
        List<C10PublicityDO> c10PublicityListResult = new ArrayList<>();
        if(c10PublicityList.size()>10){
           int currentPage = (page.getCurrentPage()-1) * 10;
            if(currentPage == 0){
                for(;currentPage<10;currentPage++){
                    c10PublicityListResult.add(c10PublicityList.get(currentPage));
                }
            }else{
                for(;currentPage<totalNumber;currentPage++){
                    c10PublicityListResult.add(c10PublicityList.get(currentPage));
                }
            }

        }else{
            for(C10PublicityDO c10PublicityDO:c10PublicityList){
                c10PublicityListResult.add(c10PublicityDO);
            }
        }
        PageSimpleVO<C10PublicityDO> c10PublicitySimpleVO = new PageSimpleVO<>();
            c10PublicitySimpleVO.setTotalNumber(totalNumber);
            c10PublicitySimpleVO.setList(c10PublicityListResult);

        return ResultVOUtils.success(c10PublicitySimpleVO);
    }

    @ApiOperation(value = "根据商家获取商家的宣传视频信息;参数——商家ID")
    @GetMapping("/getVideoByShopId")
    ResultVo getVideoByShopId(@Valid C10PublicitySearchForm form  ) {

        QueryMap map = new QueryMap(form,StatusConstant.CreatFlag.delete.getCode());
        map.put("isSale",StatusConstant.SaleFlag.on_sale.getCode());
        map.put("type",StatusConstant.PublicityType.video.getCode());
        List<C10PublicityDO> list = c10PublicityService.list(map);
        if(CollectionUtils.isEmpty(list)){
            return ResultVOUtils.success();
        }
        return ResultVOUtils.success(list.get(0));
    }

    @ApiOperation(value = "根据宣传ID获取宣传详情；参数——宣传ID")
    @GetMapping("/getInfoById")
    @AuthRuleAnnotation()
    ResultVo getInfoById( Long id ){
        if(id==null||id==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"宣传信息不能为空");
        }
        C10PublicityDO c10Publicity = c10PublicityService.get(id);
        return ResultVOUtils.success(c10Publicity);
    }



    @ApiOperation(value = "获取打赏记录列表；参数——商家ID")
    @GetMapping("/list")
    ResultVo<PageSimpleVO<C12RewardRecordDO>> listByPage(@Valid C12RewardRecordSearchForm form, @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form,page, StatusConstant.CreatFlag.delete.getCode());
        map.put("status",StatusConstant.PayStatus.pay.getCode());
        List<C12RewardRecordDO> c12RewardRecordList = c12RewardRecordService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C12RewardRecordDO> c12RewardRecordSimpleVO = new PageSimpleVO<>();
        c12RewardRecordSimpleVO.setTotalNumber(page.getTotalNumber());
        c12RewardRecordSimpleVO.setList(c12RewardRecordList);

        return ResultVOUtils.success(c12RewardRecordSimpleVO);
    }
//-------------------------------------------------------------------------------------------------
    @ApiOperation(value = "获取商家二维码图片；参数——商家ID")
    @GetMapping("/getQRcodeimage")
    ResultVo getQRcodeimage(Long shopId) {
        F01ShopDO qRcodeimage = f01ShopClientService.getQRcodeimage(shopId);
        return ResultVOUtils.success(qRcodeimage);
    }



    @ApiOperation(value = "保存单条打赏记录;参数——打赏记录保存实体：用户uID,商家ID,金额，用户名")
    @PostMapping("/save")
    @AuthRuleAnnotation()
    ResultVo insert(@RequestBody @Valid C12RewardRecordSaveForm dto ) {

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


    @ApiOperation(value = "收藏宣传:参数——宣传ID，用户ID")
    @PostMapping("/collectionGoods")
    @AuthRuleAnnotation()
    ResultVo collectionGoods(@RequestBody @Valid B05CollectionSaveForm dto ) {

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
    @ApiOperation(value = "获取宣传被收藏数；参数——宣传ID")
    @GetMapping("/getCollectionGoodsCount")
    ResultVo<PageSimpleVO<B05CollectionDO>> getGoodsCount(@Valid B05CollectionSearchCountForm form) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "查询宣传是否被收藏——0为未收藏;其他都为收藏；参数——宣传ID，用户ID")
    @GetMapping("/getIsCollectionGoods")
    @AuthRuleAnnotation()
    ResultVo  getIsCollectionGoods(@Valid B05CollectionSearchIsForm  form , @Valid EntyPage page ) {

        QueryMap map = new QueryMap(form, StatusConstant.CreatFlag.delete.getCode());
        //设置收藏类型为商家
        map.put("type",StatusConstant.CollectionType.publicity.getCode());
        int count = b05CollectionService.count(map);
        return ResultVOUtils.success(count);
    }

    @ApiOperation(value = "收藏商家；参数——商家ID，用户ID")
    @PostMapping("/collectionShop")
    @AuthRuleAnnotation()
    ResultVo collectionShop(@RequestBody @Valid B05CollectionSaveForm dto ) {

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

    @ApiOperation(value = "取消收藏商家；参数——商家ID，用户ID")
    @PostMapping("/cancelCollectionShop")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionShop(@RequestBody @Valid B05CollectionSaveForm dto ) {

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
    @ApiOperation(value = "取消收藏宣传；参数——宣传ID，用户ID")
    @PostMapping("/cancelCollectionGoods")
    @AuthRuleAnnotation()
    ResultVo cancelCollectionGoods(@RequestBody @Valid B05CollectionSaveForm dto ) {

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


    @ApiOperation(value = "评论")
    @PostMapping("/comment")
    @AuthRuleAnnotation()
    ResultVo comment(@RequestBody CommentSaveForm form ) {

        int num=c10PublicityService.comment(form);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"添加评论失败");
        }
        return ResultVOUtils.success(num);
    }

/*    @ApiOperation(value = "全部评论;参数——宣传ID")
    @PostMapping("/allComment")
    ResultVo<PageSimpleVO<C05CommentDO>> allComment(Long goodsId, @Valid EntyPage page ) {
        if(goodsId==null||goodsId==0){
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商品ID不为空");
        }
        QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
        map.put("goodsId",goodsId);
        List<C05CommentDO> c05CommentDOS = c10PublicityService.allComment(map);
        //封装到分页
        PageSimpleVO<C05CommentDO> c10PublicitySimpleVO = new PageSimpleVO<>();
        c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
        c10PublicitySimpleVO.setList(c05CommentDOS);

        return ResultVOUtils.success(c10PublicitySimpleVO);
       }*/
  //因需求改变
        @ApiOperation(value = "全部评论;参数——宣传ID")
        @PostMapping("/allComment")
        ResultVo<PageSimpleVO<C05CommentClientVO>> allComment(Long goodsId, @Valid EntyPage page ) {
            if(goodsId==null||goodsId==0){
                return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"商品ID不为空");
            }
            QueryMap map=new QueryMap(page,StatusConstant.CreatFlag.delete.getCode());
            map.put("goodsId",goodsId);
            //查所有的评论以及回复评论的评论
            List<C05CommentClientVO> c05CommentDOS = c10PublicityService.allCommentAndReplyComment(map);
            //封装到分页
            PageSimpleVO<C05CommentClientVO> c10PublicitySimpleVO = new PageSimpleVO<>();
            c10PublicitySimpleVO.setTotalNumber(page.getTotalNumber());
            c10PublicitySimpleVO.setList(c05CommentDOS);

            return ResultVOUtils.success(c10PublicitySimpleVO);
        }

/*    @ApiOperation(value = "获取回复宣传评论内容的评论表列表：参数：评论宣传店铺的id")
    @GetMapping("/getReplyComment")
    ResultVo<PageSimpleVO<C14ReplyCommentVO>> listByPage(Long id, @Valid EntyPage page) {

        if(id==null){
            return  ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST);
        }
        QueryMap map = new QueryMap( page, StatusConstant.CreatFlag.delete.getCode());
        map.put("commentId",id);
        List<C14ReplyCommentVO> c14ReplyCommentList = c14ReplyCommentClientService.listHDPage(map);
        //封装到分页
        PageSimpleVO<C14ReplyCommentVO> c14ReplyCommentSimpleVO = new PageSimpleVO<>();
        c14ReplyCommentSimpleVO.setTotalNumber(page.getTotalNumber());
        c14ReplyCommentSimpleVO.setList(c14ReplyCommentList);

        return ResultVOUtils.success(c14ReplyCommentSimpleVO);
    }*/


    @ApiOperation(value = "回复用户评论的评论;参数——回复用户评论---保存实体")
    @PostMapping("/replyUsercomment")
    @AuthRuleAnnotation()
    ResultVo comment(@RequestBody C14CommentSaveForm form) {
        //这就是一次添加,但是前提是在这个用户存在,这个被评论的宣传内容也在,才能添加评论
        int num=c14ReplyCommentClientService.replycomment(form);
        if(num<=0){
            return ResultVOUtils.error(ResultEnum.INSERT_FAIL,"添加评论失败");
        }
        return ResultVOUtils.success(num);
    }

}
