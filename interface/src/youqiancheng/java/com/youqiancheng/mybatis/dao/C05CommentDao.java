package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C05CommentDO;
import com.youqiancheng.vo.client.C05CommentClientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C05CommentDao  extends BaseMapper<C05CommentDO>{

     C05CommentDO get(Long id);

     List<C05CommentDO> list(Map<String, Object> map);
     List<C05CommentDO> listByGoodsIdHDPage(Map<String, Object> map);
     List<C05CommentDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insertBatch(List<C05CommentDO> c05Comments);

     int updateList(List<C05CommentDO> c05Comments);

     int updateStatus(Map<String, Object> map);

    List<C05CommentDO> listCommentHDPage(Map<String, Object> map);



     //通过宣传的goodsId获取对它的评论集合
      List<C05CommentClientVO> allCommentAndReplyCommentHDPage(Map<String, Object> map);
}
