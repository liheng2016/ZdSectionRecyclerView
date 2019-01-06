package utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * 名称：
 * Created by niudong on 2019/1/3.
 * Tel：18811793194
 * VersionChange：5.8.5
 */
public class AppInfoUtils {
    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 后台转前台
     *
     * @param context
     */
    public static void notifyAppToFront(Context context, Activity activity) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo task : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if (task.topActivity.getPackageName().equals(context.getPackageName())) {
                try {
                    Intent resultIntent = new Intent(context, Class.forName(task.topActivity.getClassName()));
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(resultIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        //若没有找到运行的task，用户结束了task或被系统释放，则重新启动activity
        Intent resultIntent = new Intent(context, activity.getClass());
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(resultIntent);
    }

    /**
     * 判断某activity是否处于栈顶
     * 如果当前应用在前台好用 后台有些不灵敏
     *
     * @return true在栈顶 false不在栈顶
     */
    public static boolean isActivityTop(Class cls, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }

    /**
     * 程序是否正在运行
     *
     * @param context
     * @return
     */
    public static boolean isRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals("com.tailin.webapps") && info.baseActivity.getPackageName().equals("com.tailin.webapps")) {
                return true;
            }
        }
        return false;
    }

    /**
     *  判断进程是否存活
     */
    public static boolean isProcessExist(Context context, int pid) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists ;
        if (am != null) {
            lists = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : lists) {
                if (appProcess.pid == pid) {
                    Log.e("TAG","333333");
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
