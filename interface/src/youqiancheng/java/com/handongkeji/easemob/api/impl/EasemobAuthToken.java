package com.handongkeji.easemob.api.impl;


import com.handongkeji.easemob.api.AuthTokenAPI;
import com.handongkeji.easemob.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
