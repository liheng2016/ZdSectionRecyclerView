package utils;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.niudong.com.demo.MyApplication;

/**
 * 名称：
 * Created by niudong on 2017/12/14.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class MyUtil {

    /**
     * 截取一段字符
     */
    public static String substring(String text, int beginIndex, int endIndex) {
        try {
            return text.substring(beginIndex, endIndex);
        } catch (Exception e) {
            Logger.printExceptionStackTrace(e);
        }
        return null;
    }


    /**
     * 多个数相加
     * @param data
     * @return
     */
    public static String Sum(List<String> data, int scale){
        BigDecimal resultBd = new BigDecimal("0");
        int size = 0;
        if(data != null && (size=data.size()) > 0){
            for(int i = 0; i < size;i++){
                resultBd = add(resultBd,data.get(i),scale);
            }
        }

        return resultBd.abs().toString();
    }



    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        double value = 0.0d;
        try {
            if (scale < 0) {
                throw new IllegalArgumentException(
                        "The scale must be a positive integer or zero");
            }
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            value = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 判断当前时间是否在09:00-09:28  或者16:00-16:10之间
     *
     * @return
     */
    public static boolean nowTimeIsJingPrice(String mCurrTime) {
        boolean isIn = false;
        try {
            //获取当前时间
            int itime = Integer.parseInt(mCurrTime);
            if ((itime >= 900 && itime <= 928) || (itime >= 1600 && itime <= 1610)) {
                isIn = true;
            }
        } catch (Exception e) {
            ToastUtils.showToast(MyApplication.mContext, e.toString());
        }
        return isIn;
    }


    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, String v2, int scale){
        BigDecimal b2 = new BigDecimal(v2);
        return v1.add(b2).setScale(scale,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 将数据转换为 带万或亿单位
     */
    public static String convertF2UnitF1(float f) {
        DecimalFormat dFormat;
        String unit;
        String sig = "";
        if (f < 0) {
            sig = "-";
        } else if (f > 0) {
            sig = "+";
        }
        float newf = Math.abs(f);
        if (newf >= 10000000000f) {// 百亿以上
            newf = newf / 100000000f;
            dFormat = new DecimalFormat("0");
            unit = "亿";
        } else if (newf >= 1000000000f) {// 十亿以上
            newf = newf / 100000000f;
            dFormat = new DecimalFormat("0.0");
            unit = "亿";
        } else if (newf >= 100000000f) {// 一亿以上
            newf = newf / 100000000f;
            dFormat = new DecimalFormat("0.00");
            unit = "亿";
        } else if (newf >= 1000000f) {// 百万以上
            newf = newf / 10000f;
            dFormat = new DecimalFormat("0");
            unit = "万";
        } else if (newf >= 100000f) {// 十万以上
            newf = newf / 10000f;
            dFormat = new DecimalFormat("0.0");
            unit = "万";
        } else {
            dFormat = new DecimalFormat("0.00");
            unit = "";
        }
        return sig + dFormat.format(newf) + unit;
    }

    /**
     * 为小数点后不足两位的double数后面补零， 保留小数点后两位
     */
    public static String decimalbeforeAddTwoWord(double decimalDouble) {
        String strDouble = String.valueOf(decimalDouble);
        int decimalIndex = strDouble.indexOf(".");
        if (decimalIndex == -1) {
            return strDouble + ".00";
        } else {
            String subString = strDouble.substring(decimalIndex);
            int strLength = subString.length();
            if (strLength == 2) {
                return strDouble + "0";
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
                strDouble = decimalFormat.format(decimalDouble);// format//
                // 返回的是字符串
                return strDouble;
            }
        }
    }

    /**
     * 根据rise值,判断是否需要添加+-号、是否需要添加百分号
     *
     * @param rise         输入的值
     * @param isAddPrecent false: 不需要添加百分号,true:需要添加百分号
     * @param isAdd        false:不需要添加正负号,true:需要添加正负号
     * @return
     */
    public static String getValue(String rise, boolean isAddPrecent, boolean isAdd) {
        String value = rise;
        if (null != rise && !"--".equals(rise.trim()) && !"停牌".equals(rise.trim())) {
            rise = rise.trim();
            rise = rise.replaceAll("\t", "");
            rise = rise.replaceAll("%", "");
            if (!checkDouble(rise)) {
                return value;
            }
            value = rise;
            if (isAdd) {
                try {
                    double vals = Double.parseDouble(rise);
                    if (vals > 0) {
                        value = "+" + rise;
                    }
                } catch (Exception e) {
                    Logger.printExceptionStackTrace(e);
                }
            }
            value += (isAddPrecent && !"停牌".equals(value) ? "%" : "");
        }
        return value == null ? "--" : value;
    }
    /**
     * 判断是否是浮点数值
     */
    public static boolean checkDouble(String str) {

        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile("[-+]?[0-9.]+");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 将字符串解析为double数值
     *
     * @param value         将要解析的字符串
     * @param defaultDouble 解析失败时,默认值
     * @return 返回double
     */
    public static double parseDouble(String value, double defaultDouble) {
        try {
            if (null != value) return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            //
        }
        return defaultDouble;
    }
    /**
     * 设置View的可见性
     */
    public static void setVisibility(View view, int visibility) {
        if (null != view && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 判断原字符串是为空, 则获取一个默认字符串, 否则获取原字符串
     */
    public static String getDefaultString(String value, String defaultStr) {
        return TextUtils.isEmpty(value) ? defaultStr : value;
    }

    public static String getDefaultString(String value) {
        return getDefaultString(value, "--");
    }


}
