package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.GetFileSignUrlData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:52
 * @version 
 */
public class GetFileSignUrlResponse extends Response {
    private GetFileSignUrlData data;

    public GetFileSignUrlData getData() {
        return data;
    }

    public void setData(GetFileSignUrlData data) {
        this.data = data;
    }
}
