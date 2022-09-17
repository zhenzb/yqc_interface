package com.handongkeji.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * 
 * @author victor Json工具类
 * 
 */
public class JsonUtil {
	
	
	  
    /** 
     * 将一个json字串转为list 
     * @param answer
     * @return 
     */  
	public static List<Long> converMsgIdFormString(String answer){  
        if (answer == null || answer.equals(""))  
            return new ArrayList<Long>();  
  
        JSONArray jsonArray = JSONArray.fromObject(answer);  
        @SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) JSONArray.toCollection(jsonArray, 
                Long.class);  
          
        return list;  
    }  
      

	

	/**
	 * 从json字符串中获取属性值
	 * 
	 * @param str
	 * @param attrName
	 * @return
	 */
	public static String getAttrValueFromStr(String str, String attrName) {
		String val;
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			val = jsonObject.getString(attrName);
		} catch (Exception e) {
			val = null;
		}
		return val;
	}

	public static Object jsonString2Object(String jsonString, Class<?> pojoCalss)
	  {
	    JSONObject jsonObject = JSONObject.fromObject(jsonString);

	    Object pojo = JSONObject.toBean(jsonObject, pojoCalss);

	    return pojo;
	  }

	  public static String object2JsonString(Object javaObj)
	  {
	    JSONObject json = JSONObject.fromObject(javaObj);

	    return json.toString();
	  }

	  public static List<Object> jsonList2JavaList(String jsonString, Class<?> pojoClass)
	  {
	    JSONArray jsonArray = JSONArray.fromObject(jsonString);

	    List<Object> list = new ArrayList<Object>();

	    for (int i = 0; i < jsonArray.size(); ++i)
	    {
	      JSONObject jsonObject = jsonArray.getJSONObject(i);

	      Object pojoValue = JSONObject.toBean(jsonObject, pojoClass);

	      list.add(pojoValue);
	    }

	    return list;
	  }

	  public static String javaList2JsonList(List<?> list)
	  {
	    JSONArray jsonArray = JSONArray.fromObject(list);
	    return jsonArray.toString();
	  }

	  
	    public static Map<String, Object> parseJSON2Map(String jsonStr){  
	        Map<String, Object> map = new HashMap<String, Object>();  
	        //最外层解析  
	        JSONObject json = JSONObject.fromObject(jsonStr);  
	        for(Object k : json.keySet()){  
	            Object v = json.get(k);   
	            //如果内层还是数组的话，继续解析  
	            if(v instanceof JSONArray){  
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
	                @SuppressWarnings("unchecked")
					Iterator<JSONObject> it = ((JSONArray) v).iterator();  
	                while(it.hasNext()){  
	                    JSONObject json2 = it.next();  
	                    list.add(parseJSON2Map(json2.toString()));  
	                }  
	                map.put(k.toString(), list);  
//	                break label139;
	            } else {  
	                map.put(k.toString(), v);  
//	                label139: map.put(k.toString(), v);
	            }  
	        }  
	        return map;  
	    }  
	      
}
