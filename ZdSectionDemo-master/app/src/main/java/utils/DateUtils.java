package utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 名称：
 * Created by niudong on 2018/6/8.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class DateUtils {
    /**
     * yyyyMMddHHmmss   To  yyyy-MM-dd   2018-05-23
     */
    public static String formatToString(String date) {
        if (TextUtils.isEmpty(date) || date.length() < 8) return date;
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(month).append("-").append(day);
        return sb.toString();
    }

    /*
     * 获得当前时间
	 */
    public static String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");// MM-dd
        // 当前日期
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }
    /**
     * 6月/2018
     */

    public static String dateToString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");// MM-dd
        // 当前日期
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        if (TextUtils.isEmpty(date)||date.length()<6);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
           String result= date.substring(Integer.parseInt( month) >10?4:5, 6);
        StringBuilder sb = new StringBuilder();
        sb.append(result).append("月/").append(year);
        return sb.toString();
    }


}
