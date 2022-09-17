package com.youqiancheng.service.client.serviceimpl;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.youqiancheng.form.app.C14CommentSaveForm;
import com.youqiancheng.mybatis.dao.C05CommentDao;
import com.youqiancheng.mybatis.dao.C14ReplyCommentDao;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.mybatis.model.C14ReplyCommentDO;
import com.youqiancheng.service.app.service.C14ReplyCommentAppService;
import com.youqiancheng.service.client.service.C14ReplyCommentClientService;
import com.youqiancheng.vo.app.C14ReplyCommentVO;
import com.youqiancheng.vo.result.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-07-28
 */
@Service
public class C14ReplyCommentClientServiceImpl implements C14ReplyCommentClientService {
    @Autowired
    private C14ReplyCommentDao c14ReplyCommentDao;

    @Autowired
    private C05CommentDao c05CommentDao;
    
    @Override
    public C14ReplyCommentDO get(Long id){
        return c14ReplyCommentDao.get(id);
    }

    
    @Override
    public List<C14ReplyCommentVO> listHDPage(Map<String, Object> map) {
        List<C14ReplyCommentVO> c14ReplyCommentVOS = c14ReplyCommentDao.listHDPage(map);
        if(c14ReplyCommentVOS.isEmpty()&&c14ReplyCommentVOS==null){
              throw new JsonException(ResultEnum.DATA_NOT_EXIST,"回复评论为空");
             }
        return c14ReplyCommentVOS;
    }

    
    @Override
    public List<C14ReplyCommentDO> list(Map<String, Object> map) {
        return c14ReplyCommentDao.list(map);
    }


    
    @Override
    public int count(Map<String, Object> map){
        return c14ReplyCommentDao.count(map);
    }

    
    @Override
    public int insert(C14ReplyCommentDO c14ReplyComment) {
        return c14ReplyCommentDao.insert(c14ReplyComment);
    }

    
    @Override
    public int insertBatch(List<C14ReplyCommentDO> c14ReplyComments){
        return c14ReplyCommentDao.insertBatch(c14ReplyComments);
    }

    
    @Override
    public int update(C14ReplyCommentDO c14ReplyComment) {
        return c14ReplyCommentDao.updateById(c14ReplyComment);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return c14ReplyCommentDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return c14ReplyCommentDao.updateStatus(param);
        }

    @Override
    public int replycomment(C14CommentSaveForm form) {

        //获取用户评论宣传的评论id
        C05CommentDO c05CommentDO = c05CommentDao.get(form.getId());
        if(c05CommentDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"用户评论宣传的数据为空");
        }
        C14ReplyCommentDO dto=new C14ReplyCommentDO();

        dto.setCommentId(form.getId());
        dto.setReplyContent(form.getReplyContent());
        dto.setUserId(form.getUserId());
        dto.setUserName(form.getUserName());
        dto.setCreatePerson(form.getUserName());
        dto.setCreateTime(LocalDateTime.now());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        return c14ReplyCommentDao.insert(dto);


    }
}
