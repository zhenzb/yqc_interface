package cn.tign.hz.factory.signfile.documents;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.DeleteDocumentsResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API流程文档删除
 * @author  澄泓
 * @date  2020/10/28 11:23
 * @version JDK1.7
 */
public class DeleteDocuments extends Request<DeleteDocumentsResponse> {
    @JSONField(serialize = false)
    private String flowId;
    @JSONField(serialize = false)
    private String fileIds;

    private DeleteDocuments(){};
    public DeleteDocuments(String flowId, String fileIds) {
        this.flowId = flowId;
        this.fileIds = fileIds;
    }

    public String getFlowId() {
        return flowId;
    }

    public String getFileIds() {
        return fileIds;
    }

    @Override
    public void build() {
        super.setUrl("/v1/signflows/"+flowId+"/documents?fileIds="+fileIds);
        super.setRequestType(RequestType.DELETE);
    }
}
