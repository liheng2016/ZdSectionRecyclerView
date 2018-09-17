package view.niudong.com.demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import entity.Category;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.CategoryViewBinder;
import mulitbinder.ViewBinder;
import utils.Logger;
import utils.ToastUtils;

import static view.niudong.com.demo.MyApplication.mContext;


public class EditTextActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    private static final int NUMBER = 5;
    private static final String TAG = EditTextActivity.class.getName();

    private static final InputFilter[] INPUT_FILTER_ARRAY = new InputFilter[1];
    private PopupWindow popupWindow;
    private EditText et_edit;


    /**
     * 保留小数点后多少位
     */
    private LinearLayout queryStateLayout;
    private Button bt;
    private TextView textView;
    private TextView testColor;
    private TextView tvClkicj;
    private LinearLayout tv_ll2;
    private LinearLayout tv_ll1;
    private String mCurrTab = "0";


    List<INfo> list = new ArrayList<>();
    private String current;

    /**
     * 允许最大的整数多少位
     */
//    private int mMaxIntegralLength = DEFAULT_MAX_INTEGER_LENGTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        et_edit = (EditText) findViewById(R.id.et_edit);
        queryStateLayout = (LinearLayout) findViewById(R.id.ll_layourt);
        textView = (TextView) findViewById(R.id.tv);
        tv_ll2 = (LinearLayout) findViewById(R.id.tv_ll2);
        tv_ll1 = (LinearLayout) findViewById(R.id.tv_ll1);


        testColor = (TextView) findViewById(R.id.tv_test_color);
        tvClkicj = (TextView) findViewById(R.id.tv_clkicj);
        bt = (Button) findViewById(R.id.bt_out2);
        et_edit.addTextChangedListener(this);
        bt.setOnClickListener(this);


        String newPwd = "12232323232";
//        if (-1 == (newPwd.indexOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"))) {
        if (isNumeric(newPwd)) {
            ToastUtils.showToast(this,"是");
        }else {
            ToastUtils.showToast(this,"不是");
        }


        String[] arrBank = new String[3];

        List<HkQueryChildAccModle> acntBankInfoList = getAcntBankInfoList();
        for (int i = 0; i < acntBankInfoList.size(); i++) {
            HkQueryChildAccModle hkQueryChildAccModle = acntBankInfoList.get(i);

            INfo iNfo = new INfo();
            if (TextUtils.equals(hkQueryChildAccModle.mBankType, "cn")) {
                String mStatus = hkQueryChildAccModle.mStatus;
                if (TextUtils.equals("-1", mStatus)) {
                    iNfo.name = "大陆银行卡汇款";
                    iNfo.postion = 0;
                } else if (TextUtils.equals("0", mStatus)) {
                    iNfo.name = "大陆银行卡汇款";
                    iNfo.postion = 0;
                } else if (TextUtils.equals("1", mStatus)) {
                    //大陆卡  要改名字  取银行名称进行拼接   大陆银行卡汇款-中国银行（香港）分行
                    iNfo.name = "大陆银行卡汇款-" + hkQueryChildAccModle.mBankName + "分行";
                    iNfo.postion = 1;
                }
            } else if (TextUtils.equals(hkQueryChildAccModle.mBankType, "hk")) {
                iNfo.name = "香港银行卡汇款-" + hkQueryChildAccModle.mBankName + "分行";
                iNfo.postion = i + 1;
            }

            list.add(iNfo);
        }

        INfo iNfo = list.get(2);
        String name = iNfo.name;


        int postion = findPostion(name);

        ToastUtils.showToast(this, String.valueOf(postion));
        int value = (1000 / 3) % 100;
        int haha = (1000 / 3) - value;
        int i = 1000 % 100;


        boolean okOne = 1000 % 100 == 0;
        boolean okTwo = 1000 / 100 == 0;
        Logger.d(value + i + "");
//        FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
//        FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
        testColor.setText(changeProfitLossColor("-342325435"));

        tv_ll1.setOnClickListener(this);
        tv_ll2.setOnClickListener(this);
        LinearLayout[] llayouts = new LinearLayout[]{tv_ll1, tv_ll2};
        for (LinearLayout rb : llayouts) {
            rb.setEnabled(rb.getId() == R.id.tv_ll1);
        }
        try {
            ToastUtils.showToast(this, json());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView useInfo = (TextView) findViewById(R.id.info);
        String str = "3223.32" + "、" + "323223";
        useInfo.setText("输入价格有误，临近的有效价格为");

        int indexPoit = str.indexOf("、");
        //2
        SpannableString spStr = new SpannableString(str);
        spStr.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                ToastUtils.showToast(EditTextActivity.this, "hhe");
            }
        }, 0, indexPoit + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                ToastUtils.showToast(EditTextActivity.this, "我点击了");
            }
        }, indexPoit + 1, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        useInfo.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        useInfo.append(spStr);
        useInfo.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件

    }

    private int findPostion(String name) {
        int pos = -1;
        for (INfo iNfo1 : list) {
            if (iNfo1.name.equals(name)) {
                pos = iNfo1.postion;
            }
        }
        return pos;
    }


    class INfo {

        String name;
        int postion;
    }


    //方法三：
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    @Override
    public void onClick(View v) {
        if (v == tv_ll1) {
            ToastUtils.showToast(this, "我1");
        } else if (v == tv_ll2) {
            ToastUtils.showToast(this, "我2");

        }

    }

    public List<HkQueryChildAccModle> getAcntBankInfoList() {

        List<HkQueryChildAccModle> list = new ArrayList<>();
        HkQueryChildAccModle modle = new HkQueryChildAccModle();
        //账户状态（-1未开通，0申请中，1已开通）
        modle.mStatus = "-1";
        //银行类型（cn:大陆卡 / hk:香港卡）
        modle.mBankType = "cn";
        //持卡人
        modle.mCustomer = "NIUDONG";
        //银行名称
        modle.mBankName = "中国银行（香港）";
        list.add(modle);
        modle = new HkQueryChildAccModle();
        //账户状态（-1未开通，0申请中，1已开通）
        modle.mStatus = "1";
        //银行类型（cn:大陆卡 / hk:香港卡）
        modle.mBankType = "hk";
        //持卡人
        modle.mCustomer = "NIUDONG";
        //银行名称
        modle.mBankName = "中国银行（香港）";
        list.add(modle);
        modle = new HkQueryChildAccModle();
        modle.mStatus = "1";
        //银行类型（cn:大陆卡 / hk:香港卡）
        modle.mBankType = "hk";
        //持卡人
        modle.mCustomer = "NIUDONG";
        //银行名称
        modle.mBankName = "招商银行（香港）";
        list.add(modle);
        return list;
    }

    private CharSequence testN() {

        CharSequence result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml("<font size=\"3\" color=\"#000000\">" + "你好！世界", Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml("<font size=\"3\" color=\"#000000\">" + "你好！世界");
        }
        return result;
    }

    private void setHkFontColor(TextView tv, String text, boolean addSign) {
        if (null == tv) return;
        int color = Color.BLACK;
        if (TextUtils.isEmpty(text)) {
            text = "--";
        } else if (!"0".equals(text)) {
            if (text.startsWith("-")) {
                color = Color.GREEN;
            } else {
                if (addSign) {
                    text = "+" + text;
                }
                color = Color.RED;
            }
        }

        tv.setText(text);
        tv.setTextColor(color);
    }


    private String json() throws JSONException {

//			TODO
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonInfo = new JSONObject();
        jsonInfo.put("SerialNo", "1");//合同编号
        jsonInfo.put("StkCode", "600050");
        jsonInfo.put("StkName", "腾讯控股");
        jsonInfo.put("MktName", "沪市");
        jsonInfo.put("StkAcnt", "A3263672372");
        jsonInfo.put("ExchName", "交易");
        jsonInfo.put("DealPrice", "88.8");
        jsonInfo.put("DealAmt", "100");
        jsonInfo.put("DealMoney", "666.6");
        jsonInfo.put("DealTime", "2017.11.6");
        jsonInfo.put("EntNo", "112233");
        jsonInfo.put("Comm", "22");
        jsonInfo.put("Stamp", "1");
        jsonInfo.put("TransFee", "1");
        jsonInfo.put("DealFee", "1");
        jsonInfo.put("EntDir", "B");
        jsonInfo.put("DirType", "1");

        jsonArray.put(jsonInfo);
        JSONArray jsonArraySort = new JSONArray();
        JSONObject jsonSort = new JSONObject();

        jsonSort.put("KName", "腾");
        jsonSort.put("KAlias", "xtkj");
        jsonSort.put("KSort", "0");
        jsonArraySort.put(jsonSort);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DealRS", jsonArray);
        jsonObject.put("KeyDef", jsonArraySort);
        return jsonObject.toString();

    }

    /**
     * 交易盈亏(不含手续费)  字体颜色处理
     */
    private CharSequence changeProfitLossColor(String value) {
        CharSequence result = null;
        if (TextUtils.isEmpty(value) || TextUtils.equals("--", value)) {
            result = (Html.fromHtml("<font size=\"3\" color=\"#000000\">" + "--"));
        } else if (!"0".equals(value)) {
            if (value.startsWith("-")) {
                result = (Html.fromHtml("<font size=\"3\" color=\"#38c4a9\">" + value));
            } else {
                result = (Html.fromHtml("<font size=\"3\" color=\"#000000\">" + value));
            }
        }
        return result;
    }

    private void showPop() {
        initPopWindow();
    }

    private String getInputNum() {
        return et_edit.getText().toString().trim();
    }

    private void testNullValue(String value) {
        //不相同那么 代表true                不是 null
        boolean isSupportWithdraw = !TextUtils.equals("0", value) && !TextUtils.isEmpty(value);
        if (isSupportWithdraw) {
            ToastUtils.showToast(this, "true");
        } else {
            ToastUtils.showToast(this, "false");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        Log.d(TAG, "onTextChanged.........." + charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String inOldNum = getInputNum();
        StringBuilder newText = new StringBuilder(inOldNum);
        int dotIndex = newText.indexOf(".");
        // 可出借
        // 判断小数点, 去除小数点后面

        if (dotIndex > 0) {
            newText.delete(dotIndex, newText.length());
        }
        String mInputCountNum = newText.toString();
//        et_edit.setText(mInputCountNum);
//        et_edit.setSelection(mInputCountNum.length());
        if (!inOldNum.equals(newText.toString())) {
            et_edit.setText(newText.toString());
        }
        // 光标指定到最后一位
        if (et_edit.getText().length() > 0) {
            et_edit.setSelection(et_edit.getText().length());
        }
    }


    /**
     * 获取金额
     */

    private String getMoneyText(String money) {

        return money.substring(0, money.length() - 1);
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }*/

    public String stringAdd2Decimals(String value) {
        if (!TextUtils.isEmpty(value)) {
            DecimalFormat format = new DecimalFormat("0.00");
            value = format.format(new BigDecimal(value));
        } else {
            return "--";
        }
        return value;
    }


    private static String stringAdd2Decimals2(String value) {

        if (!TextUtils.isEmpty(value)) {
            Double cny = Double.parseDouble(value);//6.20    这个是转为double类型
//            DecimalFormat format = new DecimalFormat("0.00");
            value = String.format(Locale.CHINA, "%.2f", cny);
            //value = format.format(cny);
        }
        return value; //6.20   这个是字符串，但已经是我要的两位小数了

    }


    protected void initPopWindow() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_popu_day_choice_double, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pop_recyclerview);


        // 产生背景变暗效果，设置透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;

//之前不写这一句也是可以实现的，这次突然没效果了。网搜之后加上了这一句就好了。据说是因为popUpWindow没有getWindow()方法。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        final Items items = new Items();
        final MultiTypeAdapter adapter = new MultiTypeAdapter(items);
        //TODO  注册
        adapter.register(View.class, new ViewBinder());
        adapter.register(Category.class, new CategoryViewBinder());
        //TODO  设置适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        final View Headview = inflater.inflate(R.layout.item_hk_head_view, null);
        final View loadview = inflater.inflate(R.layout.loading_view, null);
        final TextView loadStatus = (TextView) loadview.findViewById(R.id.tv_loading);
        recyclerView.setAdapter(adapter);
        items.clear();
        loadHintStatus(loadStatus, 2);
        items.add(loadview);
        adapter.setItems(items);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //添加头部View
                items.clear();
                items.add(Headview);
                for (int i = 0; i < 5; i++) {
                    items.add(new Category("我是MultiType数据" + i));
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                }
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //添加头部View
                items.clear();
                loadHintStatus(loadStatus, 1);
                items.add(loadview);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
        }, 2000);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);


        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(queryStateLayout);

        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());
    }

    private void loadHintStatus(TextView loadStatus, int status) {
        if (null == loadStatus) return;
        String hintContent = null;
        switch (status) {
            case 0:
                hintContent = "鑫财通正在为您准备数据...";
                break;
            case 1:
                hintContent = "暂无港股持仓";
                break;
            case 2:
                hintContent = "持仓获取失败，点击重试";
                loadStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(view.getContext(), "dian ji le....");
                    }
                });
                break;
            case 3:
                break;
            default:
                break;
        }
        loadStatus.setText(hintContent);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 测量layout的高度。
     *
     * @param context
     * @param view
     * @return
     */
    public int getLayoutHeight(Context context, View view) {
        view.measure(0, 0);
        int measuredHeight = view.getMeasuredHeight();
        return measuredHeight;
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }




    /**
     * 名称：查港股子账户及银行信息 Modle
     * Created by niudong on 2017/11/14.
     * Tel：18811793194
     * VersionChange：金贝塔
     */
    class HkQueryChildAccModle {
        //账户状态（-1未开通，0申请中，1已开通）
        public String mStatus;
        //银行类型（cn:大陆卡 / hk:香港卡）
        public String mBankType;
        //持卡人
        public String mCustomer;
        //银行代码
        public String mBankCode;
        //银行名称
        public String mBankName;
        //币种类型
        public String mMoneyType;
        //    银行账号
        public String mBankAcnt;
        //银行地址
        public String mBankAddress;
        //银行细分代码
        public String mBankLedger;
        //手机号码
        public String mMobile;
        //电子邮箱
        public String mEmail;
    }

}