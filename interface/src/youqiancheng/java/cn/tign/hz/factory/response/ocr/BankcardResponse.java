package cn.tign.hz.factory.response.ocr;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.BankcardData;

/**
 * 实名认证银行卡OCR响应
 * @author  澄泓
 * @date  2020/11/3 16:18
 * @version JDK1.7
 */
public class BankcardResponse extends Response {
    private BankcardData data;

    public BankcardData getData() {
        return data;
    }

    public void setData(BankcardData data) {
        this.data = data;
    }
}
