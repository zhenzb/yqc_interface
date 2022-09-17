package cn.tign.hz.factory.ocr;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ocr.DrivingPermitResponse;

/**
 * 实名认证行驶证OCR
 * @author  澄泓
 * @date  2020/11/3 16:30
 * @version JDK1.7
 */
public class DrivingPermit extends Request<DrivingPermitResponse> {
    private String image;
    private String requestId;

    private DrivingPermit(){};
    public DrivingPermit(String image) {
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
        super.setUrl("/v2/identity/auth/api/ocr/drivingPermit");
        super.setRequestType(RequestType.POST);
    }
}
