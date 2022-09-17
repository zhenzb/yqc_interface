package cn.tign.hz.factory.response;

import cn.tign.hz.factory.response.other.SearchWordsPositionDataList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 17:11
 * @version 
 */
public class SearchWordsPositionResponse extends Response {
    private SearchWordsPositionDataList data;

    public SearchWordsPositionDataList getData() {
        return data;
    }

    public void setData(SearchWordsPositionDataList data) {
        this.data = data;
    }
}
