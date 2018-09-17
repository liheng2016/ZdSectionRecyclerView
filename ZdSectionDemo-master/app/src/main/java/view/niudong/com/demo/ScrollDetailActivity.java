
package view.niudong.com.demo;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import customview.ObservableScrollView;
import utils.MyTextWatcher;
import utils.ToastUtils;

/**
 * Android开发之仿微博详情页（滑动固定顶部栏效果）

 */
public class ScrollDetailActivity extends AppCompatActivity implements MyTextWatcher.CallBack,NestedScrollView.OnScrollChangeListener, ObservableScrollView.OnObservableScrollViewScrollChanged{

    private NestedScrollView sv_contentView;
    private LinearLayout ll_topView;
    private EditText tv_topView;
    private LinearLayout ll_fixedView;

    //用来记录内层固定布局到屏幕顶部的距离
    private int mHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_view);

        sv_contentView= (NestedScrollView) findViewById(R.id.sv_contentView);
        ll_topView= (LinearLayout) findViewById(R.id.ll_topView);
        tv_topView= (EditText) findViewById(R.id.tv_topView);
        ll_fixedView= (LinearLayout) findViewById(R.id.ll_fixedView);
        sv_contentView.setOnScrollChangeListener(this);
        MyTextWatcher myTextWatcher = new MyTextWatcher(this);
        tv_topView.addTextChangedListener(myTextWatcher);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            //获取HeaderView的高度，当滑动大于等于这个高度的时候，需要把topView移除当前布局，放入到外层布局
            mHeight=ll_topView.getTop();
        }
    }

    /**
     * @param l Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    public void onObservableScrollViewScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt) {

            if(t>=mHeight){
                if(tv_topView.getParent()!=ll_fixedView){
                    ll_topView.removeView(tv_topView);
                    ll_fixedView.addView(tv_topView);
                }
            }else{
                if(tv_topView.getParent()!=ll_topView){
                    ll_fixedView.removeView(tv_topView);
                    ll_topView.addView(tv_topView);
                }
            }
    }

    @Override
    public void textInfo(String s, int count) {
        if (TextUtils.isEmpty(s))return;
        sv_contentView.scrollTo(0,Integer.parseInt(s));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        ToastUtils.showToast(this,"高度"+scrollY);

    }
}
