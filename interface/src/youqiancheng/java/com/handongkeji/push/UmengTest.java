package com.handongkeji.push;

public class UmengTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			UmengPush.sendIOSCustomizedcast("yqc_type", "yqc_78", "百川","百川biubiubiu", "papapa", "1","1",null,null,null);  // IOS 单推
			UmengPush.sendAndroidCustomizedcast("yqc_type", "yqc_78", "米5555566", "阿福biubiubiu", "阿福biubiubiu", "1","1",null,null,null); // android 单推


			UmengPush.sendIOSBroadcast("biubiubiu", "papapa", "11111111全推测试", "0","99",null);   // IOS全推

			UmengPush.sendAndroidBroadcast("biubiubiu", "papapa", "1111111111全推测试", "0","99",null);  //  android 全推

			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
