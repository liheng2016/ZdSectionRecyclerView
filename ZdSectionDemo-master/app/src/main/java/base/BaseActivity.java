package base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
/**
 * Created by niudong on 2017/9/28.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context mContext;
    private LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
/*        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //设定屏幕朝向yizhi 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mInflater = LayoutInflater.from(mContext);
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
//        CrabSDK.onResume(this);
        super.onResume();
    }


    @Override
    protected void onPause() {
//        CrabSDK.onPause(this);
        super.onPause();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


    /**
     * 跳转Activity
     *
     * @param targetActivity
     */
    protected void enterActivity(Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    protected void enterActivity(Bundle bundle, Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showSnackbar(View view, CharSequence text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startActivity(Class clazz, boolean isFinish) {
        startActivity(clazz, isFinish, null);
    }

    public void startActivity(Class clazz, boolean isFinish, String contact) {
        Intent intent = new Intent(this, clazz);
        if (contact != null) {
            intent.putExtra("username", contact);
        }
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }
}
