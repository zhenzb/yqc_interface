package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CreateFileByTemplateData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 16:45
 * @version 
 */
public class CreateFileByTemplateResponse extends Response {
    private CreateFileByTemplateData data;

    public CreateFileByTemplateData getData() {
        return data;
    }

    public void setData(CreateFileByTemplateData data) {
        this.data = data;
    }
}
