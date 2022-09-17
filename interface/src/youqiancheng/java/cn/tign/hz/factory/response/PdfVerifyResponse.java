package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.data.PdfVerifyData;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:40
 * @version 
 */
public class PdfVerifyResponse extends Response {
    private PdfVerifyData data;

    public PdfVerifyData getData() {
        return data;
    }

    public void setData(PdfVerifyData data) {
        this.data = data;
    }
}
