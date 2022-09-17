package com.handongkeji.push.android;


import com.handongkeji.push.AndroidNotification;
/**
 * 功能描述: <br>
 * 〈unicast-单播〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class AndroidUnicast extends AndroidNotification {
	public AndroidUnicast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");
	}

	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}
