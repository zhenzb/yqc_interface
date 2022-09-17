package cn.tign.hz.factory.response.ocr;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.IdcardData;

/**
 * 实名认证身份证OCR响应
 * @author  澄泓
 * @date  2020/11/3 16:12
 * @version JDK1.7
 */
public class IdcardResponse extends Response {
        private IdcardData data;

    public IdcardData getData() {
        return data;
    }

    public void setData(IdcardData data) {
        this.data = data;
    }
}
