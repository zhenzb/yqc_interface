package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/29 18:21
 * @version 
 */
public class Seals extends ArrayList {
    @Override
    public Seal get(int index) {
        Object o = super.get(index);
        Seal seal= JSON.parseObject(o.toString(), Seal.class);
        return seal;
    }
}
