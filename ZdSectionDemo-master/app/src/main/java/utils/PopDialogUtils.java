package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import view.niudong.com.demo.R;

import static view.niudong.com.demo.MyApplication.mContext;

/**
 * Created by Administrator on 2017/9/21.
 */

public class PopDialogUtils {
    private  AlertDialog showdialog;

    private static PopDialogUtils mPopDialogUtils;

    private Context mContext;

    public static PopDialogUtils getInstance(Context c) {
        if (null == mPopDialogUtils) {
            synchronized (PopDialogUtils.class) {
                if (null == mPopDialogUtils) {
                    mPopDialogUtils = new PopDialogUtils(c);
                }
            }
        }
        return mPopDialogUtils;
    }

    private PopDialogUtils(Context context) {
        mContext = context;

    }

    /**
     * 显示变更委托提示的文字
     */
    public void showEntrustChangePopWindow(View changeHintStatus, String[] data) {
        if (null == changeHintStatus) return;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_hk_buy_entrust_change, null);

        TextView tv_effective_income = (TextView) view.findViewById(R.id.tv_effective_income);
        TextView tv_already_realized = (TextView) view.findViewById(R.id.tv_already_realized);
        final PopupWindow hintPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hintPopupWindow.setContentView(view);
        hintPopupWindow.setOutsideTouchable(true);
        hintPopupWindow.setFocusable(false);
        tv_effective_income.setText(data[0]);
        tv_already_realized.setText(data[1]);
        hintPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int mShowMorePopupWindowWidth = -view.getMeasuredWidth();
        int mShowMorePopupWindowHeight = -view.getMeasuredHeight();
        Log.e("rewidth", view.getWidth() + "");
//        hintPopupWindow.showAsDropDown(changeHintStatus, mShowMorePopupWindowWidth, mShowMorePopupWindowHeight);
        hintPopupWindow.showAsDropDown(changeHintStatus);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintPopupWindow.dismiss();
            }
        });

        hintPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hintPopupWindow.dismiss();
            }
        });
    }




//    /**
//     * 显示变更委托提示的文字
//     */
//    public void showEntrustChangePopWindow(View changeHintStatus, String[] data) {
//        if (null == changeHintStatus) return;
//        LayoutInflater inflater = LayoutInflater.from(activity);
//        View view = inflater.inflate(R.layout.fragment_hkt_positiondetail, null);
//
//        TextView tv_effective_income = (TextView) view.findViewById(R.id.tv_effective_income);
//        TextView tv_already_realized = (TextView) view.findViewById(R.id.tv_already_realized);
//        final PopupWindow hintPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        hintPopupWindow.setContentView(view);
//        hintPopupWindow.setOutsideTouchable(true);
//        hintPopupWindow.setFocusable(false);
//        //设置颜色值
//        MyUtil.setHkFontColor(tv_effective_income, data[0], true);
//        MyUtil.setHkFontColor(tv_already_realized, data[1], true);
//
//        hintPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        hintPopupWindow.showAsDropDown(changeHintStatus);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hintPopupWindow.dismiss();
//            }
//        });
//
//        hintPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                hintPopupWindow.dismiss();
//            }
//        });
//    }


    /**
     * 显示 点点点
     */
    public  void showLoading3Point() {
        try {
            closePreDialog();
            showdialog = createDialog(mContext);
            showdialog.show();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.xct_lthj_layout_dialog_progressbar, null);
            // 启动动画
            ImageView imageViewProgress = (ImageView) view.findViewById(R.id.pg);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageViewProgress.getDrawable();
            animationDrawable.start();
            Window window = showdialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            // 设置透明度为0.3
            lp.alpha = 1.0f;
            // 设置暗色度
            lp.dimAmount = 0.0f;
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
            window.setContentView(view);
        } catch (Exception e) {
            Logger.printExceptionStackTrace(e);
        }
    }

    public  AlertDialog createDialog(Context pContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(pContext, R.style.MyDialogStyle);
        final AlertDialog progressDialog = builder.create();
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }



    /**
     * 将原来的显示中Dialog取消
     */
    public   void closePreDialog() {
        if (null != showdialog && showdialog.isShowing()) {
            showdialog.dismiss();
        }
    }

}
