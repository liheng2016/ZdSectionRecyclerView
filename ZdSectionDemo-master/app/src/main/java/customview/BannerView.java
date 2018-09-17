package customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import utils.GlideManagerUtil;
import utils.GlideUtils;
import utils.UIUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2018/6/13.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class BannerView extends RelativeLayout {

    private Context context;

    private Handler handler;


    private boolean isHaveHandler = true;// 当用户点击轮播图时，取消handler队列，也就是取消滚动

    // 控件Start
    private ViewPager viewPager;

    private LinearLayout indicator;// 指示器

    private onItemClickListener listener;
    // 控件End

    // 自定义属性Start
    private float mAspectRatio; // 宽高比

    private int defaultImageResource; // 默认占位图

    private int updateTime; // 图片切换的时间间隔,默认3秒

    private boolean showIndicator; // 是否显示指示器,默认显示

    private int indicatorHeight;// 指示器的高度,默认35dp

    private int indicatorPositionSize; // 指示器的大小

    private int indicatorBackground; // 指示器的背景颜色

    // 自定义属性End

    // 数据Start
    private int imageCount;

    private int lastPosition;

    private int currentPosition;

    private List<Integer> imageResources;

    private List<String> imageUrls;
    // 数据End

    /**
     * 点击监听回调事件
     */
    public interface onItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(lastPosition + 1);
            handler.postDelayed(updateRunnable, updateTime);
        }
    };

    public BannerView(Context context, float aspectRatio) {
        this(context, null);
        mAspectRatio = aspectRatio;
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseCustomAttributes(context, attrs);
        this.context = context;
        handler = new Handler();
        initViews();
    }

    /**
     * 解析自定义属性
     *
     * @param context
     * @param attrs
     */
    private void parseCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mAspectRatio = typedArray.getFloat(R.styleable.BannerView_aspectRatio, 0f);
        defaultImageResource = typedArray.getResourceId(R.styleable.BannerView_defaultSrc,
                R.mipmap.ic_launcher);
        updateTime = typedArray.getInt(R.styleable.BannerView_updateTime, 3000);
        showIndicator = typedArray.getBoolean(R.styleable.BannerView_indicatorVisible, true);
        indicatorHeight = (int) (typedArray.getDimension(R.styleable.BannerView_indicatorHeight,
                UIUtils.dip2px(context, 35)));
        indicatorBackground = typedArray.getResourceId(R.styleable.BannerView_indicatorBackground,
                R.color.ripple_material_dark);
        indicatorPositionSize = (int) typedArray.getDimension(
                R.styleable.BannerView_indicatorPositionSize, UIUtils.dip2px(context, 5));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultHeight = 0;
        if (mAspectRatio > 0) {//TODO 用户没有设置宽高比
            int width = MeasureSpec.getSize(widthMeasureSpec);//得到宽
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);//测量模式
            if (heightMode == MeasureSpec.EXACTLY) {
                resultHeight = MeasureSpec.getSize(heightMeasureSpec);//采用用户自己设定的
            } else {
                resultHeight = UIUtils.dip2px(MyApplication.mContext, 200);//给个默认高度
                if (heightMode == MeasureSpec.AT_MOST) {
                    int height = MeasureSpec.getSize(heightMeasureSpec);//测量高度
                    resultHeight = Math.min(height, resultHeight);
                }
            }
            //设置最终的结果值
            setMeasuredDimension(width, resultHeight);
        } else {//TODO 用户设置了宽高比
            int width = MeasureSpec.getSize(widthMeasureSpec);
            // 如果mAspectRatio大于0表示用户设置了宽高比，否则直接调用父类的onMeasureSpec。
            int height = (int) (width / 2);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initViews() {
        viewPager = new ViewPager(context);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (showIndicator) {
                    for (int i = 0; i < indicator.getChildCount(); i++) {
                        indicator.getChildAt(i).setSelected(false);
                    }
                    indicator.getChildAt(position % imageCount).setSelected(true);
                }
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:// 空闲状态
                        if (!isHaveHandler) {
                            isHaveHandler = true;
                            handler.postDelayed(updateRunnable, updateTime);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:// 用户滑动状态
                        handler.removeCallbacks(updateRunnable);
                        isHaveHandler = false;
                        break;
                }
            }
        });
        addView(viewPager, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        if (showIndicator) {
            indicator = new LinearLayout(context);
            indicator.setOrientation(LinearLayout.HORIZONTAL);
            indicator.setGravity(Gravity.CENTER);
            indicator.setBackgroundResource(indicatorBackground);
            RelativeLayout.LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, indicatorHeight);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            addView(indicator, layoutParams);
        }
    }

    public void setImageResources(List<Integer> imageResources) {
        if (imageResources == null || imageResources.size() == 0) {
            throw new RuntimeException("图片资源为空");
        }
        this.imageResources = imageResources;
        imageCount = imageResources.size();
    }

    public void setImageUrls(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.size() == 0) {
            throw new RuntimeException("图片地址资源为空");
        }
        this.imageUrls = imageUrls;
        imageCount = imageUrls.size();
        loadImages();
    }

    private void loadImages() {
        if (showIndicator) {
            addIndicationPoint();
        }
        viewPager.setAdapter(new MyViewPageAdapter());
        viewPager.setCurrentItem(200 - (200 % imageCount));
        handler.removeCallbacks(updateRunnable);
        handler.postDelayed(updateRunnable, updateTime);
    }

    /**
     * 添加指示点到指示器中
     */
    private void addIndicationPoint() {
        // 防止刷新重复添加
        if (indicator.getChildCount() > 0) {
            indicator.removeAllViews();
        }
        View pointView;
        int margin = UIUtils.dip2px(context, 5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                indicatorPositionSize, indicatorPositionSize);
        layoutParams.setMargins(margin, margin, margin, margin);

        for (int i = 0; i < imageCount; i++) {
            pointView = new View(context);
            pointView.setBackgroundResource(R.drawable.indicator_selector);
            if (i == lastPosition) {
                pointView.setSelected(true);
            } else {
                pointView.setSelected(false);
            }
            indicator.addView(pointView, layoutParams);
        }
    }

    private class MyViewPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //加载url
            GlideManagerUtil.glideLoader(MyApplication.mContext, imageUrls.get(position % imageCount),
                    R.mipmap.ic_launcher, R.mipmap.ic_launcher, imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v, position % imageCount);
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
