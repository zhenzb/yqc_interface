package cn.tign.hz.factory.response.other;
/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:46
 * @version 
 */
public class PdfSignInfo {
    private PdfCert cert;
    private PdfSignature signature;
    private String sealData;

    public String getSealData() {
        return sealData;
    }

    public void setSealData(String sealData) {
        this.sealData = sealData;
    }

    public PdfCert getCert() {
        return cert;
    }

    public void setCert(PdfCert cert) {
        this.cert = cert;
    }

    public PdfSignature getSignature() {
        return signature;
    }

    public void setSignature(PdfSignature signature) {
        this.signature = signature;
    }
}
