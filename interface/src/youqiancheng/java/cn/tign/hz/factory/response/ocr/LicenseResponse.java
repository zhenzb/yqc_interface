package cn.tign.hz.factory.response.ocr;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.LicenseData;

/**
 * 实名认证营业执照OCR响应
 * @author  澄泓
 * @date  2020/11/3 16:23
 * @version JDK1.7
 */
public class LicenseResponse extends Response {
    private LicenseData data;

    public LicenseData getData() {
        return data;
    }

    public void setData(LicenseData data) {
        this.data = data;
    }
}
