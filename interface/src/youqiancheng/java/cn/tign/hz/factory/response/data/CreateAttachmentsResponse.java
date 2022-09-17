package cn.tign.hz.factory.response.data;

import cn.tign.hz.factory.response.Response;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:11
 * @version 
 */
public class CreateAttachmentsResponse extends Response {
    private CreateAttachmentsData data;

    public CreateAttachmentsData getData() {
        return data;
    }

    public void setData(CreateAttachmentsData data) {
        this.data = data;
    }
}
