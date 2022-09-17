package cn.tign.hz.factory.filetemplate;

import cn.tign.hz.bean.SimpleFormFields;
import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.CreateFileByTemplateResponse;

/**
 * @description  轩辕API通过模板创建文件
 * @author  澄泓
 * @date  2020/10/27 10:37
 * @version JDK1.7
 */
public class CreateFileByTemplate extends Request<CreateFileByTemplateResponse> {
    private String name;
    private String templateId;
    private SimpleFormFields simpleFormFields;

    //禁止构造无参对象
    private CreateFileByTemplate() {}

    public CreateFileByTemplate(String name, String templateId, SimpleFormFields simpleFormFields) {
        this.name = name;
        this.templateId = templateId;
        this.simpleFormFields = simpleFormFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public SimpleFormFields getSimpleFormFields() {
        return simpleFormFields;
    }

    public void setSimpleFormFields(SimpleFormFields simpleFormFields) {
        this.simpleFormFields = simpleFormFields;
    }

    @Override
    public void build() {
        super.setUrl("/v1/files/createByTemplate");
        super.setRequestType(RequestType.POST);
    }
}
