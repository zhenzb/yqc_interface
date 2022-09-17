package com.youqiancheng.ability;

import com.handongkeji.config.exception.JsonException;
import com.youqiancheng.util.HttpUtil;
import com.youqiancheng.vo.result.ResultEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName OcrAbility
 * @Description TODO
 * @Author zzb
 * @Date 2021/11/6 17:11
 * @Version 1.0
 **/
@Component
public class OcrAbility {

    private String key="2ac8cbea2ea4e2865708";
    private String secret="5d3c34637492a4c0669f";

    public JSONObject ocrIdCard(String picUrl){
        HttpUtil.init();
        HttpUtil httpUtil = new HttpUtil();
        String url = "http://api.guaqb.cn/v1/AI/card.php?url="+picUrl+"&key="+key+
                "&secret="+secret;
        Map<String, String> stringStringMap = httpUtil.get(url);
        String result = stringStringMap.get("result");
        JSONObject jsonObject = null;
        try {
            JSONObject parse = JSONObject.fromObject(result);
            JSONArray cards = parse.getJSONArray("cards");
             jsonObject = cards.getJSONObject(0);
        }catch (Exception e){
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST, "身份证未识别！请重新上传");
        }
        return jsonObject;
    }
}
