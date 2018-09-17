package utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by qiang on 17-11-3.
 */

public class LimitedMap<K,V> {

//    private TreeMap<String,String> treeMap = new TreeMap<String,String>();
    private TreeMap<String,String> treeMap;

    public LimitedMap(String json){
        if(TextUtils.isEmpty(json)){
            json = new JSONArray().toString();
        }
        treeMap = convertToMap(json);
    }


    public void add(String data){
        try {
            while (treeMap.entrySet().size()>=5){
                treeMap.remove(treeMap.firstKey());
            }
            treeMap.put(System.currentTimeMillis()+"",data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public List<String> getValueList(){
        ArrayList<String> list = new ArrayList<String>();
        try {
            //对key进行排序
            Set<String> keySet = treeMap.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);

            //按正序遍历出值
            for(String key : keyList){
                list.add(treeMap.get(key));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getReverseList(){
        ArrayList<String> list = new ArrayList<String>();
        try {
            //对key进行排序
            Set<String> keySet = treeMap.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);
            //按倒序遍历出值
            for(int i = keyList.size()-1 ; i >=0 ; i--){
                list.add(treeMap.get(keyList.get(i)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public JSONArray convertToJson(){
        JSONArray array = new JSONArray();
        try {
            //对key进行排序
            Set<String> keySet = treeMap.keySet();
            ArrayList<String> keyList = new ArrayList<String>(keySet);
            Collections.sort(keyList);

            //封装Json
            for (String key : keyList){
                JSONObject json = new JSONObject();
                json.put(key,treeMap.get(key));
                array.put(json);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    private TreeMap<String, String> convertToMap(String jsonArray){
        TreeMap<String,String> map = new TreeMap<String,String>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            for (int i = 0; i < array.length() ; i++){
                JSONObject obj = (JSONObject) array.get(i);
                String key = obj.keys().next();
                String value = obj.getString(key);
                map.put(key,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }





}
