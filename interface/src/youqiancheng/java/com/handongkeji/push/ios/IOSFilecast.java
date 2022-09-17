package com.handongkeji.push.ios;

import com.handongkeji.push.IOSNotification;

/**
 * 功能描述: <br>
 * 〈filecast-文件播，多个device_token可通过文件形式批量发送〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class IOSFilecast extends IOSNotification {
	public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");
	}

	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
