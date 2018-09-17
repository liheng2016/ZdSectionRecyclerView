package utils;

import android.util.Log;


/**
 * 日志输出统一管理工具类
 * 使用该类进行统一的输出管理，在代码调试阶段将GlobalInfo.SHOWLOG设置为true表示信息正常打印。
 *  项目上线后在application中将onCreate函数中GlobalInfo.SHOWLOG将设置为false时，即可不再打印
 * @author Administrator
 */
public class Logger {
	
	/**
	 * 使用默认的tag将行日志输出
	 */
	public static final String TAG = "LTHJ";
	public static boolean isDebug =true;
	
	private Logger()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	//用于匹配第二种情况  当输出语句不在同一行时，可以控制
	/**
	 * Logger 换行打印
	 * 	.println(log)
	 * @param log
	 */
	public static void println(String log){
		if(isDebug){
			System.out.println(log);
		}
	}
	/**
	 * 不换行打印
	 * @param log
	 */
	public static void print(String log){
		if(isDebug){
			System.out.println(log);
		}
	}
	
	public static void sysout(Object log){
		if(isDebug){
			System.out.println(log.toString());
		}
	}
	public static void sysout(){
		if(isDebug){
			System.out.println();
		}
	}
	
	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (isDebug)
			  Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (isDebug)
			  Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.v(tag, msg);
	}
	/**
	 * 打印异常日志
	 * @param tag
	 * @param e
	 */
	public static void e(String tag, String str, Exception e)
	{
		if (isDebug)
			Log.e(tag+str, e.toString());
	}
	/**
	 * 打印异常日志
	 *  因为项目中日志输出较多，一般把其他日志都屏蔽掉，使用这个日志进行输出
	 * @param tag
	 * @param e
	 */
	public static void i_To(String tag, String str)
	{
		if(isDebug){
			Log.d(tag,str);
		}
	}
	
	public static void printExceptionStackTrace(final Throwable e) {
		if(isDebug){
				Log.getStackTraceString(e);
//			if(isDebug){
//				Logger.e("error↓↓↓↓↓↓printExceptionStackTrace↓↓↓↓↓↓↓↓Throwable----");
//				PersistentInfo.getInstance().handler.post(new Runnable(){
//					@Override
//					public void run() {
//						if (e!=null){
//							e.printStackTrace();
//						}
//					}
//				});
//
//			}
		}
	}
	
}
