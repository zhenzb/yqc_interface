package cn.tign.hz.factory.response.other;

import cn.tign.hz.factory.response.data.SearchWordsPositionData;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 17:24
 * @version 
 */
public class SearchWordsPositionDataList extends ArrayList {
    @Override
    public SearchWordsPositionData get(int index) {
        Object o = super.get(index);
        SearchWordsPositionData searchWordsPositionData = JSON.parseObject(o.toString(), SearchWordsPositionData.class);
        return searchWordsPositionData;
    }
}
