package utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.math.BigDecimal;

import view.niudong.com.demo.MyApplication;

/**
 * UI适配调节
 * 
 * @author Administrator
 * 
 */
public class UIUtils {
	private static int screenW;
	private static int screenH;
	private static float screenDensity;

	public static final float UI_WIDTH = 540f;
	public static final float UI_VIEW_HEIGHT = 390f;
	public static final float UI_HEIGHT = 960f;

	public static float scale = 1.0f;

	public static float innerWidth = 360f;

	/**
	 * 将原视图 宽高，padding，margin, 及文本字体大小 按比例缩放，重新布局；
	 * 
	 * @param view
	 *            单个视图，或视图层级
	 * @param scale
	 *            缩放比例
	 */
	public static void relayoutViewHierarchy(Context context, View view, float scale) {

		if (view == null) {
			return;
		}

		scaleView(context, view, scale);

		if (view instanceof ViewGroup) {
			View[] children = null;
			try {
				Field field = ViewGroup.class.getDeclaredField("mChildren");
				field.setAccessible(true);
				children = (View[]) field.get(view);
			} catch (NoSuchFieldException e) {
				Logger.printExceptionStackTrace(e);
			} catch (IllegalArgumentException e) {
				Logger.printExceptionStackTrace(e);
			} catch (IllegalAccessException e) {
				Logger.printExceptionStackTrace(e);
			}
			if (children != null) {
				for (View child : children) {
					relayoutViewHierarchy(context, child, scale);
				}
			}
		}
	}
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}


	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 点击的位置是否在view中
	 * @param view
	 * @param ev
	 * @return
	 */
	public static boolean inRangeOfView(View view, MotionEvent ev) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y
				|| ev.getY() > (y + view.getHeight())) {
			return false;
		}
		return true;
	}
	/**
	 * 将视图按比例缩放，不考虑嵌套视图；
	 * 
	 * @param view
	 *            不考虑嵌套，缩放单个View；
	 * @param scale
	 *            缩放比例；
	 */
	private static void scaleView(Context context, View view, float scale) {

		if (view instanceof TextView) {
			resetTextSize(context, (TextView) view, scale);
		}

		int pLeft = convertFloatToInt(view.getPaddingLeft());
		int pTop = convertFloatToInt(view.getPaddingTop());
		int pRight = convertFloatToInt(view.getPaddingRight());
		int pBottom = convertFloatToInt(view.getPaddingBottom());
		view.setPadding(getFinal(context, pLeft, scale), getFinal(context, pTop, scale),
				getFinal(context, pRight, scale), getFinal(context, pBottom, scale));

		LayoutParams params = view.getLayoutParams();
		scaleLayoutParams(context, params, scale);
		view.setLayoutParams(params);

	}

	/**
	 * 获得最终的值
	 * 
	 * @param context
	 * @param pxValue
	 * @param scale
	 * @return
	 */
	public static int getFinal(Context context, float pxValue, float scale) {
		if (pxValue == 0) {
			return 0;
		}
		int dpValue = dip2px(context, pxValue);
		int fpxValue = px2dip(context, dpValue * scale);
		return fpxValue;
	}

	//

	/**
	 * 将视图布局属性按比例设置；
	 * 
	 * @param params
	 * @param scale
	 *            缩放比例；
	 */
	public static void scaleLayoutParams(Context context, LayoutParams params, float scale) {
		if (params == null) {
			return;
		}
		if (params.width > 0) {
			params.width = getFinal(context, convertFloatToInt(params.width), scale);
		}
		if (params.height > 0) {
			params.height = getFinal(context, convertFloatToInt(params.height), scale);
		}

		if (params instanceof MarginLayoutParams) {
			MarginLayoutParams mParams = (MarginLayoutParams) params;
			if (mParams.leftMargin > 0) {
				mParams.leftMargin = getFinal(context, convertFloatToInt(mParams.leftMargin), scale);
			}
			if (mParams.rightMargin > 0) {
				mParams.rightMargin = getFinal(context, convertFloatToInt(mParams.rightMargin), scale);
			}
			if (mParams.topMargin > 0) {
				mParams.topMargin = getFinal(context, convertFloatToInt(mParams.topMargin), scale);
			}
			if (mParams.bottomMargin > 0) {
				mParams.bottomMargin = getFinal(context, convertFloatToInt(mParams.bottomMargin), scale);
			}
		}
	}

	/**
	 * 将 TextView（或其子类）文本大小按比例缩放；
	 * 
	 * @param textView
	 * @param scale
	 *            缩放比例；
	 */
	private static void resetTextSize(Context context, TextView textView, float scale) {
		float size = textView.getTextSize();
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getFinal(context, size, scale));
	}

	/**
	 * 初始宽度
	 */
	public static void initScale() {
		BigDecimal b1 = new BigDecimal(Double.toString(innerWidth));
		BigDecimal b2 = new BigDecimal(Double.toString(360));
		scale = b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * float 转换至 int 小数四舍五入
	 */
	private static int convertFloatToInt(float sourceNum) {
		return (int) (sourceNum + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(float dpValue) {
		final float scale = MyApplication.mContext.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getScreenW(Context context) {
		if (screenW == 0) {
			initScreen(context);
		}
		return screenW;
	}

	public static int getScreenH(Context context) {
		if (screenH == 0) {
			initScreen(context);
		}
		return screenH;
	}

	public static float getScreenDensity(Context context) {
		if (screenDensity == 0) {
			initScreen(context);
		}
		return screenDensity;
	}

	private static void initScreen(Context context) {
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		screenW = metric.widthPixels;
		screenH = metric.heightPixels;
		screenDensity = metric.density;
	}

	/**
	 * 测量layout的高度。
	 * 
	 * @param context
	 * @param view
	 * @return
	 */
	public static int getLayoutHeight(Context context, View view) {
		view.measure(0, 0);
		int measuredHeight = view.getMeasuredHeight();
		return measuredHeight;
	}

	/** 设置View的可见性 */
	public static void setVisibility(View view, int visibility) {
		if (view.getVisibility() != visibility) {
			view.setVisibility(visibility);
		}
	}

}
