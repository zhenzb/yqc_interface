package cn.tign.hz.factory.ocr;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.ocr.BankcardResponse;

/**
 * 实名认证银行卡OCR
 * @author  澄泓
 * @date  2020/11/3 16:16
 * @version JDK1.7
 */
public class Bankcard extends Request<BankcardResponse> {
    private String img;

    private Bankcard(){};
    public Bankcard(String img) {
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
        super.setUrl("/v2/identity/auth/api/ocr/bankcard");
        super.setRequestType(RequestType.POST);
    }
}
