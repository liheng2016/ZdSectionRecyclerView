package view.niudong.com.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/4.view.niudong.com.demo.receiver.MyReceiver
 */

public class MyReceiver extends BroadcastReceiver {
    private static MyReceiver myReceiver;
    private ConnectivityManager connectivityManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Toast.makeText(context, "网络连接成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "你断网了！", Toast.LENGTH_LONG).show();

        }
    }

    static {
        myReceiver = new MyReceiver();

    }

    public static MyReceiver getInstance() {
        return myReceiver;
    }

    public void timer() {
        //Handler创建一个延时的调用
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);
    }

}
