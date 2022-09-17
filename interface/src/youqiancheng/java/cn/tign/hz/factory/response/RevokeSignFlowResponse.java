package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.Data;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:28
 * @version 
 */
public class RevokeSignFlowResponse extends Response {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
