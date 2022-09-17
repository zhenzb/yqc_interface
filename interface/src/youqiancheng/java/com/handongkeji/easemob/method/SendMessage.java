package com.handongkeji.easemob.method;

import com.google.gson.GsonBuilder;
import com.handongkeji.easemob.api.impl.EasemobSendMessage;
import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by easemob on 2017/3/22.
 */
public class SendMessage {
    private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
    /**
     *
     * @Description TODO
     * @author Mr.Chen
     * @param
     * @param message    发送内容
     * @param from       消息发送者 分为俩种 admin为环信后台  用户的环信ID
     * @param userName     发送的目标；注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']；给用户发送时数组元素是用户名，给群组发送时，数组元素是groupid
     * @param ext        扩展内容  可以作为内容的一些定义
     * @param targettype 发送的目标类型  users：给用户发消息，chatgroups：给群发消息，chatrooms：给聊天室发消息
     * @date 2019/3/13 14:53
     * @return void
     */
    public void sendText(String message,String from,UserName userName,String targettype,Map<String,Object> ext) {
        Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg(message);
//        UserName userName = new UserName();
//        for (String s:target) {
//            userName.add(s);
//        }
        msg.from(from).target(userName).targetType(targettype).msg(msgContent).ext(ext);
        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
//        System.out.println(result);
    }
    @Test
    public void sendTextTest(){
        Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg("小锤锤🔨你胸口");
        UserName userName = new UserName();
        userName.add("qh"+194);
        Map<String,Object> ext = new HashMap<>();
         ext.put("type","0");
        msg.from("qh170").target(userName).targetType("users").msg(msgContent).ext(ext);
        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result);
    }
    @Test
    public void sendImage() {
        Msg msg = new Msg();
        ImageMsgContent msgContent = new ImageMsgContent();
        msgContent.url("http://test_url").secret("test_sec").filename("filename").size(new ImageMsgContent.Size(480, 720))
                .type(MsgContent.TypeEnum.IMG).msg("this is an image message");
        UserName userName = new UserName();
        userName.add("receiver");
        msg.from("sender").target(userName).targetType("users").msg(msgContent);
        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result);
    }
    static class ImageMsgContent extends MsgContent {
        private String url;
        private String filename;
        private String secret;
        private Size size;

        ImageMsgContent url(String url) {
            this.url = url;
            return this;
        }

        ImageMsgContent filename(String filename) {
            this.filename = filename;
            return this;
        }

        ImageMsgContent secret(String secret) {
            this.secret = secret;
            return this;
        }

        ImageMsgContent size(Size size) {
            this.size = size;
            return this;
        }

        static class Size {
            private long width;
            private long height;

            Size(long width, long height) {
                this.width = width;
                this.height = height;
            }
        }
    }
}
