package cn.tign.hz.factory.response.data;

import cn.tign.hz.factory.response.other.PdfSignInfos;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:41
 * @version 
 */
public class PdfVerifyData {
    private PdfSignInfos signInfos;

    public PdfSignInfos getSignInfos() {
        return signInfos;
    }

    public void setSignInfos(PdfSignInfos signInfos) {
        this.signInfos = signInfos;
    }
}
