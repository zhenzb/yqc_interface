package cn.tign.hz.factory.ocr;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ocr.LicenseResponse;

/**
 * 实名认证营业执照OCR
 * @author  澄泓
 * @date  2020/11/3 16:22
 * @version JDK1.7
 */
public class License extends Request<LicenseResponse> {

    private String img;

    private License(){};
    public License(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/ocr/license");
        super.setRequestType(RequestType.POST);
    }
}
