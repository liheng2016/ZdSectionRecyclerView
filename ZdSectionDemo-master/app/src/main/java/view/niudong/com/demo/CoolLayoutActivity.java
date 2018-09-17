package view.niudong.com.demo;

import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import base.BaseActivity;
import customview.CircleBackView;
import customview.OptionaIInfoView;
import customview.MyArcView;
import utils.PopDialogUtils;
import utils.ToastUtils;

/**
 * 权限机制
 */
public class CoolLayoutActivity extends BaseActivity implements MyArcView.OnViewClichListener {

    private MyArcView myView;
    private CircleBackView bvTotal;
    private CircleBackView bvToday;
    private OptionaIInfoView clickMore;
    private WebView webView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_cool_layout);
        myView = (MyArcView) findViewById(R.id.my_view);
        bvTotal = (CircleBackView) findViewById(R.id.bv_position_detail_total_rate);
        bvToday = (CircleBackView) findViewById(R.id.bv_position_detail_today_rate);
        clickMore = (OptionaIInfoView) findViewById(R.id.view_more);
        myView.setListener(this);

        initDrawImage();
        float[] gainRates = {3, 1};
        // 设置圆环的显示比例和文字颜色
//         * 收益率的比例，两个收益率的环形图需要的数据，需要计算今日收益和总收益计算二者绝对值的比例，符号与收益值相同，即100：-60 的比例为
        bvTotal.setRate(1, gainRates[0] / gainRates[1]);
        bvToday.setRate(-1, gainRates[1] / gainRates[0]);

    }

    private void initDrawImage() {
        myView.setRateAndImage(-0.5f, R.drawable.ic_aq_audio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myView.setRateAndImage(0.9f, R.drawable.ic_aq_pic);

            }
        }, 2000);
    }

    @Override
    protected void initListener() {
        clickMore.setNoneText("更多");

        clickMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2Data(1516880431000L,"yyyy-MM-dd");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void clickView() {
        PopDialogUtils.getInstance(CoolLayoutActivity.this).showLoading3Point();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PopDialogUtils.getInstance(CoolLayoutActivity.this).closePreDialog();
            }
        }, 2000);

    }

    /**
     * 2017-07-26       "yyyy-MM-dd"
     *
     * 01-29  19∶41    "MM:dd HH:mm"
     * @param time
     */
    private void time2Data(long time , String format){

        if (time>0){

    Date date2 = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//24小时制
        date2.setTime(time);
    ToastUtils.showToast(this,simpleDateFormat.format(date2));
        }
}
    /**
     * 已指定日期为准,向前或者向后推迟 day天的日期
     *
     *            为负数，向前推迟，为正数，向后推迟
     */
    public static String getDateBackDayOnDay(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 推迟的日期
        Date backDay = c.getTime();
        return dateFormat.format(backDay);
    }
}
