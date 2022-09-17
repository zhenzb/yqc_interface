package com.youqiancheng.service.client.serviceimpl;

import com.youqiancheng.mybatis.dao.F19CustomerServiceConversationDao;
import com.youqiancheng.mybatis.model.F19CustomerServiceConversationDO;
import com.youqiancheng.service.client.service.F19CustomerServiceConversationAppService;
import com.handongkeji.constants.StatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2022-02-17
 */
@Service
public class F19CustomerServiceConversationAppServiceImpl implements F19CustomerServiceConversationAppService {

    @Resource
    private F19CustomerServiceConversationDao f19CustomerServiceConversationDao;

    
    @Override
    public F19CustomerServiceConversationDO get(Long id){
        return f19CustomerServiceConversationDao.get(id);
    }

    
    @Override
    public List<F19CustomerServiceConversationDO> listHDPage(Map<String, Object> map) {
        return f19CustomerServiceConversationDao.listHDPage(map);
    }

    
    @Override
    public List<F19CustomerServiceConversationDO> list(Map<String, Object> map) {
        return f19CustomerServiceConversationDao.list(map);
    }


    
    @Override
    public int count(Map<String, Object> map){
        return f19CustomerServiceConversationDao.count(map);
    }

    
    @Override
    public int insert(F19CustomerServiceConversationDO f19CustomerServiceConversation) {
        return f19CustomerServiceConversationDao.insert(f19CustomerServiceConversation);
    }

    
    @Override
    public int insertBatch(List<F19CustomerServiceConversationDO> f19CustomerServiceConversations){
        return f19CustomerServiceConversationDao.insertBatch(f19CustomerServiceConversations);
    }

    
    @Override
    public int update(F19CustomerServiceConversationDO f19CustomerServiceConversation) {
        return f19CustomerServiceConversationDao.updateById(f19CustomerServiceConversation);
    }


    @Override
    public int stop(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.disable.getCode());
        return f19CustomerServiceConversationDao.updateStatus(param);
    }

    @Override
    public int start(List<Long> ids) {
        HashMap<String,Object> param=new HashMap<>();
        param.put("ids",ids);
        param.put("status", StatusConstant.enable.getCode());
        return f19CustomerServiceConversationDao.updateStatus(param);
        }

    @Override
    public int updateConversationStatus(String conversationId) {
        return f19CustomerServiceConversationDao.updateConversationStatus(conversationId);
    }


}
