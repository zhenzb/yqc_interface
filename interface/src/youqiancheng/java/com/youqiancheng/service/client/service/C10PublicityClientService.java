package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.CommentSaveForm;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import com.youqiancheng.vo.client.C05CommentClientVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface C10PublicityClientService {

     C10PublicityDO get(Long id);

     List<C10PublicityDO> listHDPage(Map<String, Object> map);

     List<C10PublicityDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C10PublicityDO c10Publicity);

     int insertBatch(List<C10PublicityDO> c10Publicitys);

     int update(C10PublicityDO c10Publicity);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     int comment(CommentSaveForm form);
    // List<C05CommentDO> allComment(Map<String, Object> map);

    //查询用户的评论以及回复用户的评论
     List<C05CommentClientVO> allCommentAndReplyComment(Map<String, Object> map);
}
