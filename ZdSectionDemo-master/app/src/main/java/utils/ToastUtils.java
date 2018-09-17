package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by niudong on 2017/6/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class ToastUtils {
    private static Toast toast;

    //静态单列 避免泄露
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
