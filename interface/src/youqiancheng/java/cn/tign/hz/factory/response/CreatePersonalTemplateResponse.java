package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CreateTemplateData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:12
 * @version 
 */
public class CreatePersonalTemplateResponse extends Response {
    private CreateTemplateData data;

    public CreateTemplateData getData() {
        return data;
    }

    public void setData(CreateTemplateData data) {
        this.data = data;
    }
}
