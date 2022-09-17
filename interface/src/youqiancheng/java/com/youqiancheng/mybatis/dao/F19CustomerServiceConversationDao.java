package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.youqiancheng.mybatis.model.F19CustomerServiceConversationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2022-02-17
 */
@Mapper
public interface F19CustomerServiceConversationDao extends BaseMapper<F19CustomerServiceConversationDO> {

     F19CustomerServiceConversationDO get(Long id);


     List<F19CustomerServiceConversationDO> listHDPage(Map<String, Object> map);

     List<F19CustomerServiceConversationDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F19CustomerServiceConversationDO> f19CustomerServiceConversations);



     int updateList(List<F19CustomerServiceConversationDO> f19CustomerServiceConversations);

     int updateStatus(Map<String, Object> map);

     int updateConversationStatus(@Param(value="conversationId")String conversationId);
}
