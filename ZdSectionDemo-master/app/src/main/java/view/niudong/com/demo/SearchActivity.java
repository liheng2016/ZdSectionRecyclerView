package view.niudong.com.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import utils.Logger;
import utils.ToastUtils;

import static view.niudong.com.demo.MyApplication.mContext;

public class SearchActivity extends AppCompatActivity implements TextWatcher {
    EditText etSearch;
    /**
     * 当前搜索栏中的内容
     */
    private String mSearchText;
    /**
     * 消息 触发搜索股票
     */
    private static final int MSG_SEARCH_STOCK = 0;
    /**
     * 消息 搜索结果返回
     */
    private static final int MSG_SEARCH_STOCK_RESULT = 1;


    /**
     * 搜索页面 空代码 使用云搜索
     */
    private View mEmptySearchView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEARCH_STOCK:
                    final String strSearch = null == msg.obj ? null : msg.obj.toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if (TextUtils.isEmpty(strSearch)) return;
                            Message message = Message.obtain(null, MSG_SEARCH_STOCK_RESULT, strSearch);
                            mHandler.sendMessage(message);
                        }
                    }).start();
                    break;
                case MSG_SEARCH_STOCK_RESULT:
                    String data = (String) msg.obj;
                    if (!TextUtils.isEmpty(data) && (Integer.parseInt(data) <= 2000)) {
                        ToastUtils.showToast(getApplicationContext(), "我搜到了数据哦！");
                        linearLayout.removeAllViews();

                    } else {
                        if (null == mEmptySearchView) {
                            mEmptySearchView = initEmptySearchView();
                        } else {
                            ToastUtils.showToast(getApplicationContext(), "云搜索啦..." + data);
                        }
                    }


                    break;
                default:
                    break;
            }

        }
    };
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }


    private void initView() {
        etSearch = (EditText) findViewById(R.id.et_search);
        linearLayout = (LinearLayout) findViewById(R.id.ll_serach);
        etSearch.addTextChangedListener(this);
    }

    /**
     * @param charSequence 输入框中改变前的字符串信息
     * @param start        输入框中改变前的字符串的起始位置
     * @param count        输入框中改变前后的字符串改变数量一般为0
     * @param after        输入框中改变后的字符串与起始位置的偏移量
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        Log.e(getPackageName(), "---beforeTextChanged---");
    }

    /**
     * @param charSequence 输入框中改变后的字符串信息
     * @param start        输入框中改变后的字符串的起始位置
     * @param before       输入框中改变前的字符串的位置 默认为0
     * @param count        输入框中改变后的一共输入字符串的数量
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        Log.e(getPackageName(), "---onTextChanged---" + charSequence.length());
        mSearchText =  charSequence.toString();
//        mHandler.removeMessages(MSG_SEARCH_STOCK);
//        if (charSequence.length() > 0) { //搜索
////            延迟执行任务
//            Message message = Message.obtain(null, MSG_SEARCH_STOCK, charSequence);
//            mHandler.sendMessageDelayed(message, 500);
//        }
//        String name = Thread.currentThread().getName();
//        Logger.d("onTextChanged" + name);

        List<HkSearchStock> searchResult = getSearchResult(mSearchText);
        Logger.d(searchResult.toString());

    }

    /**
     * 匹配代码
     *
     * @param mSeachContent 输入的代码
     */
    private List<HkSearchStock> getSearchResult(String mSeachContent) {
        List<HkSearchStock> mSearchHistoryList = new ArrayList<>();

        mSearchHistoryList.add(new HkSearchStock("12345", "abcde"));
        mSearchHistoryList.add(new HkSearchStock("67890", "ghijk"));
        mSearchHistoryList.add(new HkSearchStock("34567", "niudo"));
        mSearchHistoryList.add(new HkSearchStock("88888", "nanan"));

        List<HkSearchStock> resultData = new ArrayList<>();
        if (!TextUtils.isEmpty(mSeachContent) && null != mSearchHistoryList && mSearchHistoryList.size() > 0) {
            int inputLength = mSeachContent.trim().length();
            for (HkSearchStock mode : mSearchHistoryList) {
                //全匹配，匹配代码和拼音
                if (inputLength >= 5 && (TextUtils.equals(mode.mStkCode, mSeachContent)
                        || TextUtils.equals(mode.mPyShort, mSeachContent))) {
                    resultData.add(mode);
                } else {//其他情况
                    if (mode.mStkCode.length() > inputLength && TextUtils.equals(mSeachContent, mode.mStkCode.substring(0, mSeachContent.length()))) {
                        resultData.add(mode);
                    }
                    if (mode.mPyShort.length() > inputLength && TextUtils.equals(mSeachContent, mode.mPyShort.substring(0, mSeachContent.length()))) {
                        resultData.add(mode);
                    }
                    //匹配包含关系

                    if (mode.mStkCode.length() > inputLength && mode.mStkCode.contains(mSeachContent)) {
                        if (!resultData.contains(mode)) {
                            resultData.add(mode);
                        }
                    }
                    if (mode.mPyShort.length() > inputLength && mode.mPyShort.contains(mSeachContent)) {
                        if (!resultData.contains(mode)) {
                            resultData.add(mode);
                        }
                    }
                }

            }
        }

        return resultData;
    }

    /**
     * @param editable 输入结束呈现在输入框中的信息
     */
    @Override
    public void afterTextChanged(Editable editable) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 初始化搜索代码页面为空时, 使用网络搜索
     */
    private View initEmptySearchView() {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.view_stock_search_empty, null, false);
        linearLayout.removeAllViews();
        linearLayout.addView(view);
        //搜索动作
        view.findViewById(R.id.bt_stock_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtils.showToast(getApplicationContext(), "去请求云端数据" + mSearchText);
            }
        });

        return view;
    }


    private class HkSearchStock {
        public String mStkCode;

        public HkSearchStock(String mStkCode, String mPyShort) {
            this.mStkCode = mStkCode;
            this.mPyShort = mPyShort;
        }

        public String mPyShort;
    }
}


