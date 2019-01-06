package view.niudong.com.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;


import utils.AppInfoUtils;
import utils.Bus;
import view.niudong.com.demo.MainActivity;
import view.niudong.com.demo.MainTabActivity;
import view.niudong.com.demo.MyApplication;

/**
 * 名称：
 * Created by niudong on 2019/1/3.
 * Tel：18811793194view.niudong.com.demo.receiver.NotificationReceiver
 * VersionChange：5.8.5
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(MainTabActivity.NOTIFY_CLICK, intent.getAction())) {
            processJump(context);
        }
    }

    /**
     * 处理流程
     */
    private void processJump(Context mContext) {
        //判断app进程是否存活
        if (AppInfoUtils.isProcessExist(mContext, android.os.Process.myPid())) {
            if (AppInfoUtils.isApplicationBroughtToBackground(mContext)) {//后台
                Intent resultIntent = new Intent(mContext, MainTabActivity.class);
                mContext.startActivity(resultIntent);
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bus.getInstance().post(MainTabActivity.RECEVICE_DATA, "22");
                    }
                },200);

            } else {//前台
                if (AppInfoUtils.isActivityTop(MainTabActivity.class, mContext)) {
                    Toast.makeText(mContext, "我在顶部", Toast.LENGTH_SHORT).show();
                    //栈顶 刷新主页面
                } else {
                    Toast.makeText(mContext, "正在切换到顶部", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent(mContext, MainTabActivity.class);
                    mContext.startActivity(resultIntent);
                }
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                Bus.getInstance().post(MainTabActivity.RECEVICE_DATA, "22");
                    }
                },200);
            }
        } else {
            // 重启应用
            Intent launchIntent = mContext.getPackageManager().
                    getLaunchIntentForPackage(AppInfoUtils.getPackageName(mContext));
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Bundle args = new Bundle();
            args.putString("name", "电饭锅");
            args.putString("price", "58元");
            args.putString("detail", "这是一个好锅, 这是app进程不存在，先启动应用再启动Activity的");
//            launchIntent.putExtra(Constants.EXTRA_BUNDLE, args);
            mContext.startActivity(launchIntent);
        }
    }

    //判断app进程是否存活
//        if (AppInfoUtils.isProcessExist(mContext, android.os.Process.myPid())) {
//        if (!TextUtils.isEmpty(UserDataSource.getInstance().getUid())) {
//
//            if (AppInfoUtils.isApplicationBroughtToBackground(mContext)) {//后台
//                Intent resultIntent = new Intent(mContext, MainActivity.class);
//                mContext.startActivity(resultIntent);
//                processJumpPage(Integer.parseInt(goto_type));
//            } else {//前台
//                if (AppInfoUtils.isActivityTop(MainActivity.class, mContext)) {
//                    processJumpPage(Integer.parseInt(goto_type));
//                    //栈顶 刷新主页面
//                } else {
//                    Intent resultIntent = new Intent(mContext, MainActivity.class);
//                    mContext.startActivity(resultIntent);
//                    processJumpPage(Integer.parseInt(goto_type));
//                }
//            }
//        } else {
//            // 没有登陆 重启应用
//            if (AppInfoUtils.isApplicationBroughtToBackground(mContext)) {
//                if (AppInfoUtils.isActivityTop(LoginActivity.class, mContext)) {
//                    Intent resultIntent = new Intent(mContext, LoginActivity.class);
//                    mContext.startActivity(resultIntent);
//                } else {
//                    Intent launchIntent = mContext.getPackageManager().
//                            getLaunchIntentForPackage(AppInfoUtils.getPackageName(mContext));
//                    launchIntent.setFlags(
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(launchIntent);
//                }
//            }
//            //没有登陆且 前台
//        }
//    }

}
