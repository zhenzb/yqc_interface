package cn.tign.hz.factory.response.ocr;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.DrivingPermitData;

/**
 * 实名认证行驶证OCR响应
 * @author  澄泓
 * @date  2020/11/3 16:32
 * @version JDK1.7
 */
public class DrivingPermitResponse extends Response {
    private DrivingPermitData data;

    public DrivingPermitData getData() {
        return data;
    }

    public void setData(DrivingPermitData data) {
        this.data = data;
    }
}
