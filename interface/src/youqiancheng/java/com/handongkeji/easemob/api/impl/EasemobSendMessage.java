package com.handongkeji.easemob.api.impl;

import com.handongkeji.easemob.api.SendMessageAPI;
import com.handongkeji.easemob.comm.EasemobAPI;
import com.handongkeji.easemob.comm.OrgInfo;
import com.handongkeji.easemob.comm.ResponseHandler;
import com.handongkeji.easemob.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
