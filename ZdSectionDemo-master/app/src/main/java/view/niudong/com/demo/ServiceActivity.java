package view.niudong.com.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import base.BaseActivity;
import view.niudong.com.demo.service.MyIntentService;
import view.niudong.com.demo.service.MyService;


public class ServiceActivity extends BaseActivity implements View.OnClickListener {
    private Button btLoad, start, stop, bind, unbind, bt_intent_service, bt_intent_un_service;

    private MyService.DownLoadBinder downLoadBuilder;
    /**
     * 和Service 建立连接
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBuilder = (MyService.DownLoadBinder) service;
            downLoadBuilder.startDownload();
            downLoadBuilder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    @Override
    public void initView() {
        setContentView(R.layout.activity_service);


        start = (Button) findViewById(R.id.bt_start);
        stop = (Button) findViewById(R.id.bt_stop);
        bind = (Button) findViewById(R.id.bt_bind);
        unbind = (Button) findViewById(R.id.bt_unbind);
        bt_intent_service = (Button) findViewById(R.id.bt_intent_service);
    }

    @Override
    protected void initListener() {
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        bt_intent_service.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            case R.id.bt_stop:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bt_bind:
                Intent intent2 = new Intent(this, MyService.class);
                bindService(intent2, connection, BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbind:
                unbindService(connection);
                break;
            case R.id.bt_intent_service:
                Intent intentSer = new Intent(this, MyIntentService.class);
                startService(intentSer);
                break;
            default:
                break;
        }
    }
}
