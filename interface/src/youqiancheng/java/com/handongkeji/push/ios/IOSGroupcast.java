package com.handongkeji.push.ios;

import com.handongkeji.push.IOSNotification;
import org.json.JSONObject;

/**
 * 功能描述: <br>
 * 〈 groupcast-组播，按照filter筛选用户群, 请参照filter参数〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
		public class IOSGroupcast extends IOSNotification {
			public IOSGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
