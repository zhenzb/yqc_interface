package com.youqiancheng.controller.websocket;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;
import javax.swing.*;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.F19CustomerServiceConversationDO;
import com.youqiancheng.service.client.service.B01UserClientService;
import com.youqiancheng.service.client.service.F19CustomerServiceConversationAppService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.util.RandomUUIDUtil;
import com.youqiancheng.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

/**
 * @Author: Zhenzhuobin
 * @Date: 2019/12/10 0010 09:19
 */
@Component
@ServerEndpoint(value = "/Greeting/{userName}",configurator = CustomSpringConfigurator.class)
public class GreetingController {

//    主要在@ServerEndpoint设置： configurator = SpringConfigurator.class
//    意思是可以Spring注入，就这么简单
    @Autowired
    private F19CustomerServiceConversationAppService f19CustomerServiceConversationAppService;
    @Autowired
    private B01UserClientService b01UserClientService;

    private Session session;

    private static CopyOnWriteArraySet<GreetingController> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userName")String userName) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userName, session);
        System.out.println(userName+"【websocket消息】有新的连接，总数为:"+webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【websocket消息】收到客户端消息:"+message);
        JSONObject jsonObject = JSONObject.parseObject(message);

        String userId = jsonObject.getString("userId");
        String shopId = jsonObject.getString("shopId");
//        if(this.f19CustomerServiceConversationAppService == null){
//            this.f19CustomerServiceConversationAppService =
//                    (F19CustomerServiceConversationAppService)ContextLoader.getCurrentWebApplicationContext().getBean("f19CustomerServiceConversationAppService");
//        }
        //收到的消息保存入库
        if("2".equals(jsonObject.getString("content"))){
          synchronized (this){
              try {
                  this.wait(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
            //收到客户确认收到消息的回调，修改数据库消息状态为已读
            f19CustomerServiceConversationAppService.updateConversationStatus(jsonObject.getString("conversationId"));
        }else{
            if(userId !=null && shopId ==null){
                //客服向消费者发送消息
                sendOneMessage(jsonObject.getString("userId"),jsonObject.toJSONString());
            }else{
                //消费者向客服发送消息
                if(jsonObject.getString("conversationId") == null){
                    String uuid = RandomUUIDUtil.getUUID();
                    jsonObject.put("conversationId",uuid);
                    sendOneMessage(shopId,jsonObject.toJSONString());
                }else{
                    sendOneMessage(shopId,jsonObject.toJSONString());
                }
            }
            F19CustomerServiceConversationDO f19CustomerServiceConversationDO = coverF19(userId, shopId,
                    jsonObject.getString("content"),jsonObject.getString("conversationId"));
            f19CustomerServiceConversationAppService.insert(f19CustomerServiceConversationDO);
        }



    }

    public F19CustomerServiceConversationDO coverF19(String userId,String shopId,String content,String conversationId){
        F19CustomerServiceConversationDO f19CustomerServiceConversationDO = new F19CustomerServiceConversationDO();
        if(userId !=null){
            f19CustomerServiceConversationDO.setUserId(Long.valueOf(userId));
        }
        if(shopId !=null){
            f19CustomerServiceConversationDO.setShopId(Long.valueOf(shopId));
        }
        f19CustomerServiceConversationDO.setConversationId(conversationId);
        f19CustomerServiceConversationDO.setContent(content);
        f19CustomerServiceConversationDO.setConversationTime(LocalDateTime.now());
        f19CustomerServiceConversationDO.setCreateTime(LocalDateTime.now());
        return f19CustomerServiceConversationDO;
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        for(GreetingController webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userName, String message) {
        //System.out.println("【websocket消息】单点消息:"+message);
        Session session = sessionPool.get(userName);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //定时查询未读的消息
    public void findUnRead(){
        Map<String,Object> map = new HashMap<>();
        EntyPage page = new EntyPage();
        page.setCurrentPage(1);
        page.setPageSize(10);
        map.put("page",page);
        map.put("readStatus",1);
        List<F19CustomerServiceConversationDO> f19CustomerServiceConversationDOS = f19CustomerServiceConversationAppService.listHDPage(map);
        for (F19CustomerServiceConversationDO f19:f19CustomerServiceConversationDOS) {
            JSONObject jsonObject = new JSONObject();
            if(null != f19.getUserId()){
                B01UserDO b01UserDO = b01UserClientService.get(f19.getUserId());
                jsonObject.put("userName",b01UserDO.getNick());
                jsonObject.put("userPic",b01UserDO.getPic());
            }
            jsonObject.put("conversationId",f19.getConversationId());
            jsonObject.put("userId",f19.getUserId());
            jsonObject.put("shopId",f19.getShopId());
            jsonObject.put("content",f19.getContent());
            jsonObject.put("conversationTime",f19.getConversationTime());
            if(f19.getShopId() == null){
                sendOneMessage(jsonObject.getString("userId"),jsonObject.toJSONString());
            }else{
                sendOneMessage(jsonObject.getString("shopId"),jsonObject.toJSONString());
            }
        }
    }
}
