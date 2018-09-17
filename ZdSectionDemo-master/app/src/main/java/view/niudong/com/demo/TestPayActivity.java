package view.niudong.com.demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.Pingpp;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.ApplyUtil;
import utils.OnClickUtils;
import utils.ToastUtils;

import static contants.Constant.REQUEST_CODE_PAYMENT;

/**
 * TODO  支付防止重复点击处理
 */

public class TestPayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = TestPayActivity.class.getSimpleName();
    private static String HOST = "https://view_hkt_border_shadow.xincaitong.com:8443/XctPayWeb/paytest.jhtml?";
    private EditText et_amount, et_user_info, et_pay_type;
    private Button bt_pay, bt_create_order;
    private String mOrderId;
    private TextView tv_order;
    private EditText et_input_amount;
    private String mPrice;
    private String mUserPhone;
    private RelativeLayout rl_wechat, rl_alipay;
    private float amount;

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private String mPayType;
    private String mPayMoney;

    //mBtnCreateOrder.setEnabled(true);  提交订单按钮事件处理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pay);
        //开启调试模式
        Pingpp.DEBUG = true;
        //TODO Test 内存泄露   LeakClass这个内部类持有MainActivity的引用
        //创建订单  输入
        et_amount = (EditText) findViewById(R.id.et_amount_price);
        et_user_info = (EditText) findViewById(R.id.et_user_info);
        //创建订单  按钮
        bt_create_order = (Button) findViewById(R.id.bt_create_order);
        tv_order = (TextView) findViewById(R.id.tv_order);

        //订单支付 输入
        et_input_amount = (EditText) findViewById(R.id.et_input_amount);
        et_pay_type = (EditText) findViewById(R.id.et_pay_type);
        bt_pay = (Button) findViewById(R.id.bt_pay);

        //切换支付渠道
/*      rb_alipay = (RadioButton) findViewById(R.id.rb_alipay);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);

        rb_webchat = (RadioButton) findViewById(R.id.rb_webchat);
        rl_wechat = (RelativeLayout) findViewById(R.id.rl_wechat);*/


        //注册点击事件
        bt_create_order.setOnClickListener(this);
        bt_pay.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.bt_create_order:
                tv_order.setText("");
                mPrice = et_amount.getText().toString().trim();
                if (TextUtils.isEmpty(mPrice)) {
                    Toast.makeText(this, "价格不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mUserPhone = et_user_info.getText().toString().trim();
                if (!ApplyUtil.isMobileNO(mUserPhone)) {
                    Toast.makeText(this, "请检查你的用户id！", Toast.LENGTH_SHORT).show();
                    return;
                }
                createOrder(mPrice, mUserPhone);
                break;
            case R.id.bt_pay:

                if (OnClickUtils.isFastClick()) {
                    mPayMoney = et_input_amount.getText().toString().trim();
                    if (TextUtils.isEmpty(mPayMoney)) {
                        Toast.makeText(this, "价格不能为空哦！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.equals(mPrice, mPayMoney)) {
                        Toast.makeText(this, "支付价格和订单价格要一致！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mPayType = et_pay_type.getText().toString().trim();
                    if (TextUtils.isEmpty(mPayType)) {
                        Toast.makeText(this, "支付类型不能为空哦！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //TODO 订单号
                    if (TextUtils.isEmpty(mOrderId)) {
                        Toast.makeText(this, "订单号不能为空哦！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    requestChange(mOrderId, mPayType, mPayMoney);
                }
                break;
            default:
                break;
        }
    }

    private void createOrder(String price, String userPhone) {
        Map<String, String> map = new HashMap<>();

        map.put("type", "order");
        map.put("protocolVer", "1.0");
        map.put("payId", userPhone);
        map.put("amount", price);
        map.put("orderName", "股票");
        map.put("orderDes", "小鑫鑫发福利了");

        OkHttpUtils.post().url(HOST)
                .params(map)
                .build().execute(new StringCallback() {


            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                    Log.d(TAG, "onError: " + e);
                    tv_order.setText("发生异常了！");
            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    mOrderId = jsonObject.optString("orderId");
                    tv_order.setText(!TextUtils.isEmpty(mOrderId) ? mOrderId : "订单号为空了");
                    Log.d(TAG, "onResponse: 订单号为 " + mOrderId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //获取Change对象
    public void requestChange(String orderId, String payType, String money) {

        Map<String, String> map = new HashMap<>();

        if (TextUtils.equals("2", payType)) {
            map.put("payChannel", "1");
        }
        map.put("type", "pay");
        map.put("protocolVer", "1.0");
        map.put("orderId", orderId);
        map.put("payType", payType);
        map.put("disablePayChannels", "coupon,creditCard,credit_group,mcard,pcard,promotion,voucher,point,mdiscount");
        map.put("needBuyerRealNamed", "F");
        map.put("amount", money);

        OkHttpUtils.post().url(HOST)
                .params(map)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
//                主线程
                Log.d(TAG, "onResponse: 支付串" + response);
//                Pingpp.createPayment(TestPayActivity.this, response);
                openPaymentActivity(response);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                // TODO  询问结果
                ToastUtils.showToast(this,result);
            }
        }
    }

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //支付页面返回处理
            if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
                String result = data.getExtras().getString("pay_result");
           *//* 处理返回值
        * "success" - 支付成功
        * "fail"    - 支付失败
        * "cancel"  - 取消支付
        * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
        * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
        *//*
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            Log.d(TAG, "onActivityResult: 发生异常啦" + errorMsg + "extraMsg" + extraMsg);
            Toast.makeText(TestPayActivity.this, "支付结果" + result + "errorMsg" + errorMsg + "extraMsg" + extraMsg, Toast.LENGTH_SHORT).show();
        }
    }*/
    private void openPaymentActivity(String charge) {

        Intent intent = new Intent();
        String packageName = getPackageName();
        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }


}
