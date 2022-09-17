package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C14ReplyCommentDO;
import com.youqiancheng.vo.app.C14ReplyCommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-07-28
 */
@Mapper
public interface C14ReplyCommentDao extends BaseMapper<C14ReplyCommentDO> {

     C14ReplyCommentDO get(Long id);


     List<C14ReplyCommentVO> listHDPage(Map<String, Object> map);

     List<C14ReplyCommentDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C14ReplyCommentDO> c14ReplyComments);



     int updateList(List<C14ReplyCommentDO> c14ReplyComments);

     int updateStatus(Map<String, Object> map);
}
