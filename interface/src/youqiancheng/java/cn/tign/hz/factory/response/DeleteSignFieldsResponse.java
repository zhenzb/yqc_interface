package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.DeleteSignFieldsData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:07
 * @version 
 */
public class DeleteSignFieldsResponse extends Response {
    private DeleteSignFieldsData data;

    public DeleteSignFieldsData getData() {
        return data;
    }

    public void setData(DeleteSignFieldsData data) {
        this.data = data;
    }
}
