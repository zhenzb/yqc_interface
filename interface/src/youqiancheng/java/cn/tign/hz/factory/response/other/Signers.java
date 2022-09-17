package cn.tign.hz.factory.response.other;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @description  轩辕API
 * @author  澄泓
 * @date  2020/10/30 10:57
 * @version 
 */
public class Signers extends ArrayList {
    @Override
    public Signer get(int index) {
        Object o = super.get(index);
        Signer signer = JSON.parseObject(o.toString(), Signer.class);
        return signer;
    }
}
