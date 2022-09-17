package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:34
 * @version 
 */
public class Docs extends ArrayList {
    @Override
    public Doc get(int index) {
        Object o = super.get(index);
        Doc doc = JSON.parseObject(o.toString(), Doc.class);
        return doc;
    }
}
