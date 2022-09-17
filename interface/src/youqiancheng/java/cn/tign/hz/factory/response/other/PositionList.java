package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 17:13
 * @version 
 */
public class PositionList extends ArrayList {
    @Override
    public Position get(int index) {
        Object o = super.get(index);
        Position position = JSON.parseObject(o.toString(), Position.class);
        return position;
    }
}
