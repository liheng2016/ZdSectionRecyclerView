package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import base.BaseLazyFragment;
import utils.ToastUtils;
import utils.UIUtils;
import view.niudong.com.demo.R;

import static contants.ConfigInfo.COLOR_HK_333333;
import static contants.ConfigInfo.COLOR_HK_A3A8AF;

/**
 * TODO 注意：两个BaseFragment都是可以实现懒加载数据的
 */
public class SellChildFragment extends BaseLazyFragment {
    private static final String TAG = SellChildFragment.class.getName();
    public int mPosition;
    private View view;
    private TextView tvText;
    private ProgressBar mProgressBar;
    private TextView useInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
        }
    }

    public static SellChildFragment newInstance(int position) {
        SellChildFragment f = new SellChildFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }


    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_child, container, false);
        tvText = (TextView) view.findViewById(R.id.tv_child);
        useInfo = (TextView) view.findViewById(R.id.tv_test);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    protected void initData() {
    }


    @Override
    protected void lazyData() {
        super.lazyData();
        ToastUtils.showToast(getContext(), "" + mPosition);
        String mTitle = null;
        if (mPosition == 0) {
            String url_0_text = "用户协议及隐私条款";
            useInfo.setText("开始即表示您同意遵守");

            SpannableString spStr = new SpannableString(url_0_text);

            spStr.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.RED);       //设置文件颜色
                    ds.setUnderlineText(true);      //设置下划线
                }

                @Override
                public void onClick(View widget) {
                    Log.d("", "onTextClick........");
                }
            }, 0, url_0_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            useInfo.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
            useInfo.append(spStr);
            useInfo.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件

            tvText.setText(TextUtils.isEmpty(mTitle) ? "我为空啦！" : mTitle);
            Log.d(TAG, "lazyData" + mPosition + "......mTitle......" + mTitle);
        } else if (mPosition == 1) {


            SpannableString spannableString = processData("牛栋", "你好");
            useInfo.append(spannableString);
            SpannableString spannableString1 = processData("李栋", "呵呵呵");
            useInfo.append(spannableString1);
            SpannableString spannableString2 = processData("张栋", "合计欧文和");
            useInfo.append(spannableString2);

            spannableString2.toString();



        }

        mProgressBar.setVisibility(View.GONE);
    }

    private SpannableString processData(String content1, String content2) {
        String content = "\n" + content1 + " " + content2 + "\n";
        SpannableString ss = new SpannableString(content);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(UIUtils.dip2px(getActivity(), 13));
        int index = content.indexOf(" ");
        //字体颜色
        ss.setSpan(new ForegroundColorSpan(COLOR_HK_A3A8AF), 0, index,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(UIUtils.dip2px(getActivity(), 13));
        ss.setSpan(span1, index + 1, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(COLOR_HK_333333), index + 1,
                content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return  ss;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
