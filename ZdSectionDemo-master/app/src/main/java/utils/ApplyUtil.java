package utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ApplyUtil {
    public final static String Number_Chinese = "1"; //2012年2月16日
    public final static String Number_Point = "2";//2010.2.16

    /**
     * 判断数据是否为空
     *
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.trim().equals("")) {
            return true;
        }
        return false;
    }


    /**
     * 为当前日期增加month月
     *
     * @param month
     * @return
     * @time 2012-6-6 上午10:57:32
     */
    public static String addMonth(int month) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sFmt = new SimpleDateFormat("yyyy年MM月dd日");
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        return sFmt.format(cal.getTime());
    }


    /**
     * 根据要求将时间转化成字符串
     *
     * @param date  8位字符
     * @param style 样式
     * @return
     */
    public static String parseDate2String(String date, String style) {
        String formatDate = "";
        int len = 0;
        if (!ApplyUtil.isEmpty(date)) {
            len = date.trim().length();
            if (len == 8) {
                if (Number_Chinese.equals(style)) {
                    formatDate = getNumberChineseDate(date);
                }
                if (Number_Point.equals(style)) {
                    formatDate = getNumberPointDate(date);
                }
            } else {
                formatDate = date;
            }
        }
        return formatDate;
    }

    /**
     * 格式化日期为：2012年2月16日
     *
     * @param date
     * @return
     */
    private static String getNumberChineseDate(String date) {
        String year = date.substring(0, 4);
        String month = leaveZero(date.substring(4, 6));
        String day = leaveZero(date.substring(6, 8));
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 格式化日期为：2012年2月16日
     *
     * @param date
     * @return
     */
    private static String getNumberPointDate(String date) {
        String year = date.substring(0, 4);
        String month = leaveZero(date.substring(4, 6));
        String day = leaveZero(date.substring(6, 8));
        return year + "." + month + "." + day;
    }

    /**
     * 去掉前面含有的0
     *
     * @param str
     * @return
     */
    private static String leaveZero(String str) {
        if (str.length() == 2 && str.charAt(0) == '0') {
            return str.substring(1, 2);
        }
        return str;
    }

    /**
     * 获得文件的类型
     *
     * @param file 文件的全路径
     * @return
     * @author ly
     * @date 2012-4-9
     */
    public static String getSuffix(String file) {
        if (isEmpty(file) || file.indexOf(".") == -1 || file.indexOf(".") == file.length() - 1)
            return "";
        System.out.println(file.length());
        System.out.println(file.lastIndexOf("."));
        String suffix = file.substring(file.lastIndexOf("."), file.length());
        return suffix.toLowerCase();
    }


    /**
     * 邮箱校验
     *
     * @param email
     * @return true：邮箱格式正确; false：邮箱格式错误
     */
    public static boolean isEmail(String email) {
        //String str = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//带横杠的出错
        //String str = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";//带下划线出错
//      Pattern p = Pattern.compile(str);
//        Matcher m = p.matcher(email.trim());
//        return m.matches();
        //Pattern pattern = Pattern.compile("[0-9a-zA-Z]*.[0-9a-zA-Z]*@[a-zA-Z]*.[a-zA-Z]*", Pattern.LITERAL);
        if (email == null) {
            return false;
        }

        //验证开始

        //不能有连续的.
        if (email.indexOf("..") != -1) {
            return false;
        }

        //必须带有@
        int atCharacter = email.indexOf("@");
        if (atCharacter == -1) {
            return false;
        }

        //最后一个.必须在@之后,且不能连续出现
        if (atCharacter > email.lastIndexOf('.') || atCharacter + 1 == email.lastIndexOf('.')) {
            return false;
        }

        //不能以.,@结束和开始
        if (email.endsWith(".") || email.endsWith("@") || email.startsWith(".") || email.startsWith("@")) {
            return false;
        }
        return true;

    }

    /**
     * 手机号校验
     *
     * @param mobiles
     * @return true：手机号格式正确; false:手机号格式不正确
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        String str = "^1[3|4|5|8][0-9][0-9]{8}$";
        //String str = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobiles.trim());
        return m.matches() && mobiles.length() == 11;
    }
}
