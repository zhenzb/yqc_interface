package com.handongkeji.push.ios;


import com.handongkeji.push.IOSNotification;
/**
 * 功能描述: <br>
 * 〈广播〉
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");

	}
}
