package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.DataSignData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:18
 * @version 
 */
public class DataSignResponse extends Response {
    private DataSignData data;

    public DataSignData getData() {
        return data;
    }

    public void setData(DataSignData data) {
        this.data = data;
    }
}
