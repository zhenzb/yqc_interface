package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.DataVerifyData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:19
 * @version 
 */
public class DataVerifyResponse extends Response {
    private DataVerifyData data;

    public DataVerifyData getData() {
        return data;
    }

    public void setData(DataVerifyData data) {
        this.data = data;
    }
}
