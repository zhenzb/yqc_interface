package cn.tign.hz.factory.response.ocr;

import cn.tign.hz.factory.response.Response;
import cn.tign.hz.factory.response.data.DrivingLicenceData;

/**
 * 实名认证驾驶证OCR响应
 * @author  澄泓
 * @date  2020/11/3 16:27
 * @version JDK1.7
 */
public class DrivingLicenceResponse extends Response {
    private DrivingLicenceData data;

    public DrivingLicenceData getData() {
        return data;
    }

    public void setData(DrivingLicenceData data) {
        this.data = data;
    }
}
