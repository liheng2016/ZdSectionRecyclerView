package contants;

import android.graphics.Color;

/**
 * Created by niudong on 2017/5/10.
 */

public class ConfigInfo {
    //    IP和端口号
    private static String HOST = "https://xincaitong.com:8443/";
    private static String buglyId="ef6e50400d";


    public static void setHOST(String HOST) {
        ConfigInfo.HOST = HOST;
    }

    public static String getHOST() {
        return HOST;
    }

    public static final int COLOR_YELLOW = Color.parseColor("#ffaa65");
    public static final int COLOR_31B6DF = Color.parseColor("#31b6df");
    public static final int COLOR_WHITE = Color.parseColor("#ffffff");
    public static final int COLOR_99000000 = Color.parseColor("#99000000");
    public static final int COLOR_WHITE_BACKGROUND = Color.parseColor("#f0eff4");
    public static final int COLOR_HK_333333 = Color.parseColor("#333333");
    public static final int COLOR_HK_A3A8AF = Color.parseColor("#a3a8af");
    public static final int COLOR_HK_7D8187 = Color.parseColor("#7d8187");
    public static final int COLOR_HK_01A5E0 = Color.parseColor("#01a5e0");

    public static String getBuglyId() {
        return buglyId;
    }


    /**
     * //WebView 界面URL
     * webView url
     */
    public static String getWebViewUrl() {
        return  "https://wap.mytour.vip";
    }
}
