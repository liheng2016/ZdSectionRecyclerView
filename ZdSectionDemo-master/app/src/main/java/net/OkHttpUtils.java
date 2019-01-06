package net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import view.niudong.com.demo.MyApplication;

/**
 * 名称：
 * Created by niudong on 2018/12/7
 * Tel：18811793194
 * VersionChange：mthq1.0
 */
public class OkHttpUtils {

    private static OkHttpUtils mIntence;
    private OkHttpClient okHttpClient;

    private OkHttpUtils() {
        //no instance
    }

    public static OkHttpUtils getInteance() {
        if (null==mIntence){
            mIntence=new OkHttpUtils();
        }
        return mIntence;
    }

    /**
     * 异步网络请求
     */
    public void getHttpUtils(String mUrl ,Callback callback) {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient();
        }
        Request request = new Request.Builder().url(mUrl).get().build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
