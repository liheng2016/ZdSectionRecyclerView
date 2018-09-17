package fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import base.BaseLazyFragment;
import utils.MyTextWatcher;
import utils.ToastUtils;
import view.niudong.com.demo.R;

/**
 * fragment.CoolNewFragment
 * 名称：
 * Created by niudong on 2017/12/29.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class CoolNewFragment extends BaseLazyFragment implements MyTextWatcher.CallBack, View.OnClickListener {


    private LinearLayout.LayoutParams layoutParams;
    private EditText editText;
    private boolean isSupportShe = true;
    private Button mButton;

    @Override
    protected void initData() {

    }

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_cool_new, null);
        MyTextWatcher myTextWatcher = new MyTextWatcher(this);
        editText = new EditText(getContext());
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        editText.setLayoutParams(layoutParams);
        editText.setHint("输入委托价格");
        editText.addTextChangedListener(myTextWatcher);
        LinearLayout rootL = (LinearLayout) view.findViewById(R.id.root_View);
        mButton = (Button) view.findViewById(R.id.hehe);
        rootL.addView(editText);


        return view;
    }

    @Override
    protected void lazyData() {
        super.lazyData();
    }

    @Override
    public void textInfo(String s, int count) {
        processData(s);
    }

    /**
     * 计算处理
     */
    private void processData(String s) {
        if (isSupportShe) {
            isSupportShe = false;
            editText.setText(s);
        } else {
            isSupportShe = true;
        }
    }

    @Override
    public void onClick(View v) {
    }
}
