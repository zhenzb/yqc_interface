package com.handongkeji.push.android;

import com.handongkeji.push.AndroidNotification;
/**
 * 功能描述: <br>
 * 〈广播〉
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");
	}
}
