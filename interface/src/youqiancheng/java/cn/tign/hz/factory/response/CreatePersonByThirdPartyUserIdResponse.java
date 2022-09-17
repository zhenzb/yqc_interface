package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CreatePersonByThirdPartyUserIdData;

/**
 * @description  个人账户创建响应类
 * @author  澄泓
 * @date  2020/10/23 10:48
 * @version JDK1.7
 */
public class CreatePersonByThirdPartyUserIdResponse extends Response{
    private CreatePersonByThirdPartyUserIdData data;

    public CreatePersonByThirdPartyUserIdData getData() {
        return data;
    }
    public void setData(CreatePersonByThirdPartyUserIdData data) {
        this.data = data;
    }
}