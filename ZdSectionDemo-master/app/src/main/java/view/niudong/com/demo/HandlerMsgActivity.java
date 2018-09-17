package view.niudong.com.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import utils.ToastUtils;

public class HandlerMsgActivity extends AppCompatActivity {

    private Handler subHandler;
    private Button bt_handerMsg;
    private Button button;
    private Looper looper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_msg);

        button = (Button) findViewById(R.id.bt_handerMsg);
        /** 主线程给子线程发消息*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 1、创建lopper对象，looper创建了MessageQ
                 *2、将当前的Looper对象和当前线程（子线程），绑定ThreadLocal
                 */
                Looper.prepare();
                /**1、创建了Handler对象，然后从当前线程获取Lopper对象，然后获取到MessageQ对象
                 */
                subHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String msgOk = (String) msg.obj;
                        if (msg.what == 2) {
                            ToastUtils.showToast(HandlerMsgActivity.this, msgOk);

                        }
                    }
                };
                looper = Looper.myLooper();
                /**
                 *1、将当前线程中找到之前创建的lopper对象，然后找到MessageQ
                 * 2、开启死循环，遍历消息池中消息
                 * 3、获取到Msg时调用msg.target.disPatchMsg方法执行Msg
                 *
                 */
                Looper.loop();

            }
        }).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();

            }
        });
    }

    private void sendMsg() {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = "今天天气很好，我要看Handler源码";
        subHandler.sendMessage(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (looper != null) {
            looper.quit();
            looper = null;

        }
    }
}
