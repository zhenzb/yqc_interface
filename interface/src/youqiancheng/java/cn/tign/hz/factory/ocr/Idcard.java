package cn.tign.hz.factory.ocr;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ocr.IdcardResponse;

/**
 * 实名认证身份证OCR
 * @author  澄泓
 * @date  2020/11/3 16:09
 * @version JDK1.7
 */
public class Idcard extends Request<IdcardResponse> {
    private String infoImg;
    private String emblemImg;


    private Idcard(){};
    public Idcard(String infoImg) {
        this.infoImg = infoImg;
    }

    public String getInfoImg() {
        return infoImg;
    }

    public void setInfoImg(String infoImg) {
        this.infoImg = infoImg;
    }

    public String getEmblemImg() {
        return emblemImg;
    }

    public void setEmblemImg(String emblemImg) {
        this.emblemImg = emblemImg;
    }

    @Override
    public void build() {
        super.setUrl("/v2/identity/auth/api/ocr/idcard");
        super.setRequestType(RequestType.POST);
    }
}
