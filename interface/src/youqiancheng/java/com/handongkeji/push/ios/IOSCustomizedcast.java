package com.handongkeji.push.ios;

import com.handongkeji.push.IOSNotification;

/**
 * 功能描述: <br>
 * 〈  customizedcast，通过alias进行推送，包括以下两种case:
 * 	    - alias: 对单个或者多个alias进行推送
 * 	     - file_id: 将alias存放到文件后，根据file_id来推送〉
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class IOSCustomizedcast extends IOSNotification {
	public IOSCustomizedcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "customizedcast");
	}

	public void setAlias(String alias,String aliasType) throws Exception {
    	setPredefinedKeyValue("alias", alias);
    	setPredefinedKeyValue("alias_type", aliasType);
    }

	public void setFileId(String fileId, String aliasType) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
		setPredefinedKeyValue("alias_type", aliasType);
	}

}
