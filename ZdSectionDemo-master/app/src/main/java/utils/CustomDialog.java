package utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import view.niudong.com.demo.R;


/**
 * Created by niudong on 2017/1/18.
 */

public class CustomDialog extends Dialog {
    private Button yes;//确定按钮
    private TextView messageTv;//消息提示文本
    //确定文本和取消文本的显示内容
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private Context mContext;

    /**
     * 设置确定按钮的显示内容和监听
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xct_lthj_hkt_postion_detail_custom_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                    onClose();
                }
            }
        });
    }

    private void initView() {
        yes = (Button) findViewById(R.id.bt_ok);
        messageTv = (TextView) findViewById(R.id.message);
        setContent();
    }

    public void setContent() {
        if (null == messageTv) return;
        messageTv.append(processPopData("实际累计收益", "用接近真实的结算汇率得出的实际收益金额"));
        messageTv.append(processPopData("买入/卖出汇率差金额", "因当日参考买入/卖出参考汇率影响的收益金额"));
        messageTv.append(processPopData("当日参考买入汇率", "影响市值，当日卖出金额"));
        messageTv.append(processPopData("当日参考卖出汇率", "影响当日买入金额"));
    }

    public void onClose() {
        if (null != messageTv) {
            messageTv.setText("");
            messageTv = null;
        }
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    /**
     * 处理具体弹框说明
     */
    public SpannableString processPopData(String content1, String content2) {
        int mLightColor = mContext.getResources().getColor(R.color.login_color);
        String content = "\n" + content1 + " " + content2 + "\n";
        SpannableString ss = new SpannableString(content);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(UIUtils.dip2px(mContext, 13));
        int index = content.indexOf(" ");
        //字体颜色
        ss.setSpan(new ForegroundColorSpan(mLightColor), 0, index,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(UIUtils.dip2px(mContext, 13));
        ss.setSpan(span1, index + 1, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_color_gray)), index + 1,
                content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


}
