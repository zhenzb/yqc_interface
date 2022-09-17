package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 11:11
 * @version 
 */
public class Signfields extends ArrayList {
    @Override
    public Signfield get(int index) {
        Object o = super.get(index);
        Signfield signfield = JSON.parseObject(o.toString(), Signfield.class);
        return signfield;
    }
}
