package view.niudong.com.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import base.BaseActivity;
import customview.AroundCircleView;
import customview.ProgressBut;
import customview.ProgressDown;
import utils.ToastUtils;


public class CustomViewActivity extends BaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_custom_view);
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void initData() {

        ProgressBut progressBar = (ProgressBut) findViewById(R.id.pb);
        progressBar.setMaxProgress(100);
        progressBar.setText("80%");
        progressBar.setProgressEnable(true);
        progressBar.setCurrentProgress(80);
        //=============================================================
        /**
         * 确实生效了呀
         */
        ProgressDown progressDown = (ProgressDown) findViewById(R.id.pb_down);
        //自定义控件只要是继承ViewGroup需要设置背景才可以触发onDraw方法
        Drawable drawable = new ColorDrawable(Color.WHITE);
        progressDown.setBackground(drawable);
        progressDown.setDLText("90%");
        progressDown.setMaxProgress(180);
        progressDown.setCurrentProgress(100);
        progressDown.setIvIcon(R.drawable.sc_home_ico06);
        progressDown.setDLText("成功");

        AroundCircleView aroundImage = (AroundCircleView) findViewById(R.id.pb_main);
        aroundImage.setProgress(69);
        aroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(CustomViewActivity.this, "我点击了");
            }
        });

    }
}
