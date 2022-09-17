package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.GetFileUploadUrlData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 16:41
 * @version 
 */
public class GetFileUploadUrlResponse extends Response {
    private GetFileUploadUrlData data;

    public GetFileUploadUrlData getData() {
        return data;
    }

    public void setData(GetFileUploadUrlData data) {
        this.data = data;
    }
}
