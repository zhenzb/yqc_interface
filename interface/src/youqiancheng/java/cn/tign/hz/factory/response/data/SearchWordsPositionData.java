package cn.tign.hz.factory.response.data;

import cn.tign.hz.factory.response.other.PositionList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 17:12
 * @version 
 */
public class SearchWordsPositionData {
    private String fileId;
    private String keyword;
    private PositionList positionList;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public PositionList getPositionList() {
        return positionList;
    }

    public void setPositionList(PositionList positionList) {
        this.positionList = positionList;
    }
}
