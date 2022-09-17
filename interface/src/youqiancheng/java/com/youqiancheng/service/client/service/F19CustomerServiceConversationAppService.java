package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.F19CustomerServiceConversationDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2022-02-17
 */
public interface F19CustomerServiceConversationAppService {

     F19CustomerServiceConversationDO get(Long id);

     List<F19CustomerServiceConversationDO> listHDPage(Map<String, Object> map);

     List<F19CustomerServiceConversationDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(F19CustomerServiceConversationDO f19CustomerServiceConversation);

     int insertBatch(List<F19CustomerServiceConversationDO> f19CustomerServiceConversations);

     int update(F19CustomerServiceConversationDO f19CustomerServiceConversation);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     int updateConversationStatus(String conversationId);

}
