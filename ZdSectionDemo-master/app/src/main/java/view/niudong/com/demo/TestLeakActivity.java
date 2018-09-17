package view.niudong.com.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.BaseActivity;
import utils.TestDataUtils;
import utils.InsUtil;

public class TestLeakActivity extends BaseActivity implements View.OnClickListener {
    private Drawable sBackground = null;
    private static String s = null;
    private Button bt_thread;
    private Button bt_handler;
    private Button bt_inner, bt_context;
    private TextView tv_thread, tv_handler;
    private CharSequence time;

    private Handler handler = new Handler();
    private MyHandler myHandler;

    private MyAsyncTask myAsyncTask;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_leak);
        bt_thread = (Button) findViewById(R.id.bt_thread);
        bt_handler = (Button) findViewById(R.id.bt_handler);
        bt_inner = (Button) findViewById(R.id.bt_inner);
        bt_context = (Button) findViewById(R.id.bt_context);
        tv_thread = (TextView) findViewById(R.id.tv_thread);
        tv_handler = (TextView) findViewById(R.id.tv_handler);
        myHandler = new MyHandler(this);

        myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute();
    }


    @Override
    protected void initListener() {
        bt_thread.setOnClickListener(this);
        bt_inner.setOnClickListener(this);
        bt_handler.setOnClickListener(this);
        bt_context.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    /**
     * 测试内存泄漏的代码
     * 非静态内部类的对象会强引用其外围对象
     */
    private void testThreadLeak() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tv_thread.setText(getTime());
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_handler:
                testHandlerLeak();
                break;
            case R.id.bt_thread:
                testThreadLeak();
                break;
            case R.id.bt_inner:
                testInnear();
                break;
            case R.id.bt_context:
                testContext();
                break;
            default:
                break;
        }

    }

    private void testContext() {
        InsUtil.getInsUtil(this);
    }

    private void testInnear() {
        TestDataUtils.setData(new userInfo());
    }


    private void testHandlerLeak() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_handler.setText(getTime());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        return simpleDateFormat.format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        myHandler.removeCallbacksAndMessages(null);

        //判断异步任务是否存在
        if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            myAsyncTask.cancel(true);
        }
    }

    public static class userInfo {
        String mName;
    }


//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            // do something you want
//        }
//    };

    /**
     * 正确的写法
     * 原因：
     * 在finish()的时候，该Message还没有被处理，Message持有Handler,
     * Handler持有Activity,这样会导致该Activity不会被回收，就发生了内存泄露.
     * 解决方法：
     * 使用显形的引用，1.静态内部类。 2. 外部类
     * 使用弱引用 2. WeakReference
     * 最后在Activity调用onDestroy()的时候要取消掉该Handler对象的Message和Runnable
     */
    private static class MyHandler extends Handler {
        private final WeakReference<TestLeakActivity> mActivityReference;

        public MyHandler(TestLeakActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestLeakActivity handlerAct = mActivityReference.get();
            if (handlerAct == null) {
                return;
            }
            // Do something  you want
        }
    }


    /**
     * new Thread(new MyRunnable()).start();
     */

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            SystemClock.sleep(10000);
        }
    }


    /**
     * //正确的写法
     * myAsyncTask = new MyAsyncTask(ThreadActivity.this);
     * myAsyncTask.execute();
     * <p>
     * <p>
     * 以上的异步任务和Runnable都是一个匿名内部类，因此它们对当前Activity都有一个隐式引用。
     * 如果Activity在销毁之前，任务还未完成， 那么将导致Activity的内存资源无法回收，造成内存泄漏。
     * 正确的做法还是使用静态内部类的方式
     * 最后在Activity销毁的时候，相对应的取消异步任务
     */
    private static class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> weakReference;

        public MyAsyncTask(Context context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(10000);
            return null;
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TestLeakActivity activity = (TestLeakActivity) weakReference.get();
            if (activity != null) {
                //...
            }
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}