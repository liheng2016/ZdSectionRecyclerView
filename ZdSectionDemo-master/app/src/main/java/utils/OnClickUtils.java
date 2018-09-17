package utils;

import android.view.View;

/**
 * Created by NiuDong on 2017/8/31.
 * <p>
 * 防止过快点击造成多次事件执行（防止按钮重复点击）
 */

public class OnClickUtils {

    // 两次点击按钮之间的点击间隔不能少于2000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    //最后点击的时间
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    public static boolean avoidRepeatClick(View view){
        boolean flag = false;
        long lastTime = view.getTag(-1)==null?0:(long)view.getTag(-1);
        if (System.currentTimeMillis()-lastTime>1000){
            flag = true;
        }
        view.setTag(-1,System.currentTimeMillis());
        return flag;
    }

}
