package view.niudong.com.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import customview.RiskRankView;

public class RiskLevelActivity extends AppCompatActivity {

    private RiskRankView creditLevelView;
    public String[] titles = { "企业经营风险","流动性风险","杠杆风险","波动幅度", "系统性风险" };
//
    public float[] data = {4, 3, 5, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_level);


        initView();
        initData();




    }

    private void initView() {
        creditLevelView = (RiskRankView) findViewById(R.id.cutom_creditLevel);

    }

    private void initData() {
        creditLevelView.setMainPaintColor(getResources().getColor(R.color.colorPrimary));
        creditLevelView.setValuePaintColor(getResources().getColor(R.color.colorPrimary));


        creditLevelView.setMaxValue(5);
        creditLevelView.setData(data, titles);
    }

    /**
     * 设置限制字符提示
     * @param textView
     * @param len
     */
    private void setLimitRedTextTv(TextView textView, int len){
        String txt = "可输入"+len+"字";
        SpannableString builder = new SpannableString(txt);
        int iindex = txt.indexOf(len+"");
        int eindex = iindex+String.valueOf(len).length();
        int color = getResources().getColor(R.color.colorPrimary);
        builder.setSpan(new ForegroundColorSpan(color),iindex, eindex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

}
