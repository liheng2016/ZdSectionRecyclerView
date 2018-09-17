package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import utils.ColorUtils;
import utils.MyUtil;
import view.niudong.com.demo.R;


/**
 * 名称：
 * Created by niudong on 2018/2/9.
 * Tel：18811793194
 * VersionChange：1.1.0
 */
public class OptionaIInfoView extends ConstraintLayout {

    private TextView txtTitle, txtContentLeft, txtContent2Sub;
    //中间更多
    private TextView tvMiddle;

    public OptionaIInfoView(Context context) {
        super(context);
        init(context, null);
    }

    public OptionaIInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OptionaIInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_hkt_exch_position, this, true);
        //标题
        txtTitle = (TextView) findViewById(R.id.tv_view_exch_position_title);
        //中间字体
        tvMiddle = (TextView) findViewById(R.id.tv_middle);
        //现价
        txtContentLeft = (TextView) findViewById(R.id.tv_view_exch_position_content2);
        //涨幅+，-号
        txtContent2Sub = (TextView) findViewById(R.id.tv_view_exch_position_content2_sub);

        //获取相关属性填充
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionaIInfoView);
        //标题
        CharSequence title = a.getText(R.styleable.OptionaIInfoView_title);
        setTitle(title);

        //更多
        CharSequence noneText = a.getText(R.styleable.OptionaIInfoView_noneText);
        if (null != noneText) {
            setNoneText(noneText);
        } else {
            //二级文本  现价
            CharSequence text2 = a.getText(R.styleable.OptionaIInfoView_text2);
            int rise2 = a.getInt(R.styleable.OptionaIInfoView_color1, 0);
            //二级副文本 涨幅
            CharSequence text3 = a.getText(R.styleable.OptionaIInfoView_text3);
            int rise3 = a.getInt(R.styleable.OptionaIInfoView_color3, 0);
            setBottomText(text2, text3, rise2, rise3);
        }


        a.recycle();
    }


    /**
     * 设置标题
     */
    public void setTitle(CharSequence title) {
        if (null != title) {
            txtTitle.setText(title);
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        }
    }


    /**
     * 设置底部的文本
     *
     * @param text1 左则文本(不可为空, 否则不执行操作)
     * @param text2 右则文本 (可为空, 则不显示)
     * @param rise1 左则文本颜色 >0 红, =0 灰, <0 绿
     * @param rise2 右则文本颜色 >0 红, =0 灰, <0 绿
     */
    public void setBottomText(CharSequence text1, CharSequence text2, double rise1, double rise2) {
        //设置左边涨幅值
        boolean t1isEmpty = TextUtils.isEmpty(text1);
        txtContentLeft.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        setTextAndColor(txtContentLeft, t1isEmpty ? "" : text1, getColorByRise(rise1));
        setVisibility(txtContentLeft, t1isEmpty ? View.INVISIBLE : View.VISIBLE);
        //设置涨跌百分比
        boolean t2isEmpty = TextUtils.isEmpty(text2);
        setTextAndColor(txtContent2Sub, t2isEmpty ? "" : MyUtil.getValue(String.valueOf(text2), false, true), getColorByRise(rise2));
        setVisibility(txtContent2Sub, t2isEmpty ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * 设置中间文本-----更多
     */
    public void setNoneText(CharSequence text) {

        //更改内容, 颜色
        boolean isEmpty = TextUtils.isEmpty(text);
        setTextAndColor(tvMiddle, isEmpty? "" : text, ColorUtils.COLOR_HK_7D8187);
        //更改大小
        tvMiddle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        //隐藏副文本和标题
        MyUtil.setVisibility(txtContent2Sub, View.INVISIBLE);
        MyUtil.setVisibility(txtTitle, View.INVISIBLE);
    }


    /**
     * 设置文本与颜色
     */
    private void setTextAndColor(TextView tv, CharSequence text, int color) {
        if (null != tv) {
            tv.setText(text);
            tv.setTextColor(color);
        }
    }

    /**
     * 通过涨跌标识确认显示的颜色
     *
     * @param rise >0 红, =0 灰, <0 绿
     * @return 颜色值
     */
    private int getColorByRise(double rise) {
        return rise > 0 ? ColorUtils.COLOR_RED : (rise == 0 ? ColorUtils.COLOR_HK_A3A8AF : ColorUtils.COLOR_GREEN);
    }

    private void setVisibility(View view, int visibility) {
        if (null != view && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

}
