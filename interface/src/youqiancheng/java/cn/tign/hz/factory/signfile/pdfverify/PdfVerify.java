package cn.tign.hz.factory.signfile.pdfverify;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.PdfVerifyResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕APIPDF文件验签
 * @author  澄泓
 * @date  2020/10/28 17:15
 * @version JDK1.7
 */
public class PdfVerify extends Request<PdfVerifyResponse> {
    @JSONField(serialize = false)
    private String fileId;
    @JSONField(serialize = false)
    private String flowId;

    private PdfVerify(){};
    public PdfVerify(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    @Override
    public void build() {
        StringBuilder url=new StringBuilder("/v1/documents/"+fileId+"/verify?");
        if(flowId!=null){
            url.append("&flowId="+flowId);
        }
        super.setUrl(url.toString());
        super.setRequestType(RequestType.GET);
    }
}
