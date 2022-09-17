package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.DownDocumentsData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:34
 * @version 
 */
public class DownDocumentsResponse extends Response {

    private DownDocumentsData data;

    public DownDocumentsData getData() {
        return data;
    }

    public void setData(DownDocumentsData data) {
        this.data = data;
    }
}
