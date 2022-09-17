package com.handongkeji.push.ios;


import com.handongkeji.push.IOSNotification;
/**
 * 功能描述: <br>
 * 〈unicast-单播〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class IOSUnicast extends IOSNotification {
	public IOSUnicast(String appkey,String appMasterSecret) throws Exception{
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");
	}

	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
