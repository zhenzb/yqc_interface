package com.handongkeji.push.android;

import com.handongkeji.push.AndroidNotification;
import org.json.JSONObject;

/**
 * 功能描述: <br>
 * 〈 groupcast-组播，按照filter筛选用户群, 请参照filter参数〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
