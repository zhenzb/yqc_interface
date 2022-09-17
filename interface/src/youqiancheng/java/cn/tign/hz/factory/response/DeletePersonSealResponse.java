package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.Data;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:26
 * @version 
 */
public class DeletePersonSealResponse extends Response {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
