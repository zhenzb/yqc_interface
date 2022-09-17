package com.handongkeji.push;

import com.handongkeji.push.android.AndroidBroadcast;
import com.handongkeji.push.android.AndroidCustomizedcast;
import com.handongkeji.push.ios.IOSBroadcast;
import com.handongkeji.push.ios.IOSCustomizedcast;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UmengPush {
    protected static final Log logger = LogFactory.getLog(UmengPush.class);

    private static  String appkey_Android = "5ee85ca1895cca62e4000026";
    private static  String appMasterSecret_Android = "onc4k0wz71zc82utchw0wwmvpb47wmw0";

    private static  String appkey_IOS = "5ee9deb0570df30eab0000c7";
    private static  String appMasterSecret_IOS = "nyikp7w8xafuc5lxsxcnonkhb5cf8by6";


    //private static  String chinese = "{\"where\":{\"and\":[{\"or\":[{\"tag\":\"zh\"}]}]}}";
    //private static  String english = "{\"where\":{\"and\":[{\"or\":[{\"tag\":\"en\"}]}]}}";

    private static PushClient client = new PushClient();

    /**
     * IOS 单推
     * @param alias_Type		一般由  项目首字母加_type组成(例如：hlcx_type)
     * @param alias			一般由  项目首字母+用户主键userid(例如：hlcx99)
     * @param title			通知栏标题
     * @param subtitle		通知栏提示文字
     * @param text			消息内容
     * @param msgtype		自定义参数(消息类型：1 商品)
     * @param msgid			自定义参数(可以放任何id，消息id或计划id或订单id等)
     * @param obj			自定义参数
     * @return
     * @throws Exception
     */
    public static Boolean sendIOSCustomizedcast(String alias_Type, String alias,
                                                String title, String subtitle, String text, String msgtype, String msgid, String orderid, String type, JSONObject obj) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey_IOS,appMasterSecret_IOS);
        customizedcast.setAlias(alias, alias_Type);

        Map<String, String> alertJson = new HashMap<String, String>();
        alertJson.put("title", title);
        alertJson.put("subtitle", subtitle);
        alertJson.put("body", text);
        customizedcast.setPredefinedKeyValue("alert", alertJson);

        //customizedcast.setPredefinedKeyValue("display_type","message");

        //customizedcast.setProductionMode(true);
        customizedcast.setTestMode();

        customizedcast.setBadge( 0);
        customizedcast.setSound( "default");
        customizedcast.setContentAvailable(1);//唤醒app
        customizedcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
        customizedcast.setCustomizedField("msgtype", msgtype);
        customizedcast.setCustomizedField("msgid", msgid);
        customizedcast.setCustomizedField("orderid", orderid);
        customizedcast.setCustomizedField("type", type);
        if(obj!=null){
            customizedcast.setCustomizedField("obj", obj.toString());
        }

        Boolean flag = client.send(customizedcast);
        return flag;
    }


    /**
     * IOS 全推
     * @param title			通知栏标题
     * @param subtitle		通知栏提示文字
     * @param text			消息内容
     * @param msgtype		自定义参数(消息类型： 1商品)
     * @param msgid			自定义参数(可以放任何id，消息id或计划id或订单id等)//
     * @param filter
     * @return
     * @throws Exception
     */
    public static Boolean sendIOSBroadcast( String title, String subtitle,
                                            String text,String msgtype,String msgid, Object filter) throws Exception {

        IOSBroadcast broadcast = new IOSBroadcast(appkey_IOS,appMasterSecret_IOS);
        broadcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
        Map<String, String> alertJson = new HashMap<String, String>();
        alertJson.put("title", title);
        alertJson.put("subtitle", subtitle);
        alertJson.put("body", text);
        broadcast.setPredefinedKeyValue("alert", alertJson);
        broadcast.setBadge( 0);
        broadcast.setSound( "default");

        broadcast.setContentAvailable(1);//唤醒app
       //broadcast.setProductionMode(true);
        broadcast.setDescription("test1");
        broadcast.setTestMode();
        broadcast.setCustomizedField("msgtype", msgtype);
        broadcast.setCustomizedField("msgid", msgid);

        if(filter!=null){
//			JSONObject json = new JSONObject(filter);
            broadcast.setPredefinedKeyValue("filter", filter);
        }

        Boolean flag = false;
        flag = client.send(broadcast);
        return flag;
    }


    /**
     * 安卓单推
     * @param alias_Type		一般由  项目首字母加_type组成(例如：hlcx_type)
     * @param alias			一般由  项目首字母+用户主键userid(例如：hlcx99)
     * @param title			通知栏标题
     * @param ticker		通知栏提示文字
     * @param text			消息内容
     * @param msgtype		自定义参数(消息类型：)
     * @param msgid			自定义参数(可以放任何id，消息id或计划id或订单id等)
     * @param obj			自定义参数
     * @return
     * @throws Exception
     */
    public static Boolean sendAndroidCustomizedcast(String alias_Type, String alias,
                                                    String title, String ticker, String text, String msgtype, String msgid, String orderid, String type, JSONObject obj) throws Exception {

        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey_Android,appMasterSecret_Android);
        customizedcast.setAlias( alias, alias_Type);
        customizedcast.setPredefinedKeyValue("alias", alias);
        customizedcast.setPredefinedKeyValue("alias_type", alias_Type);
        /*---*/
       // customizedcast.setPredefinedKeyValue("mi_activity", "com.qicheshouce.app.push.MipushTestActivity");
       // customizedcast.setPredefinedKeyValue("mipush", "true");
        /*--*/
        customizedcast.setTitle(title);
        customizedcast.setTicker(ticker);
        customizedcast.setText(text);
        customizedcast.goAppAfterOpen();
        customizedcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        //customizedcast.setProductionMode(true);
        customizedcast.setTestMode();
        customizedcast.setExtraField("msgtype", msgtype);
        customizedcast.setExtraField("msgid", msgid);
        customizedcast.setExtraField("orderid", orderid);
        customizedcast.setExtraField("type", type);
        if(obj!=null){
            customizedcast.setExtraField("obj", obj.toString());
        }
        Boolean flag = client.send(customizedcast);

        return flag;
    }



    /**
     *  安卓全推
     * @param title			通知栏标题
     * @param text			消息内容
     * @param ticker		通知栏提示文字
     * @param msgtype		自定义参数(消息类型：)
     * @param msgid			自定义参数(可以放任何id，消息id或计划id或订单id等)
     * @param filter
     * @return
     * @throws Exception
     */
    public static Boolean sendAndroidBroadcast( String title, String ticker, String text,String msgtype,String msgid, Object filter) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey_Android,appMasterSecret_Android);
        broadcast.setTitle(  title);
        broadcast.goAppAfterOpen();
        broadcast.setTicker(ticker);
        broadcast.setText(text);
        broadcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
        broadcast.setExtraField("msgtype", msgtype);
        broadcast.setExtraField("msgid", msgid);
        /*---*/
       // broadcast.setPredefinedKeyValue("mi_activity", "com.qicheshouce.app.push.MipushTestActivity");
      //  broadcast.setPredefinedKeyValue("mipush", "true");
        /*--*/
        if(filter!=null){
            broadcast.setPredefinedKeyValue("filter", filter);
        }
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        //broadcast.setProductionMode(true);
        broadcast.setTestMode();
        Boolean flag = client.send(broadcast);
        return flag;
    }


}

