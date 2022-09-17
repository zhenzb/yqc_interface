package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:08
 * @version 
 */
public class DeleteResults extends ArrayList {
    @Override
    public DeleteResult get(int index) {
        Object o = super.get(index);
        DeleteResult deleteResult = JSON.parseObject(o.toString(), DeleteResult.class);
        return deleteResult;
    }
}
