package cn.tign.hz.factory.other;

import cn.tign.hz.enums.RequestType;
import cn.tign.hz.factory.request.Request;
import cn.tign.hz.factory.response.SearchWordsPositionResponse;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description  轩辕API搜索关键字坐标
 * @author  澄泓
 * @date  2020/10/28 17:58
 * @version JDK1.7
 */
public class SearchWordsPosition extends Request<SearchWordsPositionResponse> {
    @JSONField(serialize = false)
    private String fileId;
    @JSONField(serialize = false)
    private String keywords;

    private SearchWordsPosition(){};
    public SearchWordsPosition(String fileId, String keywords) {
        this.fileId = fileId;
        this.keywords = keywords;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public void build() {
        super.setUrl("/v1/documents/"+fileId+"/searchWordsPosition?keywords="+keywords);
        super.setRequestType(RequestType.GET);
    }
}
