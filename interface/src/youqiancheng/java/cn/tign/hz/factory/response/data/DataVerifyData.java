package cn.tign.hz.factory.response.data;

import cn.tign.hz.factory.response.other.DataSignInfo;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:20
 * @version 
 */
public class DataVerifyData {
    private DataSignInfo signInfo;

    public DataSignInfo getSignInfo() {
        return signInfo;
    }

    public void setSignInfo(DataSignInfo signInfo) {
        this.signInfo = signInfo;
    }
}
