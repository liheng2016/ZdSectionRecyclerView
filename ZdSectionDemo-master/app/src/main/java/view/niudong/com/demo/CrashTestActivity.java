package view.niudong.com.demo;

import android.view.View;
import android.widget.Button;

import base.BaseActivity;
import utils.ToastUtils;

public class CrashTestActivity extends BaseActivity implements View.OnClickListener {

    private Button btAnr;
    private Button btArray;
    private Button btNull;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_crash_test);
        btAnr = (Button) findViewById(R.id.bt_anr);
        btArray = (Button) findViewById(R.id.bt_array_out);
        btNull = (Button) findViewById(R.id.bt_null);
    }

    @Override
    protected void initListener() {
        btAnr.setOnClickListener(this);
        btArray.setOnClickListener(this);
        btNull.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_null:
                testNull();
                break;
            case R.id.bt_array_out:
                testArray();
                break;
            case R.id.bt_anr:
                testAnr();
                break;
            default:
        }
    }

    private void testAnr() {
        int i = 0;
        while (true) {
            i++;
            ToastUtils.showToast(this, i + "");
            String name = Thread.currentThread().getName();
            ToastUtils.showToast(this, i + name);

        }
    }

    private void testArray() {

//        try {
            int arr[] = new int[10];
            int last = arr[10];
     /*   } catch (Exception e) {
            e.fillInStackTrace();
            ToastUtils.showToast(this, e.toString());
        }*/
    }

    private void testNull() {

//        try {
            String s = null;
            s.length();
     /*   } catch (Exception e) {
            e.fillInStackTrace();
            ToastUtils.showToast(this, e.toString());
        }*/
    }

}
