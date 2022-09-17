package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 17:14
 * @version 
 */
public class CoordinateList extends ArrayList {
    @Override
    public Coordinate get(int index) {
        Object o = super.get(index);
        Coordinate coordinate = JSON.parseObject(o.toString(), Coordinate.class);
        return coordinate;
    }
}
