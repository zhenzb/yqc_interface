package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.UpdatePersonAccountData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 11:55
 * @version 
 */
public class UpdatePersonAccountByAccountIdResponse extends Response {
    private UpdatePersonAccountData data;

    public UpdatePersonAccountData getData() {
        return data;
    }

    public void setData(UpdatePersonAccountData data) {
        this.data = data;
    }
}
