package com.handongkeji.push;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

public abstract class IOSNotification extends UmengNotification {

	// Keys can be set in the aps level
	protected static final HashSet<String> APS_KEYS = new HashSet<String>(
			Arrays.asList(new String[] { "alert", "badge", "sound", "content-available" }));

	@Override
	public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
		if (ROOT_KEYS.contains(key)) {
			// This key should be in the root level
			rootJson.put(key, value);
		} else if (APS_KEYS.contains(key)) {
			// This key should be in the aps level
			JSONObject apsJson = null;
			JSONObject payloadJson = null;
			if (rootJson.has("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			if (payloadJson.has("aps")) {
				apsJson = payloadJson.getJSONObject("aps");
			} else {
				apsJson = new JSONObject();
				payloadJson.put("aps", apsJson);
			}
			apsJson.put(key, value);
		} else if (POLICY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject policyJson = null;
			if (rootJson.has("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy", policyJson);
			}
			policyJson.put(key, value);
		} else {
			if (key == "payload" || key == "aps" || key == "policy") {
				throw new Exception(
						"You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknownd key: " + key);
			}
		}

		return true;
	}

	// Set customized key/value for IOS notification
	public boolean setCustomizedField(String key, String value) throws Exception {
		// rootJson.put(key, value);
		JSONObject payloadJson = null;
		if (rootJson.has("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		payloadJson.put(key, value);
		return true;
	}

	public void setAlert(String token) throws Exception {
		setPredefinedKeyValue("alert", token);
	}

	public void setBadge(Integer badge) throws Exception {
		setPredefinedKeyValue("badge", badge);
	}

	public void setSound(String sound) throws Exception {
		setPredefinedKeyValue("sound", sound);
	}

	public void setContentAvailable(Integer contentAvailable) throws Exception {
		setPredefinedKeyValue("content-available", contentAvailable);
	}


	/**  IOS 参数示例
	 {
	 "appkey":"xx",    // 必填，应用唯一标识
	 "timestamp":"xx", // 必填，时间戳，10位或者13位均可，时间戳有效期为10分钟
	 "type":"xx",      // 必填，消息发送类型,其值可以为:
						 //   unicast-单播
						 //   listcast-列播，要求不超过500个device_token
						 //   filecast-文件播，多个device_token可通过文件形式批量发送
						 //   broadcast-广播
						 //   groupcast-组播，按照filter筛选用户群, 请参照filter参数
						 //   customizedcast，通过alias进行推送，包括以下两种case:
						 //     - alias: 对单个或者多个alias进行推送
						 //     - file_id: 将alias存放到文件后，根据file_id来推送
	 "device_tokens":"xx", // 当type=unicast时, 必填, 表示指定的单个设备
	 						// 当type=listcast时, 必填, 要求不超过500个, 以英文逗号分隔
	 "alias_type": "xx", // 当type=customizedcast时, 必填
						 // alias的类型, alias_type可由开发者自定义, 开发者在SDK中
						 // 调用setAlias(alias, alias_type)时所设置的alias_type
	 "alias":"xx", // 当type=customizedcast时, 选填(此参数和file_id二选一)
					 // 开发者填写自己的alias, 要求不超过500个alias, 多个alias以英文逗号间隔
					 // 在SDK中调用setAlias(alias, alias_type)时所设置的alias
	 "file_id":"xx", // 当type=filecast时，必填，file内容为多条device_token，以回车符分割
					 // 当type=customizedcast时，选填(此参数和alias二选一)
					 //   file内容为多条alias，以回车符分隔。注意同一个文件内的alias所对应
					 //   的alias_type必须和接口参数alias_type一致。
					 // 使用文件播需要先调用文件上传接口获取file_id，参照"2.4文件上传接口"
	 "filter":{}, // 当type=groupcast时，必填，用户筛选条件，如用户标签、渠道等，参考附录G。
	 "payload":   // 必填，JSON格式，具体消息内容(iOS最大为2012B)
		 {
		 "aps":      // 必填，严格按照APNs定义来填写
	 		{
				 "alert":""/{ // 当content-available=1时(静默推送)，可选; 否则必填。
								// 可为JSON类型和字符串类型
						 "title":"title",
						 "subtitle":"subtitle",
						 "body":"body"
				 }
				 "badge": xx,           // 可选
				 "sound": "xx",         // 可选
				 "content-available":1  // 可选，代表静默推送
				 "category": "xx",      // 可选，注意: ios8才支持该字段。
	 		},
	 	  "key1":"value1",       // 可选，用户自定义内容, "d","p"为友盟保留字段，
	 						// key不可以是"d","p"
	     "key2":"value2",
				 ...
	 },
	 "policy":               // 可选，发送策略
	 {
		 "start_time":"xx",   // 可选，定时发送时间，若不填写表示立即发送。
							 // 定时发送时间不能小于当前时间
							 // 格式: "yyyy-MM-dd HH:mm:ss"。
							 // 注意，start_time只对任务生效。
		 "expire_time":"xx",  // 可选，消息过期时间，其值不可小于发送时间或者
						 // start_time(如果填写了的话),
						 // 如果不填写此参数，默认为3天后过期。格式同start_time
		 "out_biz_no": "xx"   // 可选，开发者对消息的唯一标识，服务器会根据这个标识避免重复发送。
							 // 有些情况下（例如网络异常）开发者可能会重复调用API导致
							 // 消息多次下发到客户端。如果需要处理这种情况，可以考虑此参数。
							 // 注意，out_biz_no只对任务生效。
		 "apns_collapse_id": "xx" // 可选，多条带有相同apns_collapse_id的消息，iOS设备仅展示
		 							// 最新的一条，字段长度不得超过64bytes
		 },
		 "production_mode":"true/false" // 可选，正式/测试模式。默认为true
										 // 测试模式只对“广播”、“组播”类消息生效，其他类型的消息任务（如“文件播”）不会走测试模式
										 // 测试模式只会将消息发给测试设备。测试设备需要到web上添加。
		 "description": "xx"      // 可选，发送消息描述，建议填写。
	 }
	 **/

}
