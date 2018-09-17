package view.niudong.com.demo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;

import base.BaseActivity;
import utils.ThreadUtils;

/**
 * Handler 使用
 * http://blog.csdn.net/yujiugang/article/details/52881527
 * <p>
 * ActivityThread
 * Handler 作用： 1、更新UI. 2、发送消息，处理消息.
 * <p>
 * 事件传递大部分是用Hander 传递消息的！
 * <p>
 * 面试的时候多问面试官问题！
 * <p>
 * <p>
 * <p>
 * 多线程并发问题  加锁   会导致性能下降
 * <p>
 * viewRootImpl    在onCreate中， 在onResume中执行。   和mainThread比较
 * <p>
 * 给handler指定loop对象
 * <p>
 * <p>
 * 自定义控件  保存状态回复
 */
public class HandlerActivity extends BaseActivity implements View.OnClickListener {
    public static final int UpDataText = 1;
    private static final String TAG = "HandlerActivity";
    public TextView tv_Handler;
    private ImageView iv_image;
    private int index;
    private Button bt_cancle;
    private MyRunnable myRunnable = new MyRunnable();

    int[] images = {R.drawable.sc_home_ico02, R.drawable.sc_home_ico03, R.drawable.sc_home_ico04, R.drawable.sc_home_ico06};
    //加入消息机制
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UpDataText:
                    String text = (String) msg.obj;
                    tv_Handler.setText(text);
                    break;
                default:
                    break;
            }
        }


    };
    private SimpleDateFormat bartDateFormat;

    @Override
    public void onClick(View v) {
        handler.removeCallbacks(myRunnable);
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            index++;
            index = index % 4;
            iv_image.setImageResource(images[index]);
            handler.postDelayed(myRunnable, 1000);
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_handler_view);
        tv_Handler = (TextView) findViewById(R.id.tv_Handler);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        bt_cancle = (Button) findViewById(R.id.bt_cancle);
        bartDateFormat = new SimpleDateFormat("yyyy-MMMM-dd-EEEE");
        handler.postDelayed(myRunnable, 1000);

    }

    @Override
    protected void initListener() {

        bt_cancle.setOnClickListener(this);
    }



    @Override
    public void initData() {

        sendMsg();
    }


    private void startMain() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "run: " + Thread.currentThread());
            }
        }, 1000);
    }

    /**
     * * handler.obtainMessage 返回一个msg
     * msg.sendToTarget 发送也会收到消息bart
     * handler.removeCallBack();
     */
    private void sendMsg() {
        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                Message msg = new Message();
                msg.what = UpDataText;
                msg.obj = "NiuDong  " + "时间戳  " + bartDateFormat.format(date);
                handler.sendMessage(msg);
            }
        });
    }


}
