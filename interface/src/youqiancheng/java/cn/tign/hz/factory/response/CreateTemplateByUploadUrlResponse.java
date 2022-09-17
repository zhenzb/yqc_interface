package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CreateTemplateByUploadUrlData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 16:47
 * @version 
 */
public class CreateTemplateByUploadUrlResponse extends Response {
    private CreateTemplateByUploadUrlData data;

    public CreateTemplateByUploadUrlData getData() {
        return data;
    }

    public void setData(CreateTemplateByUploadUrlData data) {
        this.data = data;
    }
}
