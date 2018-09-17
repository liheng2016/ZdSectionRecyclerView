package view.niudong.com.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import java.util.HashMap;
import java.util.Map;

import utils.ThreadFactory;
import utils.ToastUtils;


/**
 * Created by niudong on 2017/4/13.
 */


public class MyApplication extends Application {
    private static final String appKey = "15c1e15f3bc9cff9";
    public static MyApplication mContext;
    private static Handler handler;
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mainThreadId = android.os.Process.myTid();// 获取当前主线程id
        handler=new Handler();
        mContext = this;
        handler.post(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                ToastUtils.showToast(mContext, threadName);
            }
        });

      new   Thread(new Runnable() {
          @Override
          public void run() {
              Looper.prepare();
            Handler  handlerTwo=new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    return false;
                }
            });
              ToastUtils.showToast(mContext, "");
          }
      }).start();

        //初始线程数
        ThreadFactory.allotThreadPool();
        // 在打开app初始化时回调，故此方法要再init之前调用
        // 不设置的情况下默认值是-1，即为关闭，故实现接口前必须设置CrabSDK.setConstantSameCrashExceedLimit(3);且>0时回调才有效
  /*      CrabSDK.setConstantSameCrashExceedLimit(3);
        CrabSDK.setOnCrashExceedListener(new OnCrashExceedListener() {
            @Override
            public void onCrashExceedCallback() {
                Log.e("^^^^^^^^^^^^^^^^^", "同一crash连续发生，回调此方法！");
            }
        });
        //TODO 百度Crabsh 初始化配置
        CrabSDK.init(this, appKey);
        CrabSDK.openNativeCrashHandler();

        // 开启卡顿捕获功能, 传入每天上传卡顿信息个数，-1代表不限制, 已自动打开
        CrabSDK.enableBlockCatch(-1);

        // 自定义卡顿阈值，默认是2000ms，强烈建议阈值不宜小于500ms
        CrabSDK.setBlockThreshold(2000);


        //以下是对可配置参数的设置，详情请参见平台安装指南：http://crab.baidu.com/wiki/sdk
        // 设置同一crash一天上传的上限，-1代表无上限，默认是10
        CrabSDK.setUploadLimitOfSameCrashInOneday(-1);

        // 设置一天上传的crash总数上限，-1代表无上限，默认是30
        CrabSDK.setUploadLimitOfCrashInOneday(-1);

        // 设置一天上传anr的上限，-1代表无上限，默认是10
        CrabSDK.setUploadLimitOfAnrInOneday(-1);

        // 设置渠道号
        CrabSDK.setChannel("testCrashDemo");

        // 开启ndk捕获功能，默认关闭，
        // 若app涉及NDK编程，可去平台下载NativeSDK集成后，打开此开关即可
        CrabSDK.openNativeCrashHandler(); // Native crash收集

        // 开启截屏收集功能，默认关闭
        CrabSDK.setCollectScreenshot(true);

        // 设置crab的log开关，默认开启
        CrabSDK.setEnableLog(true);
        //设置SDK是否为debug模式，默认为false
        CrabSDK.setDebugMode(true);
        // 设置自定义字段的两种方式 1.直接传一个字段；2.传一个map
        // 二选一，同时调用只显示后者
//		CrabSDK.setUsersCustomKV("key", "value");

        HashMap<String, String> customMap = new HashMap<String, String>();
        customMap.put("key1", "value1");
        customMap.put("key2", "valu\n" +
                "        // 设置对crash anr发生时回调接口\n" +
                "        CrabSDK.setOnAnrCrashListener(new OnAnrCrashListener() {\n" +
                "\n" +
                "            *//**\n" +
         "             * anr发生时回调接口\n" +
         "             * anrTraceMap包含字段如下\n" +
         "             * {apiType=ANR,\n" +
         "             *  time=2016-05-18 10:29:50,\n" +
         "             *  errorOriLine=com.baidu.crabsdk.TestActivity$6.onClick(TestActivity.java:~139),\n" +
         "             *  errorType=ANR,\n" +
         "             *  threadList=main..\n" +
         "             * }\n" +
         "             * @param anrTraceMap anrTrace信息\n" +
         "             *//*\n" +
                "            @Override\n" +
                "            public void onAnrStarted(Map map) {\n" +
                "                // TODO Auto-generated method stub\n" +
                "                Log.e(\"############\", \"Anr Callback-- \" + map.toString());\n" +
                "                ToastUtils.showToast(MyApplication.mContext, map.toString());\n" +
                "            }\n" +
                "\n" +
                "            *//**\n" +
         "             * Java crash发生时回调接口\n" +
         "             * @param thread crash线程信息\n" +
         "             * @param ex trawable信息\n" +
         "             *//*\n" +
                "            @Override\n" +
                "            public void onCrashStarted(Thread arg0, Throwable arg1) {\n" +
                "                // TODO Auto-generated method stub\n" +
                "                Log.e(\"#############\", \"Crash Callback-- \" + arg0.toString() + \"\\n Throwable: \" + arg1.toString());\n" +
                "                ToastUtils.showToast(MyApplication.mContext, arg0.toString() + \"异常..\" + arg1.toString());\n" +
                "            }\n" +
                "\n" +
                "            *//**\n" +
         "             * Native crash发生时回调此接口\n" +
         "             * @param e throwable信息（error）\n" +
         "             * @param backTrace 异常栈信息\n" +
         "             * @param signo 异常信号量\n" +
         "             *//*\n" +
                "            @Override\n" +
                "            public void onNativeCrashStarted(Error arg0, String arg1, int arg2) {\n" +
                "                // TODO Auto-generated method stub\n" +
                "                Log.e(\"#############\", \"Native Crash Callback-- \" + arg0.toString() + \"\\n\" + arg1 + \"\\n signal: \" + arg2);\n" +
                "            }\n" +
                "        });e2");
        customMap.put("key3", "value3");
        CrabSDK.setUsersCustomKV(customMap);
*/

//        if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

    }
    public static int getMyTid() {
        return mainThreadId;
    }

    public static Context getInstence() {
        return mContext;
    }

    /**
     * 获取主线程相应的Handler
     */
    public static Handler getHandler() {
        return handler;
    }
    public static class CommonUtil {
        public static void runOnUiThead(Runnable runnable){
            if (android.os.Process.myTid()==MyApplication.getMyTid()){
                runnable.run();
            }else {
                getHandler().post(runnable);
            }
        }}


}
