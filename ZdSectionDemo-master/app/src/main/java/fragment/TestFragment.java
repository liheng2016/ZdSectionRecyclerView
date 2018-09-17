package fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import base.TestBaseFragment;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/1/26.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class TestFragment extends TestBaseFragment {

    private EditText editText;
    private CharSequence inputStr;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    protected String getFragmentTitle() {
        return "Fragment返回键监听";
    }

    @Override
    public void onResume() {
        super.onResume();
        addBackPressedListener();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        editText = (EditText) mView.findViewById(R.id.tv_test_back);

    }

    @Override
    public void onPause() {
        super.onPause();
        removeBackPressedListener();
    }


    @Override
    public boolean doBack() {
        boolean isBack = false;
        if (getInputStr().length() == 0) {
            isBack = false;
        } else {
            isBack = true;
            new android.app.AlertDialog.Builder(getActivity()).setTitle("确定要退出么？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mContext.finish();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        return isBack;
    }

    /**
     * 数据
     */
    public String getInputStr() {
        return editText.getText().toString();
    }
}
