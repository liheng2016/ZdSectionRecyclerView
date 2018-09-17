package view.niudong.com.demo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import utils.ToastUtils;
import view.niudong.com.demo.MainActivity;
import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/4/13.
 */


public class MyService extends Service {
    private static final String TAG = "MyService";
    public DownLoadBinder builder = new DownLoadBinder();

    // DownLoadBuilder builder=
    public class DownLoadBinder extends Binder {

        public void startDownload() {
            Log.d(TAG, "startDownload: ");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress: ");

            return 1;
        }
    }

    public MyService() {
    }

    /**
     * 第一次执行会调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentText("My is title")
                .setContentTitle("hello beijing!")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        ToastUtils.showToast(this, "onBind 代理人绑定");
        return builder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        ToastUtils.showToast(this, "onUnbind 代理人解除绑定");
        return super.onUnbind(intent);
    }

    /**
     * 每一次调用都会执行
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        ToastUtils.showToast(this, "  start onStartCommand  绑定");

//        Auto stop ---------intentService   原来是这个方法在做怪
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        }).start();*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ToastUtils.showToast(this, "解绑");
        Log.d(TAG, "onDestroy: ");
    }


}
