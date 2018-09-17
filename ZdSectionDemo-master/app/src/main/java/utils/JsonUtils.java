package utils;

import android.content.Context;

import com.google.gson.Gson;

import entity.HotelEntity;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 */

public class JsonUtils {

    public static HotelEntity analysisJsonFile(Context context,String fileName){
        String content = FileUtils.readJsonFile(context,fileName);
        Gson gson = new Gson();
        HotelEntity entity = gson.fromJson(content, HotelEntity.class);
        return  entity;

    }
}
