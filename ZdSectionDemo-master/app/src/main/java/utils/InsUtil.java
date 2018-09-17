package utils;

import android.content.Context;

/**
 * 单例模式
 * Created by donkor
 */
public class InsUtil {
    private static InsUtil instance;
    private Context mContext;

    private InsUtil(Context context) {
        this.mContext = context;
    }
    /**
     *正确的写法
     * 使用Application的Context
     * 使得这个Context的生命周期跟Application一样长
     * 传入Activity的Context，当前Activity退出时它的内存并不会被回收，因为单例对象持有该Activity的引用
     * 同时注意对Context的引用不要超过它本身的生命周期，慎重的对Context使用“static”关键字，
     * Context里如果有线程，一定要在onDestroy()里及时停掉
     */
//    private InsUtil(Context context) {
//        this.mContext = context.getApplicationContext();
//    }

    public static InsUtil getInsUtil(Context context) {
        if (instance == null) {
            instance = new InsUtil(context);
        }
        return instance;
    }
}