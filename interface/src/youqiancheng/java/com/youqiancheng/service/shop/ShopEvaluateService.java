package com.youqiancheng.service.shop;

import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.D04GoodsEvaluateDO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/15 18:26
 * @Version: V1.0
 */
public interface ShopEvaluateService {
    List<D04GoodsEvaluateDO> listEvaluateHDPage(Map<String, Object> map);

    D04GoodsEvaluateDO getEvaluateById(long id);
    Integer saveOrUpdateEvaluate(D04GoodsEvaluateDO goodsEvaluate);
    Integer batckUpdateEvaluate(List<D04GoodsEvaluateDO> goodsEvaluates);


    List<C05CommentDO> listCommentHDPage(Map<String, Object> map);
     C05CommentDO getCommentById(Long id);
   int  updateCommentById(C05CommentDO dto);
   int  updateEvaluateById(D04GoodsEvaluateDO dto);
    Integer batckUpdateComment(List<C05CommentDO> commentDO);
}
