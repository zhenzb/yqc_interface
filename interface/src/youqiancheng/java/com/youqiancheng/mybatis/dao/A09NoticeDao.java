package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A09NoticeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface A09NoticeDao  extends BaseMapper<A09NoticeDO>{

     A09NoticeDO get(Long id);

     List<A09NoticeDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A09NoticeDO> a09Notices);



     int updateList(List<A09NoticeDO> a09Notices);

     int updateStatus(Map<String, Object> map);

    List<A09NoticeDO> listNoticeHDPage(Map<String, Object> map);
    //Mr.Deng
    List<A09NoticeDO> listNoticePage(Map<String, Object> map);
}
