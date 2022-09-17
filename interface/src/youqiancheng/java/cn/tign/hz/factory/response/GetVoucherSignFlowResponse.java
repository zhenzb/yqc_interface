package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.GetVoucherSignFlowData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:22
 * @version 
 */
public class GetVoucherSignFlowResponse extends Response {
    private GetVoucherSignFlowData data;

    public GetVoucherSignFlowData getData() {
        return data;
    }

    public void setData(GetVoucherSignFlowData data) {
        this.data = data;
    }
}
