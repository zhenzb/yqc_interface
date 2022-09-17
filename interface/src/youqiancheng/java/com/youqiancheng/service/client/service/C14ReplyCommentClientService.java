package com.youqiancheng.service.client.service;

import com.youqiancheng.form.app.C14CommentSaveForm;
import com.youqiancheng.mybatis.model.C14ReplyCommentDO;
import com.youqiancheng.vo.app.C14ReplyCommentVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-07-28
 */
public interface C14ReplyCommentClientService {

     C14ReplyCommentDO get(Long id);

     List<C14ReplyCommentVO> listHDPage(Map<String, Object> map);

     List<C14ReplyCommentDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C14ReplyCommentDO c14ReplyComment);

     int insertBatch(List<C14ReplyCommentDO> c14ReplyComments);

     int update(C14ReplyCommentDO c14ReplyComment);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     //回复用户评论的评论
     int replycomment(C14CommentSaveForm form);
}
