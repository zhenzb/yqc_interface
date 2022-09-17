package com.youqiancheng.util;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.EntyPage;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class QueryMap extends LinkedHashMap<String, Object> {

    public QueryMap(){
    }
    public QueryMap(int  num){
        if(num==StatusConstant.CreatFlag.delete.getCode()){
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        }
        else if(num==StatusConstant.CreatFlag.status.getCode()){
            this.put("status", StatusConstant.enable.getCode());
        }else{
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
            this.put("status", StatusConstant.enable.getCode());
        }
    }
    public QueryMap(EntyPage page){
        this.put("page", page);
    }
    public QueryMap(EntyPage page,int  num){
        if(num==StatusConstant.CreatFlag.delete.getCode()){
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        }
        else if(num==StatusConstant.CreatFlag.status.getCode()){
            this.put("status", StatusConstant.enable.getCode());
        }else{
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
            this.put("status", StatusConstant.enable.getCode());
        }
        this.put("page", page);

    }

    public QueryMap(Object class1 ,EntyPage page) {
        try {
            Map<String,Object> map= PropertyUtils.describe(class1);
//            //排序转换
//            if(!StringUtils.isEmpty(map.get("sort").toString()))
//            {
//                String sort=map.get("sort").toString();
//                String sorts="";
//                if(sort.contains(","))
//                {
//                    String[] str=sort.split(",");
//                    for (int i = 0; i <str.length ; i++) {
//                        sorts+=PropertyToFieldUtils.propertyToField(str[i])+",";
//                    }
//                    sorts=sorts.substring(0,sorts.length()-1);
//                }else
//                {
//                    sorts=PropertyToFieldUtils.propertyToField(map.get("sort").toString());
//                }
//                map.put("sort",sorts);
//            }
            this.putAll(map);
            this.put("page", page);
        }catch (Exception e){
            e.printStackTrace();
            throw new JsonException(ResultEnum.CHANGE_FAIL,"实体转换map失败");
        }

    }

    public QueryMap(Object class1, EntyPage page, int num){

        if(num==StatusConstant.CreatFlag.delete.getCode()){
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        }
        else if(num==StatusConstant.CreatFlag.status.getCode()){
            this.put("status", StatusConstant.enable.getCode());
        }else{
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
            this.put("status", StatusConstant.enable.getCode());
        }
        try {
            Map<String,Object> map= PropertyUtils.describe(class1);
//            if(map.containsKey("sort"))
//            {
//                //排序转换
//                if(!StringUtils.isEmpty(map.get("sort").toString()))
//                {
//                    String sort=map.get("sort").toString();
//                    String sorts="";
//                    if(sort.contains(","))
//                    {
//                        String[] str=sort.split(",");
//                        for (int i = 0; i <str.length ; i++) {
//                            sorts+=PropertyToFieldUtils.propertyToField(str[i])+",";
//                        }
//                        sorts=sorts.substring(0,sorts.length()-1);
//                    }else
//                    {
//                        sorts=PropertyToFieldUtils.propertyToField(map.get("sort").toString());
//                    }
//                    map.put("sort",sorts);
//                }
//            }
            this.putAll(map);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new JsonException(ResultEnum.CHANGE_FAIL,"实体转换map失败");
        }
        this.put("page", page);
    }

    public QueryMap(Object class1) {
        try {
            Map<String,Object> map=PropertyUtils.describe(class1);
//            //排序转换
//            if(!StringUtils.isEmpty(map.get("sort").toString()))
//            {
//                String sort=map.get("sort").toString();
//                String sorts="";
//                if(sort.contains(","))
//                {
//                    String[] str=sort.split(",");
//                    for (int i = 0; i <str.length ; i++) {
//                        sorts+=PropertyToFieldUtils.propertyToField(str[i])+",";
//                    }
//                    sorts=sorts.substring(0,sorts.length()-1);
//                }else
//                {
//                    sorts=PropertyToFieldUtils.propertyToField(map.get("sort").toString());
//                }
//                map.put("sort",sorts);
//            }
            this.putAll(map);
        }catch (Exception e){
            e.printStackTrace();
            throw new JsonException(ResultEnum.CHANGE_FAIL,"实体转换map失败");
        }

    }

    public QueryMap(Object class1, int num){

        if(num==StatusConstant.CreatFlag.delete.getCode()){
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
        }
        else if(num==StatusConstant.CreatFlag.status.getCode()){
            this.put("status", StatusConstant.enable.getCode());
        }else{
            this.put("deleteFlag",StatusConstant.DeleteFlag.un_delete.getCode());
            this.put("status", StatusConstant.enable.getCode());
        }
        try {
            Map<String,Object> map=PropertyUtils.describe(class1);
//            if(map.containsKey("sort"))
//            {
//                //排序转换
//                if(!StringUtils.isEmpty(map.get("sort").toString()))
//                {
//                    String sort=map.get("sort").toString();
//                    String sorts="";
//                    if(sort.contains(","))
//                    {
//                        String[] str=sort.split(",");
//                        for (int i = 0; i <str.length ; i++) {
//                            sorts+=PropertyToFieldUtils.propertyToField(str[i])+",";
//                        }
//                        sorts=sorts.substring(0,sorts.length()-1);
//                    }else
//                    {
//                        sorts=PropertyToFieldUtils.propertyToField(map.get("sort").toString());
//                    }
//                    map.put("sort",sorts);
//                }
//            }
            this.putAll(map);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new JsonException(ResultEnum.CHANGE_FAIL,"实体转换map失败");
        }
    }

}
