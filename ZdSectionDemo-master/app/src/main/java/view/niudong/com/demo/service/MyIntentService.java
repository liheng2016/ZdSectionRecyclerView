package view.niudong.com.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import utils.ToastUtils;


/**
 * Created by niudong on 2017/4/13.
 */


public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";



    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ToastUtils.showToast(this,"MyIntentService绑定");
        Log.d(TAG, "onHandleIntent: " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.showToast(this,"MyIntentService解绑");
        Log.d(TAG, "onDestroy: ");
    }
}
