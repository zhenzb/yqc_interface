package cn.tign.hz.factory.ocr;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ocr.DrivingLicenceResponse;

/**
 * 实名认证驾驶证OCR
 * @author  澄泓
 * @date  2020/11/3 16:26
 * @version JDK1.7
 */
public class DrivingLicence extends Request<DrivingLicenceResponse> {
    private String image;
    private String requestId;

    private DrivingLicence(){};
    public DrivingLicence(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/ocr/drivinglicence");
        super.setRequestType(RequestType.POST);
    }
}
