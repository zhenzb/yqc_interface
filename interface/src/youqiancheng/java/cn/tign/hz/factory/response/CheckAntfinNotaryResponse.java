package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.CheckAntfinNotaryData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 15:54
 * @version 
 */
public class CheckAntfinNotaryResponse extends Response {
    private CheckAntfinNotaryData data;

    public CheckAntfinNotaryData getData() {
        return data;
    }

    public void setData(CheckAntfinNotaryData data) {
        this.data = data;
    }
}
