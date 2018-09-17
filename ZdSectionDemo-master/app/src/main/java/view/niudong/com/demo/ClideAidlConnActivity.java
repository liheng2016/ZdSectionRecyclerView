package view.niudong.com.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import utils.ToastUtils;
import view.niudong.com.demo.bean.IMyAidl;
import view.niudong.com.demo.bean.TestAidlBean;
import view.niudong.com.demo.service.SeverAidlSerVice;

public class ClideAidlConnActivity extends AppCompatActivity {

    private TextView aidlClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clide_aidl_conn);

        aidlClick = findViewById(R.id.tv_click_aidl);
        aidlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeverAidlSerVice.class);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (iMyAidl != null) {
                                iMyAidl.getPersonList().clear();
                                addData();
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                },500);
            }
        });
    }


    private IMyAidl iMyAidl;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidl = null;
        }
    };

    List<TestAidlBean> dataListl;

    private void addData() {
        try {
            for (int i = 0; i < 3; i++) {

                TestAidlBean mode = new TestAidlBean();
                mode.age = 23 + i;
                mode.name = "zhangsan" + i;
                iMyAidl.addPerson(mode);

            }
            dataListl = iMyAidl.getPersonList();
            aidlClick.setText("远程AiDl 返回" + dataListl.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


}
